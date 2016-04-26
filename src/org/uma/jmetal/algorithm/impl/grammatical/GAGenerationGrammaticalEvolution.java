package org.uma.jmetal.algorithm.impl.grammatical;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.algorithm.components.impl.operator.mutation.DuplicationMutation;
import org.uma.jmetal.algorithm.components.impl.operator.mutation.PruneMutation;
import org.uma.jmetal.problem.impl.GAGenerationProblem;
import org.uma.jmetal.solution.impl.VariableIntegerSolution;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;

public class GAGenerationGrammaticalEvolution extends GrammaticalEvolutionAlgorithm {

    private final GAGenerationProblem problem;
    private final String outputFile;

    public GAGenerationGrammaticalEvolution(GAGenerationProblem problem, int maxEvaluations, int populationSize, CrossoverOperator<VariableIntegerSolution> crossoverOperator, MutationOperator<VariableIntegerSolution> mutationOperator, SelectionOperator<List<VariableIntegerSolution>, VariableIntegerSolution> selectionOperator, PruneMutation pruneMutationOperator, DuplicationMutation duplicationMutationOperator, SolutionListEvaluator<VariableIntegerSolution> evaluator, String outputFile) {
        super(problem, maxEvaluations, populationSize, crossoverOperator, mutationOperator, selectionOperator, pruneMutationOperator, duplicationMutationOperator, evaluator);
        this.problem = problem;
        this.outputFile = outputFile;

        new File(outputFile).getParentFile().mkdirs();
    }

    @Override
    public void run() {
        List<VariableIntegerSolution> offspringPopulation;
        List<VariableIntegerSolution> matingPopulation;

        setPopulation(createInitialPopulation());

        //Execute the algorithm
        evaluatePopulation(getPopulation());
        //Calculate the Hypervolume (fitness)
        problem.evaluateAll(getPopulation(), Collections.emptyList());

        initProgress();
        while (!isStoppingConditionReached()) {
            matingPopulation = selection(getPopulation());
            offspringPopulation = reproduction(matingPopulation);

            //Execute the algorithm
            evaluatePopulation(offspringPopulation);
            //Calculate the Hypervolume (fitness)
            problem.evaluateAll(getPopulation(), offspringPopulation);

            setPopulation(replacement(getPopulation(), offspringPopulation));
            updateProgress();
        }
    }

    @Override
    public void updateProgress() {
        printProgress();
        super.updateProgress(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initProgress() {
        printProgress();
        super.initProgress(); //To change body of generated methods, choose Tools | Templates.
    }

    private void printProgress() {
        try (FileWriter writer = new FileWriter(outputFile, true)) {
            writer.write(getResult().getAttribute("FoundIn") + " - " + getResult().getVariables() + "\n");
        } catch (IOException ex) {
            Logger.getLogger(GAGenerationGrammaticalEvolution.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
