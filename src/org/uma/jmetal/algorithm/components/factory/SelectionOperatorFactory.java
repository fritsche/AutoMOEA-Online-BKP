package org.uma.jmetal.algorithm.components.factory;

import org.uma.jmetal.algorithm.components.Diversity;
import org.uma.jmetal.algorithm.components.Ranking;
import org.uma.jmetal.algorithm.components.impl.operator.selection.KTournamentSelection;
import org.uma.jmetal.algorithm.components.impl.operator.selection.RandomSelection;
import org.uma.jmetal.algorithm.components.impl.operator.selection.RankingAndDiversitySortingSelection;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.algorithm.components.impl.operator.selection.RouletteWheelSelection;

public class SelectionOperatorFactory {

    public static final String K_TOURNAMENT = "K Tournament";
    public static final String RANDOM = "Random";
    public static final String ROULETTE_WHEEL = "Roulette Wheel";
    public static final String RANKING = "Ranking";

    public static SelectionOperator createSelectionOperator(String selectionOperator, int torunamentSize, int solutionsToSelect, Ranking selectionRanking, Diversity selectionDiversity) {
        if (selectionOperator != null) {
            switch (selectionOperator) {
                case K_TOURNAMENT:
                    return new KTournamentSelection(solutionsToSelect, torunamentSize, selectionRanking, selectionDiversity);
                case ROULETTE_WHEEL:
                    return new RouletteWheelSelection(solutionsToSelect, selectionRanking);
                case RANKING:
                    return new RankingAndDiversitySortingSelection(solutionsToSelect, selectionRanking, selectionDiversity);
                case RANDOM:
                    return new RandomSelection(solutionsToSelect);
            }
        }
        return null;
    }

}
