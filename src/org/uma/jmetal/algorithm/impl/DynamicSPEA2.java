package org.uma.jmetal.algorithm.impl;

import java.util.List;
import org.uma.jmetal.algorithm.components.impl.archiving.RankingAndDiversityArchiving;
import org.uma.jmetal.algorithm.components.impl.diversity.KthNearestNeighbor;
import org.uma.jmetal.algorithm.components.impl.initialization.RandomInitialization;
import org.uma.jmetal.algorithm.components.impl.operator.selection.KTournamentSelection;
import org.uma.jmetal.algorithm.components.impl.progress.EvaluationsCountProgress;
import org.uma.jmetal.algorithm.components.impl.ranking.RawFitness;
import org.uma.jmetal.algorithm.components.impl.replacement.GenerationalReplacement;
import org.uma.jmetal.algorithm.components.impl.reproduction.GenerationalOneChildReproduction;
import org.uma.jmetal.algorithm.components.impl.selection.OnlyArchiveSelection;
import org.uma.jmetal.algorithm.components.impl.stoppingcondition.MaxEvaluationsCondition;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;

public class DynamicSPEA2<S extends Solution<?>> extends DefaultDynamicGeneticAlgorithm<S> {

    public DynamicSPEA2(Problem<S> problem, int maxEvaluations, int populationSize, int archiveSize,
            CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator,
            SolutionListEvaluator<S> evaluator) {
        super(problem,
                populationSize,
                new EvaluationsCountProgress(),
                new MaxEvaluationsCondition(maxEvaluations),
                new RandomInitialization<>(),
                new OnlyArchiveSelection<>(),
                new GenerationalOneChildReproduction<>(),
                new GenerationalReplacement<>(),
                new RankingAndDiversityArchiving<>(archiveSize, new RawFitness<>(), new KthNearestNeighbor<>(populationSize, archiveSize)),
                new KTournamentSelection(populationSize * 2, 2, new RawFitness<>(), new KthNearestNeighbor<>(populationSize, archiveSize)),
                crossoverOperator,
                mutationOperator,
                evaluator);
    }

}
