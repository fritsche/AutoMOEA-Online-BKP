//  ScrambleMutation
//  Author: Thainá Mariani
package org.uma.jmetal.algorithm.components.impl.operator.mutation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.solution.PermutationSolution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

/**
 * This class implements a scramble mutation. The solution type of the solution must be Permutation.
 */
public class ScrambleMutation<T> implements MutationOperator<PermutationSolution<T>> {

    private double mutationProbability;
    private JMetalRandom randomGenerator;

    /**
     * Constructor
     */
    public ScrambleMutation(double mutationProbability) {
        if ((mutationProbability < 0) || (mutationProbability > 1)) {
            throw new JMetalException("Mutation probability value invalid: " + mutationProbability);
        }
        this.mutationProbability = mutationProbability;
        randomGenerator = JMetalRandom.getInstance();
    }

    /**
     * Execute() method
     */
    @Override
    public PermutationSolution<T> execute(PermutationSolution<T> solution) {
        if (null == solution) {
            throw new JMetalException("Null parameter");
        }

        doMutation(solution);
        return solution;
    }

    /**
     * Performs the operation
     */
    public void doMutation(PermutationSolution<T> solution) {
        int permutationLength;
        permutationLength = solution.getNumberOfVariables();

        if ((permutationLength != 0) && (permutationLength != 1)) {
            if (randomGenerator.nextDouble() < mutationProbability) {

                int pos1 = randomGenerator.nextInt(0, permutationLength - 1);
                int pos2 = randomGenerator.nextInt(0, permutationLength - 1);

                while (pos1 == pos2) {
                    if (pos1 == (permutationLength - 1)) {
                        pos2 = randomGenerator.nextInt(0, permutationLength - 2);
                    } else {
                        pos2 = randomGenerator.nextInt(pos1, permutationLength - 1);
                    }
                }

                if (pos1 > pos2) {
                    int aux = pos2;
                    pos2 = pos1;
                    pos1 = aux;
                }

                List<T> auxiliarList = new ArrayList<>();
                for (int i = pos1; i <= pos2; i++) {
                    auxiliarList.add(solution.getVariableValue(i));
                }

                Collections.shuffle(auxiliarList);

                int j = 0;
                for (int i = pos1; i <= pos2; i++) {
                    solution.setVariableValue(i, auxiliarList.get(j));
                    j++;
                }
            }
        }
    }

//tests
//    public static void main(String[] args) {
//        List<Integer> solution = new ArrayList<>();
//        solution.add(3);
//        solution.add(2);
//        solution.add(4);
//        solution.add(5);
//        solution.add(9);
//        solution.add(1);
//        solution.add(6);
//        solution.add(7);
//        solution.add(8);
//
//        System.out.println("Solution Before:");
//        for (int i = 0; i < solution.size(); i++) {
//            System.out.println(i + ": " + solution.get(i));
//        }
//        int permutationLength;
//        permutationLength = solution.size();
//
//        JMetalRandom randomGenerator2 = JMetalRandom.getInstance();
//
//        if ((permutationLength != 0) && (permutationLength != 1)) {
//            if (randomGenerator2.nextDouble() < 1) {
//
//                int pos1 = randomGenerator2.nextInt(0, permutationLength - 1);
//                int pos2 = randomGenerator2.nextInt(0, permutationLength - 1);
//
//                while (pos1 == pos2) {
//                    if (pos1 == (permutationLength - 1)) {
//                        pos2 = randomGenerator2.nextInt(0, permutationLength - 2);
//                    } else {
//                        pos2 = randomGenerator2.nextInt(pos1, permutationLength - 1);
//                    }
//                }
//
//                System.out.println("pos1: " + pos1);
//                System.out.println("pos2: " + pos2);
//
//                if (pos1 > pos2) {
//                    int aux = pos2;
//                    pos2 = pos1;
//                    pos1 = aux;
//                }
//
//                List<Integer> auxiliarList = new ArrayList<>();
//                for (int i = pos1; i <= pos2; i++) {
//                    auxiliarList.add(solution.get(i));
//                }
//
//                Collections.shuffle(auxiliarList);
//
//                int j = 0;
//                for (int i = pos1; i <= pos2; i++) {
//                    solution.set(i, auxiliarList.get(j));
//                    j++;
//                }
//
//                System.out.println("Solution After:");
//                for (int i = 0; i < solution.size(); i++) {
//                    System.out.println(i + ": " + solution.get(i));
//                }
//            }
//        }
//    }
    @Override
    public String toString() {
        return "Scramble Mutation (" + this.mutationProbability + ")";
    }
}
