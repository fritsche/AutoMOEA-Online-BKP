package org.uma.jmetal.algorithm.components.impl.operator.selection;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.algorithm.components.Diversity;
import org.uma.jmetal.algorithm.components.Ranking;
import org.uma.jmetal.comparator.impl.RankingAndDiversityComparator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.selection.TournamentSelection;
import org.uma.jmetal.solution.Solution;

public class KTournamentSelection<S extends Solution<?>> implements SelectionOperator<List<S>, List<S>> {

    private int numberOfTournaments;
    private int solutionsToSelect;
    private Ranking<S, Integer> ranking;
    private Diversity<S, Double> diversity;

    public KTournamentSelection(int solutionsToSelect, int numberOfTournaments, Ranking<S, Integer> ranking, Diversity<S, Double> diversity) {
        this.numberOfTournaments = numberOfTournaments;
        this.solutionsToSelect = solutionsToSelect;
        this.ranking = ranking;
        this.diversity = diversity;
    }

    public int getNumberOfTournaments() {
        return numberOfTournaments;
    }

    public void setNumberOfTournaments(int numberOfTournaments) {
        this.numberOfTournaments = numberOfTournaments;
    }

    public int getSolutionsToSelect() {
        return solutionsToSelect;
    }

    public void setSolutionsToSelect(int solutionsToSelect) {
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

    @Override
    public List<S> execute(List<S> source) {
        List<S> selectedSolutions = new ArrayList<>();

        if (ranking != null) {
            ranking.computeRanking(source);
        }
        if (diversity != null) {
            diversity.computeDiversity(source);
        }

        RankingAndDiversityComparator<S> comparator = new RankingAndDiversityComparator(ranking, diversity);
        TournamentSelection<S> tournament = new TournamentSelection(comparator, numberOfTournaments);

        for (int i = 0; i < solutionsToSelect; i++) {
            selectedSolutions.add(tournament.execute(source));
        }

        return selectedSolutions;
    }

    @Override
    public String toString() {
        return "KTournamentSelection{" + "numberOfTournaments=" + numberOfTournaments + ", ranking=" + ranking + ", diversity=" + diversity + '}';
    }

}
