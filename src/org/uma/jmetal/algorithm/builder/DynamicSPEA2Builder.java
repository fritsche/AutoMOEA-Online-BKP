package org.uma.jmetal.algorithm.builder;

import java.util.List;
import org.uma.jmetal.algorithm.impl.DynamicSPEA2;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.AlgorithmBuilder;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;

public class DynamicSPEA2Builder<S extends Solution<?>> implements AlgorithmBuilder<DynamicSPEA2<S>> {

    /**
     * NSGAIIBuilder class
     */
    private final Problem<S> problem;
    private int maxEvaluations;
    private int populationSize;
    private int archiveSize;
    private CrossoverOperator<S> crossoverOperator;
    private MutationOperator<S> mutationOperator;
    private SelectionOperator<List<S>, List<S>> selectionOperator;
    private SolutionListEvaluator<S> evaluator;

    public DynamicSPEA2Builder(Problem<S> problem,
            int populationSize, int archiveSize,
            CrossoverOperator<S> crossoverOperator,
            MutationOperator<S> mutationOperator) {
        this.problem = problem;
        this.populationSize = populationSize;
        this.archiveSize = archiveSize;
        this.crossoverOperator = crossoverOperator;
        this.mutationOperator = mutationOperator;
        this.evaluator = new SequentialSolutionListEvaluator<>();
    }

    public DynamicSPEA2Builder<S> setMaxEvaluations(int maxEvaluations) {
        if (maxEvaluations < 0) {
            throw new JMetalException("maxIterations is negative: " + maxEvaluations);
        }
        this.maxEvaluations = maxEvaluations;

        return this;
    }

    public DynamicSPEA2Builder<S> setPopulationSize(int populationSize) {
        if (populationSize < 0) {
            throw new JMetalException("Population size is negative: " + populationSize);
        }

        this.populationSize = populationSize;

        return this;
    }

    public DynamicSPEA2Builder<S> setArchiveSize(int archiveSize) {
        if (archiveSize < 0) {
            throw new JMetalException("Archive size is negative: " + archiveSize);
        }

        this.archiveSize = archiveSize;

        return this;
    }

    public DynamicSPEA2Builder<S> setSelectionOperator(SelectionOperator<List<S>, List<S>> selectionOperator) {
        if (selectionOperator == null) {
            throw new JMetalException("selectionOperator is null");
        }
        this.selectionOperator = selectionOperator;

        return this;
    }

    public DynamicSPEA2Builder<S> setSolutionListEvaluator(SolutionListEvaluator<S> evaluator) {
        if (evaluator == null) {
            throw new JMetalException("evaluator is null");
        }
        this.evaluator = evaluator;

        return this;
    }

    public DynamicSPEA2Builder<S> setCrossoverOperator(CrossoverOperator<S> crossoverOperator) {
        this.crossoverOperator = crossoverOperator;
        return this;
    }

    public DynamicSPEA2Builder<S> setMutationOperator(MutationOperator<S> mutationOperator) {
        this.mutationOperator = mutationOperator;
        return this;
    }

    public DynamicSPEA2<S> build() {
        DynamicSPEA2<S> algorithm = null;
        algorithm = new DynamicSPEA2<>(problem, maxEvaluations, populationSize, archiveSize,
                crossoverOperator, mutationOperator, evaluator);
        if (selectionOperator != null) {
            algorithm.setSelectionOperator(selectionOperator);
        }
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

    public int getArchiveSize() {
        return archiveSize;
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
