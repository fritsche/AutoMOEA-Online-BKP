package org.uma.jmetal.algorithm.components.impl.stoppingcondition;

import org.uma.jmetal.algorithm.components.StoppingConditionImplementation;

public class MaxEvaluationsCondition implements StoppingConditionImplementation {

    private long maxEvaluations;

    public MaxEvaluationsCondition(long maxEvaluations) {
        this.maxEvaluations = maxEvaluations;
    }

    @Override
    public boolean isStoppingConditionReached(long progress) {
        return progress >= this.maxEvaluations;
    }

    public long getMaxEvaluations() {
        return maxEvaluations;
    }

    @Override
    public void setStoppingCondition(long maxProgress) {
        this.maxEvaluations = maxProgress;
    }

}
