package org.uma.jmetal.algorithm.components;

import java.util.List;
import org.uma.jmetal.solution.Solution;

public interface ReplacementImplementation<S extends Solution<?>> {

    public List<S> replacement(List<S> population, List<S> offspringPopulation, int populationSize);

}
