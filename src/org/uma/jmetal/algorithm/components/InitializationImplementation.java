package org.uma.jmetal.algorithm.components;

import java.util.List;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;

public interface InitializationImplementation<S extends Solution<?>> {

    public List<S> createInitialPopulation(Problem<S> problem, int populationSize);

}
