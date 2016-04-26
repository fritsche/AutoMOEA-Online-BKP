//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU Lesser General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU Lesser General Public License for more details.
//
//  You should have received a copy of the GNU Lesser General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.
package org.uma.jmetal.algorithm.components.impl.operator.crossover;

import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.solution.PermutationSolution;

/**
 * @author Antonio J. Nebro
 * @version 1.0
 *
 * This class implements a single point crossover operator to permutation
 * representations.
 */
public class PermutationSinglePointCrossover implements CrossoverOperator<PermutationSolution<Integer>> {

    private double crossoverProbability;
    private JMetalRandom randomGenerator;

    /**
     * Constructor
     */
    public PermutationSinglePointCrossover(double crossoverProbability) {
        if (crossoverProbability < 0) {
            throw new JMetalException("Crossover probability is negative: " + crossoverProbability);
        }
        this.crossoverProbability = crossoverProbability;
        randomGenerator = JMetalRandom.getInstance();
    }

    /* Getter */
    public double getCrossoverProbability() {
        return crossoverProbability;
    }

    @Override
    public List<PermutationSolution<Integer>> execute(List<PermutationSolution<Integer>> solutions) {
        if (solutions == null) {
            throw new JMetalException("Null parameter");
        } else if (solutions.size() != 2) {
            throw new JMetalException("There must be two parents instead of " + solutions.size());
        }

        return doCrossover(crossoverProbability, solutions.get(0), solutions.get(1));
    }

    /**
     * Perform the crossover operation.
     *
     * @param probability Crossover setProbability
     * @param parent1 The first parent
     * @param parent2 The second parent
     * @return An array containing the two offspring
     */
    public List<PermutationSolution<Integer>> doCrossover(double probability, PermutationSolution parent1, PermutationSolution parent2) {
        List<PermutationSolution<Integer>> offspring = new ArrayList<>(2);
        offspring.add((PermutationSolution<Integer>) parent1.copy());
        offspring.add((PermutationSolution<Integer>) parent2.copy());

        if (randomGenerator.nextDouble() < probability) {
            List<List<Integer>> offspringNewArray = new ArrayList<>(2);
            offspringNewArray.add(new ArrayList<>());
            offspringNewArray.add(new ArrayList<>());

            // 1. Get the total number genes
            int totalNumberOfGenes = parent1.getNumberOfVariables();

            // 2. Calculate the point to make the crossover
            int crossoverPoint = randomGenerator.nextInt(0, totalNumberOfGenes - 2);

            // 3 - create the offsprings
            List<Integer> reorderedParent1 = new ArrayList<>();
            List<Integer> reorderedParent2 = new ArrayList<>();

            // 3.1 - create the first part of the offsprings
            for (int i = crossoverPoint + 1; i < totalNumberOfGenes; i++) {
                reorderedParent1.add(((PermutationSolution<Integer>) parent1).getVariableValue(i));
                reorderedParent2.add(((PermutationSolution<Integer>) parent2).getVariableValue(i));
            }

            for (int i = 0; i <= crossoverPoint; i++) {
                offspringNewArray.get(0).add(((PermutationSolution<Integer>) parent1).getVariableValue(i));
                offspringNewArray.get(1).add(((PermutationSolution<Integer>) parent2).getVariableValue(i));

                reorderedParent1.add(((PermutationSolution<Integer>) parent1).getVariableValue(i));
                reorderedParent2.add(((PermutationSolution<Integer>) parent2).getVariableValue(i));
            }

            // 3.1 - create the second part of the offsprings
            for (int i = 0; i < totalNumberOfGenes; i++) {
                if (!offspringNewArray.get(0).contains(reorderedParent2.get(i))) {
                    offspringNewArray.get(0).add(reorderedParent2.get(i));
                }
                if (!offspringNewArray.get(1).contains(reorderedParent1.get(i))) {
                    offspringNewArray.get(1).add(reorderedParent1.get(i));
                }
            }

            for (int i = 0; i < offspringNewArray.size(); i++) {
                for (int j = 0; j < offspringNewArray.get(i).size(); j++) {
                    Integer variable = offspringNewArray.get(i).get(j);
                    offspring.get(i).setVariableValue(j, variable);
                }
            }
        }

        return offspring;
    }

//    public static void main(String[] args) {
//        List<Integer> parent1 = new ArrayList<>();
//        List<Integer> parent2 = new ArrayList<>();
//
//        parent1.add(0);
//        parent1.add(1);
//        parent1.add(2);
//        parent1.add(3);
//        parent1.add(4);
//        parent1.add(5);
//        parent1.add(6);
//        parent1.add(7);
//        parent1.add(8);
//
//        parent2.add(0);
//        parent2.add(3);
//        parent2.add(7);
//        parent2.add(8);
//        parent2.add(2);
//        parent2.add(6);
//        parent2.add(5);
//        parent2.add(1);
//        parent2.add(4);
//
//        List<List<Integer>> offspring = new ArrayList<>(2);
//        offspring.add(new ArrayList<>());
//        offspring.add(new ArrayList<>());
//
//        JMetalRandom randomGenerator = JMetalRandom.getInstance();
//        // 1. Get the total number genes
//        int totalNumberOfGenes = parent1.size();
//
//        // 2. Calculate the point to make the crossover
////       int crossoverPoint = randomGenerator.nextInt(0, totalNumberOfGenes - 2);
//        int crossoverPoint = 2;
//
//        // 3 - create the offsprings
//        List<Integer> reorderedParent1 = new ArrayList<>();
//        List<Integer> reorderedParent2 = new ArrayList<>();
//
//        // 3.1 - create the first part of the offsprings
//        for (int i = crossoverPoint + 1; i < totalNumberOfGenes; i++) {
//            reorderedParent1.add(parent1.get(i));
//            reorderedParent2.add(parent2.get(i));
//        }
//
//        for (int i = 0; i <= crossoverPoint; i++) {
//            offspring.get(0).add(parent1.get(i));
//            offspring.get(1).add(parent2.get(i));
//
//            reorderedParent1.add(parent1.get(i));
//            reorderedParent2.add(parent2.get(i));
//        }
//
//        // 3.1 - create the second part of the offsprings
//        for (int i = 0; i < totalNumberOfGenes; i++) {
//            if (!offspring.get(0).contains(reorderedParent2.get(i))) {
//                offspring.get(0).add(reorderedParent2.get(i));
//            }
//            if (!offspring.get(1).contains(reorderedParent1.get(i))) {
//                offspring.get(1).add(reorderedParent1.get(i));
//            }
//        }
//
//        System.out.println("R1: " + reorderedParent1);
//        System.out.println("R2: " + reorderedParent2);
//
//        System.out.println("Point: " + crossoverPoint);
//        System.out.println("Offspring1: " + offspring.get(0));
//        System.out.println("Offspring2: " + offspring.get(1));
//    }

    @Override
    public String toString() {
        return "1P Crossover (" + this.crossoverProbability + ")";
    }

}
