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
 * This class implements a two points crossover operator to permutation representations.
 */
public class PermutationTwoPointsCrossover implements CrossoverOperator<PermutationSolution<Integer>> {

    private double crossoverProbability;
    private JMetalRandom randomGenerator;

    /**
     * Constructor
     */
    public PermutationTwoPointsCrossover(double crossoverProbability) {
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

            // 2. Calculate the points to make the crossover
            int firstPoint = randomGenerator.nextInt(0, totalNumberOfGenes - 2);
            int secondPoint = randomGenerator.nextInt(firstPoint + 1, totalNumberOfGenes - 1);

            List<Integer> firstSequenceOffspring0 = new ArrayList<>();
            List<Integer> firstSequenceOffspring1 = new ArrayList<>();
            List<Integer> secondSequenceOffspring0 = new ArrayList<>();
            List<Integer> secondSequenceOffspring1 = new ArrayList<>();
            List<Integer> thirdSequenceOffspring0 = new ArrayList<>();
            List<Integer> thirdSequenceOffspring1 = new ArrayList<>();

            // 3 - save the sequence between the points of the offsprings
            for (int i = firstPoint; i < secondPoint; i++) {
                secondSequenceOffspring0.add((Integer) parent1.getVariableValue(i));
                secondSequenceOffspring1.add((Integer) parent2.getVariableValue(i));
            }

            int sizeThirdSequence0 = 0;
            int sizeThirdSequence1 = 0;
            for (int i = secondPoint; i < totalNumberOfGenes; i++) {
                createSequence(secondSequenceOffspring0, parent2, i, thirdSequenceOffspring0);
                createSequence(secondSequenceOffspring1, parent1, i, thirdSequenceOffspring1);
                sizeThirdSequence0++;
                sizeThirdSequence1++;
            }

            for (int i = 0; i < secondPoint; i++) {
                if (sizeThirdSequence0 > thirdSequenceOffspring0.size()) {
                    createSequence(secondSequenceOffspring0, parent2, i, thirdSequenceOffspring0);
                } else {
                    createSequence(secondSequenceOffspring0, parent2, i, firstSequenceOffspring0);
                }

                if (sizeThirdSequence1 > thirdSequenceOffspring1.size()) {
                    createSequence(secondSequenceOffspring1, parent1, i, thirdSequenceOffspring1);
                } else {
                    createSequence(secondSequenceOffspring1, parent1, i, firstSequenceOffspring1);
                }
            }

            // 4 - create the offsprings
            for (int i = 0; i < firstSequenceOffspring0.size(); i++) {
                createOffsprings(offspringNewArray.get(0), firstSequenceOffspring0, i);
                createOffsprings(offspringNewArray.get(1), firstSequenceOffspring1, i);
            }

            for (int i = 0; i < secondSequenceOffspring0.size(); i++) {
                createOffsprings(offspringNewArray.get(0), secondSequenceOffspring0, i);
                createOffsprings(offspringNewArray.get(1), secondSequenceOffspring1, i);
            }

            for (int i = 0; i < thirdSequenceOffspring0.size(); i++) {
                createOffsprings(offspringNewArray.get(0), thirdSequenceOffspring0, i);
                createOffsprings(offspringNewArray.get(1), thirdSequenceOffspring1, i);
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

    private static void createOffsprings(List<Integer> offspring, List<Integer> secondSequenceOffspring, int i) {
        offspring.add(secondSequenceOffspring.get(i));
    }

    private static void createSequence(List<Integer> firstSequence, PermutationSolution<Integer> parent, int i, List<Integer> newSequence) {
        if (!firstSequence.contains(parent.getVariableValue(i))) {
            newSequence.add(parent.getVariableValue(i));
        }
    }

    //test
//    public static void main(String[] args) {
//        List<Integer> parent1 = new ArrayList<>();
//        List<Integer> parent2 = new ArrayList<>();
//
//        parent1.add(1);
//        parent1.add(2);
//        parent1.add(3);
//        parent1.add(4);
//        parent1.add(5);
//        parent1.add(6);
//        parent1.add(7);
//        parent1.add(8);
//        parent1.add(9);
//
//        parent2.add(9);
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
//        // 2. Calculate the points to make the crossover
//        int firstPoint = randomGenerator.nextInt(0, totalNumberOfGenes - 2);
//        int secondPoint = randomGenerator.nextInt(firstPoint + 1, totalNumberOfGenes - 1);
//
//        List<Integer> firstSequenceOffspring0 = new ArrayList<>();
//        List<Integer> firstSequenceOffspring1 = new ArrayList<>();
//        List<Integer> secondSequenceOffspring0 = new ArrayList<>();
//        List<Integer> secondSequenceOffspring1 = new ArrayList<>();
//        List<Integer> thirdSequenceOffspring0 = new ArrayList<>();
//        List<Integer> thirdSequenceOffspring1 = new ArrayList<>();
//
//        // 3 - save the sequence between the points of the offsprings
//        for (int i = firstPoint; i < secondPoint; i++) {
//            secondSequenceOffspring0.add(parent1.get(i));
//            secondSequenceOffspring1.add(parent2.get(i));
//        }
//
//        int sizeThirdSequence0 = 0;
//        int sizeThirdSequence1 = 0;
//        for (int i = secondPoint; i < totalNumberOfGenes; i++) {
//            createSequence(secondSequenceOffspring0, parent2, i, thirdSequenceOffspring0);
//            createSequence(secondSequenceOffspring1, parent1, i, thirdSequenceOffspring1);
//            sizeThirdSequence0++;
//            sizeThirdSequence1++;
//        }
//
//        for (int i = 0; i < secondPoint; i++) {
//            if (sizeThirdSequence0 > thirdSequenceOffspring0.size()) {
//                createSequence(secondSequenceOffspring0, parent2, i, thirdSequenceOffspring0);
//            } else {
//                createSequence(secondSequenceOffspring0, parent2, i, firstSequenceOffspring0);
//            }
//
//            if (sizeThirdSequence1 > thirdSequenceOffspring1.size()) {
//                createSequence(secondSequenceOffspring1, parent1, i, thirdSequenceOffspring1);
//            } else {
//                createSequence(secondSequenceOffspring1, parent1, i, firstSequenceOffspring1);
//            }
//        }
//
//        // 4 - create the offsprings
//        for (int i = 0; i < firstSequenceOffspring0.size(); i++) {
//            createOffsprings(offspring.get(0), firstSequenceOffspring0, i);
//            createOffsprings(offspring.get(1), firstSequenceOffspring1, i);
//        }
//
//        for (int i = 0; i < secondSequenceOffspring0.size(); i++) {
//            createOffsprings(offspring.get(0), secondSequenceOffspring0, i);
//            createOffsprings(offspring.get(1), secondSequenceOffspring1, i);
//        }
//
//        for (int i = 0; i < thirdSequenceOffspring0.size(); i++) {
//            createOffsprings(offspring.get(0), thirdSequenceOffspring0, i);
//            createOffsprings(offspring.get(1), thirdSequenceOffspring1, i);
//        }
//
//        System.out.println("1: " + firstSequenceOffspring0);
//        System.out.println("2: " + secondSequenceOffspring0);
//        System.out.println("3: " + thirdSequenceOffspring0);
//
//        System.out.println("First Point: " + firstPoint);
//        System.out.println("Second Point: " + secondPoint);
//        System.out.println("Offspring1: " + offspring.get(0));
//        System.out.println("Offspring2: " + offspring.get(1));
//    }
//
//    private static void createSequence(List<Integer> firstSequence, List<Integer> parent, int i, List<Integer> newSequence) {
//        if (!firstSequence.contains(parent.get(i))) {
//            newSequence.add(parent.get(i));
//        }
//    }
    @Override
    public String toString() {
        return "2P Crossover (" + this.crossoverProbability + ")";
    }
}
