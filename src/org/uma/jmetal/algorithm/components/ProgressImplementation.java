package org.uma.jmetal.algorithm.components;

public interface ProgressImplementation {

    public void initialize(int state);

    public void update(int state);

    public int getCurrentProgress();

    public void setCurrentProgress(int state);

}
