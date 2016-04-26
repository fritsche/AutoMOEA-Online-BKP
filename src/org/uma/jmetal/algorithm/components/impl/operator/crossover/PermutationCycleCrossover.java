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
 * This class implements a cycle crossover operator to permutation representations.
 */
public class PermutationCycleCrossover implements CrossoverOperator<PermutationSolution<Integer>> {

    private double crossoverProbability;
    private JMetalRandom randomGenerator;

    /**
     * Constructor
     */
    public PermutationCycleCrossover(double crossoverProbability) {
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

        int[] offspring1 = new int[parent1.getNumberOfVariables()];
        int[] offspring2 = new int[parent2.getNumberOfVariables()];
        List<Integer> constructedPositions = new ArrayList<>();

        if (randomGenerator.nextDouble() < probability) {
            int invert = 0;
            while (checkConstructedPositions(parent1, constructedPositions) != parent1.getNumberOfVariables()) {
                int initialPosition = checkConstructedPositions(parent1, constructedPositions);
                int position = checkConstructedPositions(parent1, constructedPositions);
                do {
                    if (invert % 2 == 0) {
                        offspring1[position] = (int) parent1.getVariableValue(position);
                        position = getMatchingGene(parent2, (int) parent1.getVariableValue(position), offspring2);
                    } else {
                        offspring2[position] = (int) parent1.getVariableValue(position);
                        position = getMatchingGene(parent2, (int) parent1.getVariableValue(position), offspring1);
                    }
                    constructedPositions.add(position);
                } while (position != initialPosition);
                invert++;
            }

            for (int i = 0; i < offspring1.length; i++) {
                offspring.get(0).setVariableValue(i, offspring1[i]);
            }

            for (int i = 0; i < offspring2.length; i++) {
                offspring.get(1).setVariableValue(i, offspring2[i]);
            }
        }

        return offspring;
    }

    private int checkConstructedPositions(PermutationSolution parent1, List<Integer> constructedPositions) {
        for (int i = 0; i < parent1.getNumberOfVariables(); i++) {
            if (!constructedPositions.contains(i)) {
                return i;
            }
        }
        return parent1.getNumberOfVariables();
    }

    private int getMatchingGene(PermutationSolution parent, int valueGene, int[] offspring) {
        for (int j = 0; j < parent.getNumberOfVariables(); j++) {
            if (parent.getVariableValue(j).equals(valueGene)) {
                offspring[j] = valueGene;
                return j;
            }
        }
        return 0;
    }

//    public static void main(String[] args) {
//
//        List<Integer> parent1 = new ArrayList<>();
//        parent1.add(4);
//        parent1.add(2);
//        parent1.add(7);
//        parent1.add(8);
//        parent1.add(5);
//        parent1.add(1);
//        parent1.add(3);
//        parent1.add(6);
//        parent1.add(9);
//
//        List<Integer> parent2 = new ArrayList<>();
//        parent2.add(1);
//        parent2.add(3);
//        parent2.add(4);
//        parent2.add(5);
//        parent2.add(9);
//        parent2.add(7);
//        parent2.add(8);
//        parent2.add(6);
//        parent2.add(2);
//
//        int[] offspring1 = new int[parent1.size()];
//        int[] offspring2 = new int[parent2.size()];
//        List<Integer> constructedPositions = new ArrayList<>();
//
//        int invert = 0;
//        while (checkConstructedPositions1(parent1, constructedPositions) != parent1.size()) {
//            int initialPosition = checkConstructedPositions1(parent1, constructedPositions);
//            int position = checkConstructedPositions1(parent1, constructedPositions);
//            do {
//                if (invert % 2 == 0) {
//                    offspring1[position] = (int) parent1.get(position);
//                    position = getMatchingGene1(parent2, (int) parent1.get(position), offspring2);
//                } else {
//                    offspring2[position] = (int) parent1.get(position);
//                    position = getMatchingGene1(parent2, (int) parent1.get(position), offspring1);
//                }
//                constructedPositions.add(position);
//            } while (position != initialPosition);
//            invert++;
//        }
//
//        //print results
//        System.out.println("Offspring 1");
//        for (int i = 0; i < offspring1.length; i++) {
//            System.out.println(offspring1[i]);
//        }
//
//        System.out.println("Offspring 2");
//        for (int i = 0; i < offspring2.length; i++) {
//            System.out.println(offspring2[i]);
//        }
//
//    }
//
//    private static int checkConstructedPositions1(List<Integer> parent1, List<Integer> constructedPositions) {
//        for (int i = 0; i < parent1.size(); i++) {
//            if (!constructedPositions.contains(i)) {
//                return i;
//            }
//        }
//        return parent1.size();
//    }
//
//    private static int getMatchingGene1(List<Integer> parent, int valueGene, int[] offspring) {
//        for (int j = 0; j < parent.size(); j++) {
//            if (parent.get(j).equals(valueGene)) {
//                offspring[j] = valueGene;
//                return j;
//            }
//        }
//        return 0;
//    }
    @Override
    public String toString() {
        return "Cycle Crossover (" + this.crossoverProbability + ")";
    }

}
