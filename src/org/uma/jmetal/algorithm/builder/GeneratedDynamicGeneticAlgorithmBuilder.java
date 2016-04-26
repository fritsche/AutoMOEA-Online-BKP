package org.uma.jmetal.algorithm.builder;

import org.uma.jmetal.algorithm.components.factory.ArchivingImplementationFactory;
import org.uma.jmetal.algorithm.components.factory.CrossoverOperatorFactory;
import org.uma.jmetal.algorithm.components.factory.DiversityFactory;
import org.uma.jmetal.algorithm.components.factory.InitializationImplementationFactory;
import org.uma.jmetal.algorithm.components.factory.MutationOperatorFactory;
import org.uma.jmetal.algorithm.components.factory.RankingFactory;
import org.uma.jmetal.algorithm.components.factory.ReplacementImplementationFactory;
import org.uma.jmetal.algorithm.components.factory.ReproductionImplementationFactory;
import org.uma.jmetal.algorithm.components.factory.SelectionImplementationFactory;
import org.uma.jmetal.algorithm.components.factory.SelectionOperatorFactory;
import org.uma.jmetal.algorithm.components.factory.TrackingImplementationFactory;
import org.uma.jmetal.algorithm.impl.grammatical.GeneratedDynamicGeneticAlgorithm;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;

public class GeneratedDynamicGeneticAlgorithmBuilder<S extends Solution<?>> {

    private Problem<S> problem;
    private int populationSize;
    private int maxEvaluations;
    private String initialization;
    private String selectionSource;
    private String selectionOperator;
    private int solutionsToSelectAtEachGeneration;
    private int tournamentSize;
    private String selectionRanking;
    private String selectionDiversity;
    private String reproduction;
    private String crossoverOperator;
    private double crossoverProbability;
    private String mutationOperator;
    private double mutationProbability;
    private String replacement;
    private int elitismSize;
    private String replacementRanking;
    private String replacementDiversity;
    private String archiveRanking;
    private String archiveDiversity;
    private int archiveSize;
    private int bisections;
    private int numberOfObjectives;
    private String fileOutputPath;
    private String trackingImplementation;

    public GeneratedDynamicGeneticAlgorithmBuilder() {
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setProblem(Problem<S> problem) {
        this.problem = problem;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setMaxEvaluations(int maxEvaluations) {
        this.maxEvaluations = maxEvaluations;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setInitialization(String initialization) {
        this.initialization = initialization;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setSelectionSource(String selectionSource) {
        this.selectionSource = selectionSource;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setSelectionOperator(String selectionOperator) {
        this.selectionOperator = selectionOperator;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setSolutionsToSelectAtEachGeneration(int solutionsToSelectAtEachGeneration) {
        this.solutionsToSelectAtEachGeneration = solutionsToSelectAtEachGeneration;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setTournamentSize(int tournamentSize) {
        this.tournamentSize = tournamentSize;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setSelectionRanking(String selectionRanking) {
        this.selectionRanking = selectionRanking;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setSelectionDiversity(String selectionDiversity) {
        this.selectionDiversity = selectionDiversity;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setReproduction(String reproduction) {
        this.reproduction = reproduction;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setCrossoverOperator(String crossoverOperator) {
        this.crossoverOperator = crossoverOperator;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setMutationOperator(String mutationOperator) {
        this.mutationOperator = mutationOperator;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setReplacement(String replacement) {
        this.replacement = replacement;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setReplacementRanking(String replacementRanking) {
        this.replacementRanking = replacementRanking;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setReplacementDiversity(String replacementDiversity) {
        this.replacementDiversity = replacementDiversity;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setArchiveRanking(String archiveRanking) {
        this.archiveRanking = archiveRanking;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setArchiveDiversity(String archiveDiversity) {
        this.archiveDiversity = archiveDiversity;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setArchiveSize(int archiveSize) {
        this.archiveSize = archiveSize;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setBisections(int bisections) {
        this.bisections = bisections;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setElitismSize(int elitismSize) {
        this.elitismSize = elitismSize;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setCrossoverProbability(double crossoverProbability) {
        this.crossoverProbability = crossoverProbability;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setMutationProbability(double mutationProbability) {
        this.mutationProbability = mutationProbability;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setNumberOfObjectives(int numberOfObjectives) {
        this.numberOfObjectives = numberOfObjectives;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setTrackingImplementation(String trackingImplementation) {
        this.trackingImplementation = trackingImplementation;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setTrackingFileOutputPath(String fileOutputPath) {
        this.fileOutputPath = fileOutputPath;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithm<S> buildAlgorithm() {

//        System.out.println("Number of Objectives: " + numberOfObjectives);
//        System.out.println("Bisections: " + bisections);
//        System.out.println("Population Size: " + populationSize);
//        System.out.println("Initialization: " + initialization);
//        System.out.println("Selection Source: " + selectionSource);
//        System.out.println("Selection Operator: " + selectionOperator);
//        System.out.println("Tournament Size: " + tournamentSize);
//        System.out.println("Selection Ranking: " + selectionRanking);
//        System.out.println("Selection Diversity: " + selectionDiversity);
//        System.out.println("Mating Strategy: " + reproduction);
//        System.out.println("Solutions to Select: " + solutionsToSelectAtEachGeneration);
//        System.out.println("Crossover Operator: " + crossoverOperator);
//        System.out.println("Crossover Probability: " + crossoverProbability);
//        System.out.println("Mutation Operator: " + mutationOperator);
//        System.out.println("Mutation Probability: " + mutationProbability);
//        System.out.println("Replacement: " + replacement);
//        System.out.println("Elitism: " + elitismSize);
//        System.out.println("Replacement Ranking: " + replacementRanking);
//        System.out.println("Replacemente Diversity: " + replacementDiversity);
//        System.out.println("Archive Ranking: " + archiveRanking);
//        System.out.println("Archive Diversity: " + archiveDiversity);
//        System.out.println("Archive Size: " + archiveSize);
        return new GeneratedDynamicGeneticAlgorithm(
                problem,
                populationSize,
                maxEvaluations,
                InitializationImplementationFactory.createInitializationImplementation(initialization),
                SelectionImplementationFactory.createSelectionImplementation(selectionSource),
                SelectionOperatorFactory.createSelectionOperator(selectionOperator, tournamentSize, solutionsToSelectAtEachGeneration,
                        RankingFactory.createRanking(selectionRanking),
                        DiversityFactory.createRanking(selectionDiversity, populationSize, archiveSize, bisections, numberOfObjectives)),
                ReproductionImplementationFactory.createSelectionImplementation(reproduction),
                CrossoverOperatorFactory.createCrossoverOperator(crossoverOperator, crossoverProbability),
                MutationOperatorFactory.createMutationOperator(mutationOperator, mutationProbability),
                ReplacementImplementationFactory.createReplacementImplementation(replacement, elitismSize,
                        RankingFactory.createRanking(replacementRanking),
                        DiversityFactory.createRanking(replacementDiversity, populationSize, archiveSize, bisections, numberOfObjectives)),
                ArchivingImplementationFactory.createArchivingImplementation(archiveSize,
                        RankingFactory.createRanking(archiveRanking),
                        DiversityFactory.createRanking(archiveDiversity, populationSize, archiveSize, bisections, numberOfObjectives)),
                TrackingImplementationFactory.createTracking(trackingImplementation, fileOutputPath));
    }

}
