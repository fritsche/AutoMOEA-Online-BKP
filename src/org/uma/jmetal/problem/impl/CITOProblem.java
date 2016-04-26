package org.uma.jmetal.problem.impl;

import java.util.ArrayList;
import org.uma.jmetal.solution.PermutationSolution;
import org.uma.jmetal.util.CITOReader;

public class CITOProblem extends AbstractIntegerPermutationProblem {

    private int numberOfUnits;
    private ArrayList<Integer> aspects;
    private int[][] constraintMatrix;
    private int[][] dependencyMatrix;
    private int[][] attributeCouplingMatrix;
    private int[][] methodCouplingMatrix;
    private int[][] methodReturnTypeMatrix;
    private int[][] methodParamTypeMatrix;

    public CITOProblem(String filePath) {
        this(new CITOReader(filePath));
    }

    public CITOProblem(CITOReader problemReader) {
        this.numberOfUnits = problemReader.getNumberOfUnits();
        this.aspects = problemReader.getAspects();
        this.attributeCouplingMatrix = problemReader.getAttributeCouplingMatrix();
        this.constraintMatrix = problemReader.getConstraintMatrix();
        this.dependencyMatrix = problemReader.getDependencyMatrix();
        this.methodCouplingMatrix = problemReader.getMethodCouplingMatrix();
        this.methodParamTypeMatrix = problemReader.getMethodParamTypeMatrix();
        this.methodReturnTypeMatrix = problemReader.getMethodReturnTypeMatrix();
        setNumberOfVariables(numberOfUnits);
        setNumberOfObjectives(2);
        setName("CITO");
    }

    @Override
    public void evaluate(PermutationSolution<Integer> solution) {
        this.treatConstraintsAndRepairSolution(solution, constraintMatrix);

        double fitness0 = 0.0;
        double fitness1 = 0.0;

        //percorre o vetor de solucoes
        for (int i = 0; i < numberOfUnits; i++) {

            //pega o id da classe
            int x = solution.getVariableValue(i);

            //percorre as colunas da matrix de dependencia
            for (int k = 0; k < numberOfUnits; k++) {

                //verifica se existe dependencia de x para k
                if (dependencyMatrix[x][k] == 1) {
                    boolean verificador = false;

                    //verifica se a classe já exite
                    for (int j = 0; j <= i; j++) {
                        int y = solution.getVariableValue(j);
                        if (y == k) {
                            verificador = true;
                            break;
                        }
                    }

                    //adiciona os valores ao fitnesse se a classe não tiver sido testada ainda
                    if (!verificador) {
                        fitness0 += attributeCouplingMatrix[x][k];
                        fitness1 += methodCouplingMatrix[x][k];
                    }
                }
            }
        }

        solution.setObjective(0, fitness0);
        solution.setObjective(1, fitness1);
    }

    @Override
    public int getPermutationLength() {
        return numberOfUnits;
    }

    public void treatConstraintsAndRepairSolution(PermutationSolution<Integer> solution, int constraints[][]) {
        int[] array = new int[solution.getNumberOfVariables()];
        int size = array.length;

        for (int i = 0; i < array.length; i++) {
            int variable = solution.getVariableValue(i);
            array[i] = variable;
        }

        ArrayList subVector = new ArrayList();

        //System.out.println("Tamanho Vetor: " + size_);
        for (int indexSolution = 0; indexSolution < size; indexSolution++) {
            //pega o id da classe para buscar as restricoes
            int contraintClassId = array[indexSolution];
            boolean addInSubVector = true;
            //passa por todas as classes para verificar restricao com a classe atual
            for (int indexConstraint = 0; indexConstraint < constraints[contraintClassId].length; indexConstraint++) {
                //verifica se existe restricao
                if (constraints[contraintClassId][indexConstraint] == 1) {
                    //verifica se a classe exigida já apareceu anteriormente
                    int x = subVector.indexOf(indexConstraint);
                    if (x == -1) {
                        array = this.putEnd(array, indexSolution);
                        addInSubVector = false;
                        indexSolution--;
                        break;
                    }
                }
            }
            //adiciona o elemento no subVector
            if (addInSubVector) {
                subVector.add(array[indexSolution]);
            }
        }

        for (int i = 0; i < array.length; i++) {
            int variable = array[i];
            solution.setVariableValue(i, variable);
        }
    }

    public int[] putEnd(int haystack[], int index) {
        int temp = haystack[index];

        for (int i = index; i < haystack.length - 1; i++) {
            haystack[i] = haystack[i + 1];
        }

        haystack[haystack.length - 1] = temp;

        return haystack;
    }

}
