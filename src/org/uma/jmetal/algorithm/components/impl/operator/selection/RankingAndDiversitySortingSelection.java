package org.uma.jmetal.algorithm.components.impl.operator.selection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.uma.jmetal.algorithm.components.Diversity;
import org.uma.jmetal.algorithm.components.Ranking;
import org.uma.jmetal.comparator.impl.RankingAndDiversityComparator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalException;

public class RankingAndDiversitySortingSelection<S extends Solution<?>>
        implements SelectionOperator<List<S>, List<S>> {

    private Ranking<S, Integer> ranking;
    private Diversity<S, Double> diversity;
    private int solutionsToSelect;

    public RankingAndDiversitySortingSelection(int solutionsToSelect, Ranking<S, Integer> ranking, Diversity<S, Double> diversity) {
        this.ranking = ranking;
        this.diversity = diversity;
        this.solutionsToSelect = solutionsToSelect;
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

    public int getSolutionsToSelect() {
        return solutionsToSelect;
    }

    public void setSolutionsToSelect(int solutionsToSelect) {
        this.solutionsToSelect = solutionsToSelect;
    }

    @Override
    public List<S> execute(List<S> source) {
        if (null == source) {
            throw new JMetalException("The solution list is null");
        } else if (source.isEmpty()) {
            throw new JMetalException("The solution list is empty");
        }

        if (ranking != null) {
            ranking.computeRanking(source);
        }
        if (diversity != null) {
            diversity.computeDiversity(source);
        }

        RankingAndDiversityComparator<S> comparator = new RankingAndDiversityComparator(ranking, diversity);
        Collections.sort(source, comparator);

        List<S> selectedSolutions = new ArrayList<>();
        for (int i = 0; i < solutionsToSelect; i++) {
            selectedSolutions.add(source.get(i % source.size()));
        }

        return selectedSolutions;
    }

    @Override
    public String toString() {
        return "RankingAndDiversitySortingSelection{" + "ranking=" + ranking + ", diversity=" + diversity + '}';
    }

}
