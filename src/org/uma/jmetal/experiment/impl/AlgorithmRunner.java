package org.uma.jmetal.experiment.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.fileoutput.SolutionSetOutput;

public class AlgorithmRunner<R> implements Runnable {

    private Algorithm<R> algorithm;
    private long runningTime;
    private String outputDirectory;
    private String description;
    private R result;

    public AlgorithmRunner(Algorithm<R> algorithm, String outputDirectory, String description) {
        this.algorithm = algorithm;
        this.outputDirectory = outputDirectory;
        this.description = description;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public String getOutputFile() {
        return description;
    }

    public void setOutputFile(String description) {
        this.description = description;
    }

    public long getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(long runningTime) {
        this.runningTime = runningTime;
    }

    public Algorithm<R> getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm<R> algorithm) {
        this.algorithm = algorithm;
    }

    public R getResult() {
        return result;
    }

    public void setResult(R result) {
        this.result = result;
    }

    @Override
    public void run() {
        try {
            runningTime = System.nanoTime();
            algorithm.run();
            runningTime = System.nanoTime() - runningTime;
            result = algorithm.getResult();
            if (result instanceof List) {
                printResults((List<Solution<?>>) result);
            } else if (result instanceof Solution) {
                Solution<?> concreteResult = (Solution<?>) result;
                List<Solution<?>> solutionList = new ArrayList();
                solutionList.add(concreteResult);
                printResults(solutionList);
            }
        } catch (Exception ex) {
            Logger.getLogger(AlgorithmRunner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void printResults(List<Solution<?>> concreteResult) {
        try {
            File outputDirectoryFile = new File(outputDirectory);
            if (!outputDirectoryFile.exists()) {
                outputDirectoryFile.mkdirs();
            }
            SolutionSetOutput.printObjectivesToFile(concreteResult, outputDirectory + "/FUN_" + description + ".txt");
            SolutionSetOutput.printVariablesToFile(concreteResult, outputDirectory + "/VAR_" + description + ".txt");
            try (FileWriter writer = new FileWriter(outputDirectory + "/TIME_" + description + ".txt")) {
                writer.append(String.valueOf(runningTime));
                writer.append("\n");
            }
        } catch (IOException ex) {
            Logger.getLogger(AlgorithmRunner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
