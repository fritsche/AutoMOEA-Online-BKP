package org.uma.jmetal.algorithm;

import org.uma.jmetal.algorithm.impl.AbstractGeneticAlgorithm;
import org.uma.jmetal.algorithm.components.impl.operator.mutation.DuplicationMutation;
import org.uma.jmetal.algorithm.components.impl.operator.mutation.PruneMutation;
import org.uma.jmetal.solution.impl.VariableIntegerSolution;

/**
 * Created by ajnebro on 26/10/14.
 */
public abstract class AbstractGrammaticalEvolutionAlgorithm<S extends VariableIntegerSolution, R> extends AbstractGeneticAlgorithm<VariableIntegerSolution, R> {

    protected PruneMutation pruneMutationOperator;
    protected DuplicationMutation duplicationMutationOperator;

}
