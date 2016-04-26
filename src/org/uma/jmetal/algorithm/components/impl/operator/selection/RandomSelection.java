package org.uma.jmetal.algorithm.components.impl.operator.selection;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.solution.Solution;

public class RandomSelection<S extends Solution<?>> implements SelectionOperator<List<S>, List<S>> {

    private org.uma.jmetal.operator.impl.selection.RandomSelection<S> random;
    private int solutionsToSelect;

    public RandomSelection(int solutionsToSelect) {
        random = new org.uma.jmetal.operator.impl.selection.RandomSelection<>();
        this.solutionsToSelect = solutionsToSelect;
    }

    @Override
    public List<S> execute(List<S> source) {
        List<S> selected = new ArrayList<>();
        for (int i = 0; i < solutionsToSelect; i++) {
            selected.add(random.execute(source));
        }
        return selected;
    }

    @Override
    public String toString() {
        return "RandomSelection";
    }

}
