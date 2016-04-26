package org.uma.jmetal.algorithm.components.factory;

import org.uma.jmetal.algorithm.components.ArchivingImplementation;
import org.uma.jmetal.algorithm.components.Diversity;
import org.uma.jmetal.algorithm.components.Ranking;
import org.uma.jmetal.algorithm.components.impl.archiving.RankingAndDiversityArchiving;

public class ArchivingImplementationFactory {

    public static ArchivingImplementation createArchivingImplementation(int archiveSize, Ranking ranking, Diversity diversity) {
        if (archiveSize > 0) {
            return new RankingAndDiversityArchiving(archiveSize, ranking, diversity);
        }
        return null;
    }

}
