package org.uma.jmetal.algorithm.components.impl.progress;

import org.uma.jmetal.algorithm.components.ProgressImplementation;

public class EvaluationsCountProgress implements ProgressImplementation {

    private int evaluations;

    public EvaluationsCountProgress() {
        this.initialize(0);
    }

    public EvaluationsCountProgress(int evaluations) {
        this.initialize(evaluations);
    }

    @Override
    public void initialize(int state) {
        this.setCurrentProgress(state);
    }

    @Override
    public void update(int state) {
        this.evaluations += state;
    }

    @Override
    public int getCurrentProgress() {
        return evaluations;
    }

    @Override
    public void setCurrentProgress(int state) {
        this.evaluations = state;
    }

}
