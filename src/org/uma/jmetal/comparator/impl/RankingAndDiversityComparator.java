package org.uma.jmetal.comparator.impl;

import java.util.Comparator;
import org.uma.jmetal.algorithm.components.Diversity;
import org.uma.jmetal.algorithm.components.Ranking;
import org.uma.jmetal.solution.Solution;

public class RankingAndDiversityComparator<S extends Solution<?>> implements Comparator<S> {

    private Ranking<S, Integer> ranking;
    private Diversity<S, Double> diversity;

    public RankingAndDiversityComparator(Ranking<S, Integer> ranking, Diversity<S, Double> diversity) {
        this.ranking = ranking;
        this.diversity = diversity;
    }

    public Ranking<S, Integer> getRanking() {
        return ranking;
    }

    public void setRanking(Ranking<S, Integer> ranking) {
        this.ranking = ranking;
    }

    public Diversity<S, Double> getDiversity() {
        return diversity;
    }

    public void setDiversity(Diversity<S, Double> diversity) {
        this.diversity = diversity;
    }

    @Override
    public int compare(S o1, S o2) {
        int result = compareRanking(o1, o2);
        if (result == 0) {
            result = compareDiversity(o1, o2);
        }
        return result;
    }

    private int compareRanking(S o1, S o2) {
        if (ranking != null) {
            if (ranking.getAttribute(o1) < ranking.getAttribute(o2)) {
                return -1;
            } else if (ranking.getAttribute(o1) > ranking.getAttribute(o2)) {
                return 1;
            }
        }
        return 0;
    }

    private int compareDiversity(S o1, S o2) {
        if (diversity != null) {
            if (diversity.getAttribute(o1) < diversity.getAttribute(o2)) {
                return -1;
            } else if (diversity.getAttribute(o1) > diversity.getAttribute(o2)) {
                return 1;
            }
        }
        return 0;
    }

}
