package ca.mcmaster.cas.se2aa4.a2.pathfinder.Elements;

import java.util.Objects;

/**
 * Edge ADT that is an element of a Graph
 */
public class Edge {

    private final Integer weight;
    Node n1;
    Node n2;

    // Creators
    public Edge(Node n1, Node n2, Integer weight) {
        this.n1 = n1;
        this.n2 = n2;
        this.weight = weight;
    }

    // Observers
    public Node getN1() {
        return n1;
    }

    public Node getN2() {
        return n2;
    }

    public Node getOtherNode(Node n) {
        if (Objects.equals(getN1(), n)) {
            return getN2();
        } else if (Objects.equals(getN2(), n)) {
            return getN1();
        }
        return null;
    }

    public Integer getWeight() {
        return this.weight;
    }

    // Debugging purposes
    @Override
    public String toString() {
        return "("+n1.getId()+", "+n2.getId()+")";
    }

}
