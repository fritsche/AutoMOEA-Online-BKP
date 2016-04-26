package org.uma.jmetal.algorithm.components.impl.diversity;

import java.util.List;
import java.util.stream.Collectors;
import org.uma.jmetal.algorithm.components.Diversity;
import org.uma.jmetal.solution.Solution;

public class AdaptiveGrid<S extends Solution<?>> extends Diversity<S, Double> {

    private int bisections;
    private int objectives;

    public AdaptiveGrid(int numberOfObjectives) {
        this(5, numberOfObjectives);
    }

    public AdaptiveGrid(int bisections, int numberOfObjectives) {
        this.bisections = bisections;
        this.objectives = numberOfObjectives;
    }

    @Override
    protected List<Double> getSolutionsDiversity(List<S> solutions) {
        org.uma.jmetal.util.AdaptiveGrid grid = new org.uma.jmetal.util.AdaptiveGrid(bisections, objectives);
        grid.updateGrid(solutions);

        return solutions
                .stream()
                .mapToDouble((solution) -> (double) grid.getLocationDensity(grid.location(solution)))
                .boxed()
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "AdaptiveGrid";
    }
}
