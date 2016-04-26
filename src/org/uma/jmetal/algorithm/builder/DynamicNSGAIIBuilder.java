package org.uma.jmetal.algorithm.builder;

import java.util.List;
import org.uma.jmetal.algorithm.components.impl.diversity.CrowdingDistance;
import org.uma.jmetal.algorithm.components.impl.operator.selection.KTournamentSelection;
import org.uma.jmetal.algorithm.components.impl.ranking.DominanceDepth;
import org.uma.jmetal.algorithm.impl.DynamicNSGAII;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.AlgorithmBuilder;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;

public class DynamicNSGAIIBuilder<S extends Solution<?>> implements AlgorithmBuilder<DynamicNSGAII<S>> {

    /**
     * NSGAIIBuilder class
     */
    private final Problem<S> problem;
    private int maxEvaluations;
    private int populationSize;
    private CrossoverOperator<S> crossoverOperator;
    private MutationOperator<S> mutationOperator;
    private SelectionOperator<List<S>, List<S>> selectionOperator;
    private SolutionListEvaluator<S> evaluator;

    public DynamicNSGAIIBuilder(Problem<S> problem,
            int populationSize,
            CrossoverOperator<S> crossoverOperator,
            MutationOperator<S> mutationOperator) {
        this.problem = problem;
        this.crossoverOperator = crossoverOperator;
        this.mutationOperator = mutationOperator;
        this.populationSize = populationSize;
        selectionOperator = new KTournamentSelection(populationSize, 2, new DominanceDepth(), new CrowdingDistance());
        evaluator = new SequentialSolutionListEvaluator<>();

    }

    public DynamicNSGAIIBuilder<S> setMaxEvaluations(int maxEvaluations) {
        if (maxEvaluations < 0) {
            throw new JMetalException("maxIterations is negative: " + maxEvaluations);
        }
        this.maxEvaluations = maxEvaluations;

        return this;
    }

    public DynamicNSGAIIBuilder<S> setPopulationSize(int populationSize) {
        if (populationSize < 0) {
            throw new JMetalException("Population size is negative: " + populationSize);
        }

        this.populationSize = populationSize;

        return this;
    }

    public DynamicNSGAIIBuilder<S> setSelectionOperator(SelectionOperator<List<S>, List<S>> selectionOperator) {
        if (selectionOperator == null) {
            throw new JMetalException("selectionOperator is null");
        }
        this.selectionOperator = selectionOperator;

        return this;
    }

    public DynamicNSGAIIBuilder<S> setSolutionListEvaluator(SolutionListEvaluator<S> evaluator) {
        if (evaluator == null) {
            throw new JMetalException("evaluator is null");
        }
        this.evaluator = evaluator;

        return this;
    }

    public DynamicNSGAIIBuilder<S> setCrossoverOperator(CrossoverOperator<S> crossoverOperator) {
        this.crossoverOperator = crossoverOperator;
        return this;
    }

    public DynamicNSGAIIBuilder<S> setMutationOperator(MutationOperator<S> mutationOperator) {
        this.mutationOperator = mutationOperator;
        return this;
    }

    public DynamicNSGAII<S> build() {
        DynamicNSGAII<S> algorithm = null;
        algorithm = new DynamicNSGAII<>(problem, maxEvaluations, populationSize, crossoverOperator,
                mutationOperator, selectionOperator, evaluator);

        return algorithm;
    }

    public Problem<S> getProblem() {
        return problem;
    }

    public int getMaxEvaluations() {
        return maxEvaluations;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public CrossoverOperator<S> getCrossoverOperator() {
        return crossoverOperator;
    }

    public MutationOperator<S> getMutationOperator() {
        return mutationOperator;
    }

    public SelectionOperator<List<S>, List<S>> getSelectionOperator() {
        return selectionOperator;
    }

    public SolutionListEvaluator<S> getEvaluator() {
        return evaluator;
    }

}
