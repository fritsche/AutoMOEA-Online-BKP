package org.uma.jmetal.algorithm.components.factory;

import org.uma.jmetal.algorithm.components.Diversity;
import org.uma.jmetal.algorithm.components.Ranking;
import org.uma.jmetal.algorithm.components.ReplacementImplementation;
import org.uma.jmetal.algorithm.components.impl.replacement.GenerationalReplacement;
import org.uma.jmetal.algorithm.components.impl.replacement.RankingAndDiversityReplacement;

public class ReplacementImplementationFactory {

    public static final String GENERATIONAL = "Generational";
    public static final String RANKING = "Ranking";

    public static ReplacementImplementation createReplacementImplementation(String replacement, int elitismSize, Ranking ranking, Diversity diversity) {
        if (replacement != null) {
            switch (replacement) {
                case GENERATIONAL:
                    return new GenerationalReplacement(elitismSize, ranking, diversity);
                case RANKING:
                    return new RankingAndDiversityReplacement(ranking, diversity);
            }
        }
        return null;
    }

}
