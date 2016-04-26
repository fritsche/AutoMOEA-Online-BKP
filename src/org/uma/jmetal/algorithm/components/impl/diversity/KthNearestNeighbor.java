package org.uma.jmetal.algorithm.components.impl.diversity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.uma.jmetal.algorithm.components.Diversity;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.SolutionListUtils;

public class KthNearestNeighbor<S extends Solution<?>> extends Diversity<S, Double> {

    private final int k;

    public KthNearestNeighbor() {
        this(1);
    }

    public KthNearestNeighbor(int k) {
        this.k = k;
    }

    public KthNearestNeighbor(int populationSize, int archiveSize) {
        this(StrictMath.round((float) StrictMath.sqrt(populationSize + archiveSize)));
    }

    @Override
    protected List<Double> getSolutionsDiversity(List<S> solutions) {
        List<Double> distances = new ArrayList<>();

        double[][] distanceMatrix = SolutionListUtils.distanceMatrix(solutions);
        for (int i = 0; i < solutions.size(); i++) {
            Arrays.sort(distanceMatrix[i]);
            double kDistance = 1.0 / (distanceMatrix[i][solutions.size() <= k ? solutions.size() - 1 : k] + 2.0);
            distances.add(kDistance);
        }

        return distances;
    }

    @Override
    public String toString() {
        return "KthNearestNeighbor";
    }

}
