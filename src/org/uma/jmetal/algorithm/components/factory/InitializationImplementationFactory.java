package org.uma.jmetal.algorithm.components.factory;

import org.uma.jmetal.algorithm.components.InitializationImplementation;
import org.uma.jmetal.algorithm.components.impl.initialization.ParallelDiversifiedInitialization;
import org.uma.jmetal.algorithm.components.impl.initialization.RandomInitialization;

public class InitializationImplementationFactory {

    public static final String RANDOM = "Random";
    public static final String PARALLEL_DIVERSIFIED_INITIALIZATION = "Parallel Diversified Initialization";

    public static InitializationImplementation createInitializationImplementation(String initialization) {
        if (initialization != null) {
            switch (initialization) {
                case RANDOM:
                    return new RandomInitialization();
                case PARALLEL_DIVERSIFIED_INITIALIZATION:
                    return new ParallelDiversifiedInitialization();
            }
        }
        return null;
    }

}
