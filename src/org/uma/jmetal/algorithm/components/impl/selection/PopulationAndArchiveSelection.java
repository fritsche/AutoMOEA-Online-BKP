package org.uma.jmetal.algorithm.components.impl.selection;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.algorithm.components.SelectionImplementation;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.solution.Solution;

public class PopulationAndArchiveSelection<S extends Solution<?>> implements SelectionImplementation<S> {

    @Override
    public List<S> selection(List<S> population, List<S> archivePopulation, SelectionOperator<List<S>, List<S>> selectionOperator) {
        List<S> union = new ArrayList<>();
        union.addAll(population);
        union.addAll(archivePopulation);
        return selectionOperator.execute(union);
    }

    @Override
    public String toString() {
        return "ArchiveAndPopulation";
    }

}
