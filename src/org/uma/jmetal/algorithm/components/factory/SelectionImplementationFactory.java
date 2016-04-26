package org.uma.jmetal.algorithm.components.factory;

import org.uma.jmetal.algorithm.components.SelectionImplementation;
import org.uma.jmetal.algorithm.components.impl.selection.OnlyArchiveSelection;
import org.uma.jmetal.algorithm.components.impl.selection.OnlyPopulationSelection;
import org.uma.jmetal.algorithm.components.impl.selection.PopulationAndArchiveSelection;

public class SelectionImplementationFactory {

    public static final String POPULATION = "Population";
    public static final String ARCHIVE = "Archive";
    public static final String ARCHIVE_AND_POPULATION = "Archive and Population";

    public static SelectionImplementation createSelectionImplementation(String selection) {
        if (selection != null) {
            switch (selection) {
                case POPULATION:
                    return new OnlyPopulationSelection();
                case ARCHIVE:
                    return new OnlyArchiveSelection();
                case ARCHIVE_AND_POPULATION:
                    return new PopulationAndArchiveSelection();
            }
        }
        return null;
    }

}
