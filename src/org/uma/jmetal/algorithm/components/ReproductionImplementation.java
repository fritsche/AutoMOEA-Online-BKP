package org.uma.jmetal.algorithm.components;

import java.util.List;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.solution.Solution;

public interface ReproductionImplementation<S extends Solution<?>> {

    public List<S> reproduction(List<S> matingPopulation, int offspringSize, CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator);

}
