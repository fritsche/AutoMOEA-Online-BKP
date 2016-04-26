package org.uma.jmetal.grammaticalevolution.representation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Node {

    protected String name;
    protected HashSet<Node> parentNodes;
    protected List<Expression> expressions;

    public Node(String name) {
        this(name, new HashSet<>(), new ArrayList<>());
    }

    public Node(String name, HashSet<Node> parentNodes) {
        this(name, parentNodes, new ArrayList<>());
    }

    public Node(String name, List<Expression> expressions) {
        this(name, new HashSet<>(), expressions);
    }

    public Node(String name, HashSet<Node> parentNodes, List<Expression> expressions) {
        this.name = name;
        this.expressions = expressions;
        this.parentNodes = parentNodes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Expression> getExpressions() {
        return expressions;
    }

    public void setExpressions(List<Expression> expressions) {
        this.expressions = expressions;
    }

    public HashSet<Node> getParentNodes() {
        return parentNodes;
    }

    public void setParentNodes(HashSet<Node> parentNodes) {
        this.parentNodes = parentNodes;
    }

    public boolean addParentNode(Node e) {
        return parentNodes.add(e);
    }

    public boolean removeParentNode(Node o) {
        return parentNodes.remove(o);
    }

    public void clearParentNodes() {
        parentNodes.clear();
    }

    public boolean isTerminal() {
        return expressions.isEmpty();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.name);
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
        final Node other = (Node) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return isTerminal()
                ? "\"" + name + "\""
                : "<" + name + "> ::= " + expressions.stream()
                .map(Expression::toString)
                .collect(Collectors.joining(" | "));
    }

}
