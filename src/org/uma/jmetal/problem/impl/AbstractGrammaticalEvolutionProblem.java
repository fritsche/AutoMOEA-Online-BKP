package org.uma.jmetal.problem.impl;

import org.uma.jmetal.grammaticalevolution.mapper.AbstractGrammarMapper;
import org.uma.jmetal.solution.impl.VariableIntegerSolution;

public abstract class AbstractGrammaticalEvolutionProblem<MapperReturn> extends AbstractGenericProblem<VariableIntegerSolution> {

    protected AbstractGrammarMapper<MapperReturn> mapper;
    protected int maxInitialCondons;
    protected int minInitialCondons;

    public AbstractGrammaticalEvolutionProblem(int minCondons, int maxCondons, AbstractGrammarMapper<MapperReturn> mapper, String grammarFile) {
        this.mapper = mapper;
        this.minInitialCondons = minCondons;
        this.maxInitialCondons = maxCondons;
        mapper.loadGrammar(grammarFile);
    }

    public Integer getLowerBound(int i) {
        return 1;
    }

    public Integer getUpperBound(int i) {
        return Integer.MAX_VALUE;
    }

    public AbstractGrammarMapper getMapper() {
        return mapper;
    }

    public void setMapper(AbstractGrammarMapper mapper) {
        this.mapper = mapper;
    }

    public int getMaxCondons() {
        return maxInitialCondons;
    }

    public void setMaxCondons(int maxCondons) {
        this.maxInitialCondons = maxCondons;
    }

    public int getMinInitialCondons() {
        return minInitialCondons;
    }

    public void setMinInitialCondons(int minInitialCondons) {
        this.minInitialCondons = minInitialCondons;
    }

    @Override
    public VariableIntegerSolution createSolution() {
        return new VariableIntegerSolution(this, minInitialCondons, maxInitialCondons);
    }

}
