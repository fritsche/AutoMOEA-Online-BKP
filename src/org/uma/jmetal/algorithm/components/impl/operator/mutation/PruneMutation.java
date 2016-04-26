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
package org.uma.jmetal.algorithm.components.impl.operator.mutation;

import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.solution.impl.VariableIntegerSolution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

/**
 * @author Antonio J. Nebro
 * @version 1.0
 *
 * This class implements a bit flip mutation operator.
 */
public class PruneMutation implements MutationOperator<VariableIntegerSolution> {

    private double mutationProbability;
    private JMetalRandom randomGenerator;
    private int pruneIndex;

    /**
     * Constructor
     */
    public PruneMutation(double probability, int pruneIndex) {
        if (probability < 0) {
            throw new JMetalException("Mutation probability is negative: " + probability);
        }
        if (pruneIndex <= 0) {
            throw new JMetalException("Prune index is zero or negative: " + pruneIndex);
        }
        this.mutationProbability = probability;
        randomGenerator = JMetalRandom.getInstance();
        this.pruneIndex = pruneIndex;
    }

    /* Getter */
    public double getPruneProbability() {
        return mutationProbability;
    }

    /**
     * Execute() method
     */
    @Override
    public VariableIntegerSolution execute(VariableIntegerSolution solution) {
        if (null == solution) {
            throw new JMetalException("Null parameter");
        }

        doPrune(mutationProbability, solution);
        return solution;
    }

    /**
     * Perform the mutation operation
     *
     * @param probability Mutation setProbability
     * @param solution The solution to mutate
     */
    public void doPrune(double probability, VariableIntegerSolution solution) {
        if (randomGenerator.nextDouble() <= probability) {
            solution.prune(pruneIndex);
        }
    }
}
