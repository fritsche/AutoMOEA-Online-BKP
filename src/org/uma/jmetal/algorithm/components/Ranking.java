package org.uma.jmetal.algorithm.components;

import java.util.List;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.solutionattribute.impl.GenericSolutionAttribute;

public abstract class Ranking<S extends Solution<?>, V> extends GenericSolutionAttribute<S, V> {

    public void computeRanking(List<S> solutions){
        List<V> solutionsRanking = getSolutionsRanking(solutions);
        for (int i = 0; i < solutions.size(); i++) {
            S solution = solutions.get(i);
            V rank = solutionsRanking.get(i);
            this.setAttribute(solution, rank);
        }
    }
            
    protected abstract List<V> getSolutionsRanking(List<S> solutions);
}
