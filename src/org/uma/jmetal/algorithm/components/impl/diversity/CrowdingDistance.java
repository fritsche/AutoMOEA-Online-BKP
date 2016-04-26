package org.uma.jmetal.algorithm.components.impl.diversity;

import java.util.List;
import org.uma.jmetal.algorithm.components.Diversity;
import org.uma.jmetal.solution.Solution;

public class CrowdingDistance<S extends Solution<?>> extends Diversity<S, Double> {

    private final org.uma.jmetal.util.solutionattribute.impl.CrowdingDistance<S> crowdingDistance;

    public CrowdingDistance() {
        this.crowdingDistance = new org.uma.jmetal.util.solutionattribute.impl.CrowdingDistance<>();
    }

    @Override
    protected List<Double> getSolutionsDiversity(List<S> solutions) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void computeDiversity(List<S> solutions) {
        crowdingDistance.computeDensityEstimator(solutions);
        for (S solution : solutions) {
            this.setAttribute(solution, crowdingDistance.getAttribute(solution) * -1);
        }
    }
    
    @Override
    public String toString() {
        return "CrowdingDistance";
    }

}
