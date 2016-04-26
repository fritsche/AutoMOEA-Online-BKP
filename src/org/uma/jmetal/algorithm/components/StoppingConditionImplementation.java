package org.uma.jmetal.algorithm.components;

public interface StoppingConditionImplementation {

    public boolean isStoppingConditionReached(long progress);

    public void setStoppingCondition(long maxProgress);

}
