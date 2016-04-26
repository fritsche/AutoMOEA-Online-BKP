package org.uma.jmetal.algorithm.components.impl.diversity;

import java.util.List;
import org.uma.jmetal.algorithm.components.Diversity;
import org.uma.jmetal.solution.Solution;

public class HypervolumeContribution<S extends Solution<?>> extends Diversity<S, Double> {

    private org.uma.jmetal.util.solutionattribute.impl.HypervolumeContribution<S> hypervolume;

    public HypervolumeContribution() {
        hypervolume = new org.uma.jmetal.util.solutionattribute.impl.HypervolumeContribution();
    }

    @Override
    protected List<Double> getSolutionsDiversity(List<S> solutions) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void computeDiversity(List<S> solutions) {
        hypervolume.computeDensityEstimator(solutions);
        for (S solution : solutions) {
            this.setAttribute(solution, hypervolume.getAttribute(solution) * -1);
        }
    }
    
    @Override
    public String toString() {
        return "HV Contribution";
    }

}
