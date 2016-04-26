package org.uma.jmetal.algorithm.components.factory;

import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.impl.crossover.PMXCrossover;
import org.uma.jmetal.algorithm.components.impl.operator.crossover.PermutationCycleCrossover;
import org.uma.jmetal.algorithm.components.impl.operator.crossover.PermutationSinglePointCrossover;
import org.uma.jmetal.algorithm.components.impl.operator.crossover.PermutationTwoPointsCrossover;

public class CrossoverOperatorFactory {

    public static final String TWO_POINTS = "Two Points Crossover";
    public static final String SINGLE_POINTS = "Single Point Crossover";
    public static final String PMX = "PMX Crossover";
    public static final String CYCLE = "Cycle Crossover";

    public static CrossoverOperator createCrossoverOperator(String crossoverOperator, double crossoverProbability) {
        if (crossoverOperator != null) {
            switch (crossoverOperator) {
                case TWO_POINTS:
                    return new PermutationTwoPointsCrossover(crossoverProbability);
                case SINGLE_POINTS:
                    return new PermutationSinglePointCrossover(crossoverProbability);
                case PMX:
                    return new PMXCrossover(crossoverProbability);
                case CYCLE:
                    return new PermutationCycleCrossover(crossoverProbability);
            }
        }
        return null;
    }

}
