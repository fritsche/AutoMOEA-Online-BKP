package org.uma.jmetal.algorithm.components;

import java.util.List;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.solution.Solution;

public interface SelectionImplementation<S extends Solution<?>> {

    public List<S> selection(List<S> population, List<S> archivePopulation, SelectionOperator<List<S>, List<S>> selectionOperator);

}
