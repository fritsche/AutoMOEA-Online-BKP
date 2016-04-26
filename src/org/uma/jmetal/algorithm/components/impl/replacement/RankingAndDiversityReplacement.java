package org.uma.jmetal.algorithm.components.impl.replacement;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.algorithm.components.Diversity;
import org.uma.jmetal.algorithm.components.Ranking;
import org.uma.jmetal.algorithm.components.ReplacementImplementation;
import org.uma.jmetal.algorithm.components.impl.archiving.RankingAndDiversityArchiving;
import org.uma.jmetal.solution.Solution;

public class RankingAndDiversityReplacement<S extends Solution<?>> implements ReplacementImplementation<S> {

    private Ranking<S, Integer> ranking;
    private Diversity<S, Double> diversity;

    public RankingAndDiversityReplacement(Ranking<S, Integer> ranking, Diversity<S, Double> diversity) {
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
    public List<S> replacement(List<S> population, List<S> offspringPopulation, int populationSize) {
        RankingAndDiversityArchiving<S> archive = new RankingAndDiversityArchiving(populationSize, ranking, diversity);

        ArrayList<S> union = new ArrayList<>();
        union.addAll(population);
        union.addAll(offspringPopulation);
        archive.updateArchive(union);

        return archive.getArchive();
    }

    @Override
    public String toString() {
        return "RankingAndDiversityReplacement{" + "ranking=" + ranking + ", diversity=" + diversity + '}';
    }

}
