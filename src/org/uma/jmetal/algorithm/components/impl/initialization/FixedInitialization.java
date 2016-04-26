package org.uma.jmetal.algorithm.components.impl.initialization;

import java.util.List;
import org.uma.jmetal.algorithm.components.InitializationImplementation;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;

public class FixedInitialization<S extends Solution<?>> implements InitializationImplementation<S> {

    private List<S> initialPopulation;

    public FixedInitialization(List<S> initialPopulation) {
        this.initialPopulation = initialPopulation;
    }

    public List<S> getInitialPopulation() {
        return initialPopulation;
    }

    public void setInitialPopulation(List<S> initialPopulation) {
        this.initialPopulation = initialPopulation;
    }

    @Override
    public List<S> createInitialPopulation(Problem<S> problem, int populationSize) {
        return initialPopulation;
    }

}
