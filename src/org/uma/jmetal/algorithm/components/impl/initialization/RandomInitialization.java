package org.uma.jmetal.algorithm.components.impl.initialization;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.algorithm.components.InitializationImplementation;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;

public class RandomInitialization<S extends Solution<?>> implements InitializationImplementation<S> {

    @Override
    public List<S> createInitialPopulation(Problem<S> problem, int populationSize) {
        List<S> population = new ArrayList<>(populationSize);
        for (int i = 0; i < populationSize; i++) {
            S newIndividual = problem.createSolution();
            population.add(newIndividual);
        }
        return population;
    }
    
    @Override
    public String toString() {
        return "Random";
    }

}
