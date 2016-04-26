package org.uma.jmetal.algorithm.components.factory;

import org.uma.jmetal.algorithm.components.ReproductionImplementation;
import org.uma.jmetal.algorithm.components.impl.reproduction.GenerationalOneChildReproduction;
import org.uma.jmetal.algorithm.components.impl.reproduction.GenerationalTwoChildrenReproduction;
import org.uma.jmetal.algorithm.components.impl.reproduction.SteadyStateReproduction;

public class ReproductionImplementationFactory {

    public static final String STEADY_STATE = "Steady State";
    public static final String GENERATIONAL_TWO_CHILDREN = "Generational Two Children";
    public static final String GENERATIONAL_ONE_CHILD = "Generational One Child";

    public static ReproductionImplementation createSelectionImplementation(String reproduction) {
        if (reproduction != null) {
            switch (reproduction) {
                case STEADY_STATE:
                    return new SteadyStateReproduction();
                case GENERATIONAL_TWO_CHILDREN:
                    return new GenerationalTwoChildrenReproduction();
                case GENERATIONAL_ONE_CHILD:
                    return new GenerationalOneChildReproduction();
            }
        }
        return null;
    }

}
