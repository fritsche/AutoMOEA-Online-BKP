package org.uma.jmetal.algorithm.components.impl.reproduction;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.algorithm.components.ReproductionImplementation;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.solution.Solution;

public class GenerationalTwoChildrenReproduction<S extends Solution<?>> implements ReproductionImplementation<S> {

    @Override
    public List<S> reproduction(List<S> matingPopulation, int offspringSize, CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator) {
        List<S> offspringPopulation = new ArrayList<>(offspringSize);
        for (int i = 0; i < offspringSize; i += 2) {
            List<S> parents = new ArrayList<>(2);
            parents.add(matingPopulation.get(i % matingPopulation.size()));
            parents.add(matingPopulation.get((i + 1) % matingPopulation.size()));

            List<S> offspring;
            if (crossoverOperator != null) {
                offspring = crossoverOperator.execute(parents);
            } else {
                offspring = new ArrayList<>(2);
                offspring.add((S) parents.get(0).copy());
                offspring.add((S) parents.get(1).copy());
            }

            if (mutationOperator != null) {
                mutationOperator.execute(offspring.get(0));
                mutationOperator.execute(offspring.get(1));
            }

            offspringPopulation.add(offspring.get(0));
            offspringPopulation.add(offspring.get(1));
        }
        return offspringPopulation;
    }
    
    @Override
    public String toString() {
        return "Generational 2C";
    }

}
