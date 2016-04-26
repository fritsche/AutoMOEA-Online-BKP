package org.uma.jmetal.solution.impl;

import java.util.HashMap;
import org.uma.jmetal.problem.impl.AbstractGrammaticalEvolutionProblem;
import org.uma.jmetal.solution.IntegerSolution;

/**
 * Created by Antonio J. Nebro on 03/09/14.
 */
public class VariableIntegerSolution extends AbstractVariableSolution<Integer, AbstractGrammaticalEvolutionProblem<?>> implements IntegerSolution {

    /**
     * Constructor
     */
    public VariableIntegerSolution(AbstractGrammaticalEvolutionProblem problem, int minCondons, int maxCondons) {
        super(problem);

        overallConstraintViolationDegree = 0.0;
        numberOfViolatedConstraints = 0;

        int numberOfCondons = randomGenerator.nextInt(minCondons, maxCondons);
        for (int i = 0; i < numberOfCondons; i++) {
            Integer value = randomGenerator.nextInt(getLowerBound(0), getUpperBound(0));
            addVariable(value);
        }

        for (int i = 0; i < problem.getNumberOfObjectives(); i++) {
            setObjective(i, Integer.MAX_VALUE);
        }
    }

    /**
     * Copy constructor
     */
    public VariableIntegerSolution(VariableIntegerSolution solution) {
        super(solution.problem);

        for (int i = 0; i < solution.getNumberOfVariables(); i++) {
            addVariable(solution.getVariableValue(i));
        }

        for (int i = 0; i < solution.getNumberOfObjectives(); i++) {
            setObjective(i, solution.getObjective(i));
        }

        overallConstraintViolationDegree = solution.overallConstraintViolationDegree;
        numberOfViolatedConstraints = solution.numberOfViolatedConstraints;

        attributes = new HashMap<Object, Object>(solution.attributes);
    }

    @Override
    public Integer getUpperBound(int index) {
        return problem.getUpperBound(index);
    }

    @Override
    public Integer getLowerBound(int index) {
        return problem.getLowerBound(index);
    }

    @Override
    public VariableIntegerSolution copy() {
        return new VariableIntegerSolution(this);
    }

    @Override
    public String getVariableValueString(int index) {
        return getVariableValue(index).toString();
    }
}
