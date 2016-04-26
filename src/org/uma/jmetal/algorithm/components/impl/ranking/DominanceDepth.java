package org.uma.jmetal.algorithm.components.impl.ranking;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.algorithm.components.Ranking;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.SolutionListUtils;

public class DominanceDepth<S extends Solution<?>> extends Ranking<S, Integer> {

    public DominanceDepth() {
    }

    @Override
    protected List<Integer> getSolutionsRanking(List<S> solutions) {
        int solutionsSize = solutions.size();

        List<Integer> rankings = new ArrayList<>();
        for (int i = 0; i < solutionsSize; i++) {
            rankings.add(0);
        }

        List<S> manipulatingPopulation = new ArrayList(solutions);
        do {
            List<S> nonDominated = SolutionListUtils.getNondominatedSolutions(manipulatingPopulation);
            for (int i = 0; i < solutions.size(); i++) {
                S solution = solutions.get(i);
                if (!nonDominated.contains(solution) && manipulatingPopulation.contains(solution)) {
                    rankings.set(i, rankings.get(i) + 1);
                }
            }
            manipulatingPopulation.removeAll(nonDominated);
        } while (!manipulatingPopulation.isEmpty());

        return rankings;
    }
    
    @Override
    public String toString() {
        return "DominanceDepth";
    }

}
