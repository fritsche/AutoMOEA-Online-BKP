package org.uma.jmetal.algorithm.components;

import java.util.List;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.solutionattribute.impl.GenericSolutionAttribute;

public abstract class Diversity<S extends Solution<?>, V> extends GenericSolutionAttribute<S, V> {

    public void computeDiversity(List<S> solutions){
        List<V> solutionsDiversity = getSolutionsDiversity(solutions);
        for (int i = 0; i < solutions.size(); i++) {
            S solution = solutions.get(i);
            V diversity = solutionsDiversity.get(i);
            this.setAttribute(solution, diversity);
        }
    }
            
    protected abstract List<V> getSolutionsDiversity(List<S> solutions);
}
