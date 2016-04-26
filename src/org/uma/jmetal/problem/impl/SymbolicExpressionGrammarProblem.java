/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uma.jmetal.problem.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.uma.jmetal.grammaticalevolution.mapper.impl.SymbolicExpressionGrammarMapper;
import org.uma.jmetal.solution.impl.VariableIntegerSolution;

/**
 *
 * @author thaina
 */
public class SymbolicExpressionGrammarProblem extends AbstractGrammaticalEvolutionProblem<String> {

    private final List<Double> testCases;
    private final String expectedFunction;

    public SymbolicExpressionGrammarProblem(String file, int minCondons, int maxCondons, List<Double> testCases, String expectedFunction) {
        super(minCondons, maxCondons, new SymbolicExpressionGrammarMapper(), file);
        this.testCases = testCases;
        this.expectedFunction = expectedFunction;
        setNumberOfObjectives(1);
    }

    @Override
    public void evaluate(VariableIntegerSolution solution) {
        String function = mapper.interpret(solution.getVariables());
        double fitness = calculate(function);
        solution.setObjective(0, fitness);
    }

    public double calculate(String function) {
        double fitness = 0;
        try {

            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("js");

            for (Double testCase : testCases) {
                String genFunction = function.replaceAll("X", testCase.toString());
                String expFunction = expectedFunction.replaceAll("X", testCase.toString());
                double result = (double) engine.eval(genFunction);
                double expectedResult = (double) engine.eval(expFunction);
                double diff = Math.abs(expectedResult - result);
                fitness += diff;
            }

        } catch (ScriptException ex) {
            Logger.getLogger(SymbolicExpressionGrammarProblem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fitness;
    }
}
