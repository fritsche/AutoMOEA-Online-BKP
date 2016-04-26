package org.uma.jmetal.algorithm.components.impl.ranking;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.uma.jmetal.algorithm.components.Ranking;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.comparator.DominanceComparator;

public class DominanceRank<S extends Solution<?>> extends Ranking<S, Integer> {

    private final Comparator<Solution<?>> dominanceComparator;

    public DominanceRank() {
        this.dominanceComparator = new DominanceComparator<>();
    }

    @Override
    protected List<Integer> getSolutionsRanking(List<S> solutions) {
        int solutionsSize = solutions.size();

        List<Integer> rankings = new ArrayList<>();
        for (int i = 0; i < solutionsSize; i++) {
            rankings.add(0);
        }

        for (int i = 0; i < solutionsSize; i++) {
            for (int j = 0; j < solutionsSize; j++) {
                if (dominanceComparator.compare(solutions.get(j), solutions.get(i)) == -1) {
                    rankings.set(i, rankings.get(i) + 1);
                }
            }
        }

        return rankings;
    }
    
    @Override
    public String toString() {
        return "DominanceRank";
    }

}
