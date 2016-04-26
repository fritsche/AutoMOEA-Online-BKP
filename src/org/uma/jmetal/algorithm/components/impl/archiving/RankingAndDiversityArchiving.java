package org.uma.jmetal.algorithm.components.impl.archiving;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.algorithm.components.ArchivingImplementation;
import org.uma.jmetal.algorithm.components.Diversity;
import org.uma.jmetal.algorithm.components.Ranking;
import org.uma.jmetal.comparator.impl.RankingAndDiversityComparator;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.SolutionListUtils;

public class RankingAndDiversityArchiving<S extends Solution<?>> implements ArchivingImplementation<S> {

    private Ranking<S, Integer> ranking;
    private Diversity<S, Double> diversity;
    private int archiveSize;
    private List<S> solutions;

    public RankingAndDiversityArchiving(int archiveSize, Ranking<S, Integer> ranking, Diversity<S, Double> diversity) {
        this.ranking = ranking;
        this.diversity = diversity;
        this.archiveSize = archiveSize;
        this.solutions = new ArrayList<>();
    }

    @Override
    public int updateArchive(List<S> population) {
        RankingAndDiversityComparator comparator = new RankingAndDiversityComparator(ranking, diversity);

        List<S> allSolutions = new ArrayList<>();
        allSolutions.addAll(solutions);
        allSolutions.addAll(population);

        if (ranking != null) {
            ranking.computeRanking(allSolutions);
        }
        if (diversity != null) {
            diversity.computeDiversity(allSolutions);
        }

        allSolutions.sort(comparator);
        solutions.clear();

        List<S> nondominatedSolutions = SolutionListUtils.getNondominatedSolutions(allSolutions);
        solutions.addAll(nondominatedSolutions);

        int remaining = archiveSize - solutions.size();
        if (remaining > 0) {
            allSolutions.removeAll(nondominatedSolutions);
            for (int i = 0; i < remaining && !allSolutions.isEmpty(); i++) {
                solutions.add(allSolutions.get(0));
                allSolutions.remove(0);
            }
        } else if (remaining < 0) {
            solutions.sort(comparator);
            for (int i = 0; i > remaining; i--) {
                solutions.remove(solutions.size() - 1);
            }
        }
        return (int) (solutions.size() - population.stream().filter(solution -> solutions.contains(solution)).count());
    }

    @Override
    public List<S> getArchive() {
        return solutions;
    }

    @Override
    public String toString() {
        return "RankingAndDiversityArchiving{" + "ranking=" + ranking + ", diversity=" + diversity + ", size = " + archiveSize + "}";
    }

}
