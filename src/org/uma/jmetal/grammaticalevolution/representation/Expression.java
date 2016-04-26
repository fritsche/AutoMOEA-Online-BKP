package org.uma.jmetal.grammaticalevolution.representation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Expression {

    protected int index;
    protected List<Node> values;

    public Expression() {
        this.values = new ArrayList<>();
    }

    public Expression(int index) {
        this();
        this.index = index;
    }

    public Expression(int index, List<Node> values) {
        this.index = index;
        this.values = values;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<Node> getNodes() {
        return values;
    }

    public void setValues(List<Node> values) {
        this.values = values;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.values);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Expression other = (Expression) obj;
        if (values.size() != other.getNodes().size()) {
            return false;
        }
        for (int i = 0; i < values.size(); i++) {
            Node node1 = values.get(i);
            Node node2 = other.getNodes().get(i);
            if (!node1.equals(node2)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return values.stream()
                .map((Node node) -> {
                    return node.isTerminal()
                            ? "\"" + node.getName() + "\""
                            : "<" + node.getName() + ">";
                })
                .collect(Collectors.joining(" "));
    }

}
