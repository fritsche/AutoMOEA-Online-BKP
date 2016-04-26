package org.uma.jmetal.algorithm.components.impl.reproduction;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.algorithm.components.ReproductionImplementation;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.solution.Solution;

public class SteadyStateReproduction<S extends Solution<?>> implements ReproductionImplementation<S> {

    @Override
    public List<S> reproduction(List<S> matingPopulation, int offspringSize, CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator) {
        List<S> offspringPopulation = new ArrayList<>(offspringSize);

        List<S> parents = new ArrayList<>(2);
        parents.add(matingPopulation.get(0));
        parents.add(matingPopulation.get(1));

        List<S> offspring;
        if (crossoverOperator != null) {
            offspring = crossoverOperator.execute(parents);
        } else {
            offspring = new ArrayList<>(1);
            offspring.add((S) parents.get(0).copy());
        }

        if (mutationOperator != null) {
            mutationOperator.execute(offspring.get(0));
        }

        offspringPopulation.add(offspring.get(0));

        return offspringPopulation;
    }
    
    @Override
    public String toString() {
        return "SteadyState";
    }

}
