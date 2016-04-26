package org.uma.jmetal.algorithm.components.impl.tracking;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.uma.jmetal.algorithm.AbstractDynamicGeneticAlgorithm;
import org.uma.jmetal.algorithm.components.TrackingImplementation;
import org.uma.jmetal.algorithm.components.impl.reproduction.SteadyStateReproduction;
import org.uma.jmetal.util.fileoutput.SolutionSetOutput;

public class ResultToFileOutputTracking implements TrackingImplementation {

    private int count;
    private int current;
    private String outputFolder;
    private int minStepsToNextTrack;

    public ResultToFileOutputTracking(String outputFolder) {
        this(outputFolder, 1000);
    }

    public ResultToFileOutputTracking(String outputFolder, int minStepsToNextTrack) {
        this.count = 0;
        this.current = 0;
        this.outputFolder = outputFolder;
        this.minStepsToNextTrack = minStepsToNextTrack;
    }

    public String getOutputFolder() {
        return outputFolder;
    }

    public void setOutputFolder(String outputFolder) {
        this.outputFolder = outputFolder;
    }

    public int getMinStepsToNextTrack() {
        return minStepsToNextTrack;
    }

    public void setMinStepsToNextTrack(int minStepsToNextTrack) {
        this.minStepsToNextTrack = minStepsToNextTrack;
    }

    @Override
    public void trackProgress(AbstractDynamicGeneticAlgorithm algorithm) {
        final List result = algorithm.getResultWithDominatedSolutions();
        if (current == 0 && count == 0) {
            current += result.size();
            try {
                SolutionSetOutput.printObjectivesToFile(result, outputFolder + "/" + (count) + ".txt");
            } catch (IOException ex) {
                Logger.getLogger(ResultToFileOutputTracking.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            current += algorithm.getReproductionImplementation() instanceof SteadyStateReproduction ? 1 : result.size();
            if (current >= minStepsToNextTrack) {
                current -= minStepsToNextTrack;
                try {
                    SolutionSetOutput.printObjectivesToFile(result, outputFolder + "/" + (++count) + ".txt");
                } catch (IOException ex) {
                    Logger.getLogger(ResultToFileOutputTracking.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
