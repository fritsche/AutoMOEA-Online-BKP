/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uma.jmetal.experiment;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.algorithm.impl.grammatical.GrammaticalEvolutionAlgorithm;
import org.uma.jmetal.grammaticalevolution.mapper.impl.SymbolicExpressionGrammarMapper;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.algorithm.components.impl.operator.crossover.SinglePointCrossoverVariableLength;
import org.uma.jmetal.algorithm.components.impl.operator.mutation.DuplicationMutation;
import org.uma.jmetal.algorithm.components.impl.operator.mutation.IntegerMutation;
import org.uma.jmetal.algorithm.components.impl.operator.mutation.PruneMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.impl.SymbolicExpressionGrammarProblem;
import org.uma.jmetal.solution.impl.VariableIntegerSolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;

/**
 *
 * @author thaina
 */
public class SymbolicExpressionGrammarExperiment {

    public static void main(String[] args) {

//X - X * X / (X + X + (X * X))
//1 = 0,666666667
//2 = 1,5
//3 = 2,4
//4 = 3,333333333
//5 = 4,285714286
        List<Double> testCases = new ArrayList<>();
        testCases.add(1.0);
        testCases.add(2.0);
        testCases.add(3.0);
        testCases.add(4.0);
        testCases.add(5.0);
        testCases.add(6.0);
        testCases.add(7.0);
        testCases.add(8.0);
        testCases.add(9.0);
        testCases.add(10.0);

        String expectedFunction = "X - X * X / ((X + X) * X)";

        SymbolicExpressionGrammarProblem problem = new SymbolicExpressionGrammarProblem("symbolicexpression.bnf", 5, 20, testCases, expectedFunction);
        CrossoverOperator crossover = new SinglePointCrossoverVariableLength(1);
        MutationOperator mutation = new IntegerMutation(0.1);
        SelectionOperator selection = new BinaryTournamentSelection();

        GrammaticalEvolutionAlgorithm algorithm = new GrammaticalEvolutionAlgorithm(
                problem,
                10000,
                100,
                crossover,
                mutation,
                selection,
                new PruneMutation(0.01, 5),
                new DuplicationMutation(0.01),
                new SequentialSolutionListEvaluator());
        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();
        VariableIntegerSolution solution = algorithm.getResult();
        long computingTime = algorithmRunner.getComputingTime();

        SymbolicExpressionGrammarMapper mapper = new SymbolicExpressionGrammarMapper();
        mapper.loadGrammar("symbolicexpression.bnf");

        System.out.println("Total time of execution: " + computingTime);
        System.out.println("Solution: " + solution.getObjective(0));
        System.out.println("Variables: " + mapper.interpret(solution));
    }
}
