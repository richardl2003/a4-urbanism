package ca.mcmaster.cas.se2aa4.a2.pathfinder.Elements;

import com.sun.jdi.IntegerValue;

import java.util.*;

/**
 * Node ADT that is an element of the graph
 */
public class Node implements Comparable<Node> {

    Integer id;

    // Attributes needed for Dijkstra calculation
    private Integer distance = Integer.MAX_VALUE;
    private List<Edge> neighbours = new ArrayList<>();

    // Creators
    public Node(Integer id) {
        this.id = id;
    }

    // Observers
    public Integer getId() {
        return id;
    }
    public Integer getDistance() {
        return this.distance;
    }
    public List<Edge> getNeighbours() {
        return new ArrayList<>(this.neighbours);
    }

    // Mutators
    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public void setNeighbours(List<Edge> neighbours) {
        this.neighbours = neighbours;
    }

    /**
     * Override method to compare distance weights for the Priority Queue
     * @param node the object to be compared.
     * @return int
     */
    @Override
    public int compareTo(Node node) {
        return Integer.compare(this.distance, node.getDistance());
    }

    // Debugging purposes
    @Override
    public String toString() {
        return ""+id+"";
    }

}
