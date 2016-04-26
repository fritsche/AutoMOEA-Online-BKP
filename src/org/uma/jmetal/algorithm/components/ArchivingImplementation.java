package org.uma.jmetal.algorithm.components;

import java.util.List;
import org.uma.jmetal.solution.Solution;

public interface ArchivingImplementation<S extends Solution<?>> {

    public int updateArchive(List<S> population);

    public List<S> getArchive();

}
