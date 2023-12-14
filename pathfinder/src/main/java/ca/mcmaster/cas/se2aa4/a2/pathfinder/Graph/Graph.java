package ca.mcmaster.cas.se2aa4.a2.pathfinder.Graph;

import ca.mcmaster.cas.se2aa4.a2.pathfinder.Elements.Edge;
import ca.mcmaster.cas.se2aa4.a2.pathfinder.Elements.Node;
import ca.mcmaster.cas.se2aa4.a2.pathfinder.Algorithms.FindPath;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Graph ADT with creators, observers
 */
public class Graph {

    Set<Node> nodes;
    Set<Edge> edges;

    // Creators
    public Graph(Set<Node> nodes, Set<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    // Observers
    public Set<Node> getNodes() {
        return nodes;
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    public Integer numNodes() {
        return nodes.size();
    }

    public Integer numEdges() {
        return edges.size();
    }

    public boolean containsNode(Node node) {
        return nodes.contains(node);
    }

    public boolean containsEdge(Edge edge) {
        return edges.contains(edge);
    }

    /**
     * Finds the shortest paths from a given source node
     * @param path, FindPath interface
     * @param source, type Node
     * @return Map<Node, List<Node>
     */
    public Map<Node, List<Node>> findAllShortestPaths(FindPath path, Node source) {
        return path.pathFinder(this, source);
    }

    /**
     * Given the output from the above method, returns the path for the target node
     * @param dijkstraSource, Map<Node, List<Node>
     * @param target, type Node
     * @return List<Node>
     */
    public List<Node> findShortestPath(Map<Node, List<Node>> dijkstraSource, Node target) {
        return FindPath.findTargetPath(dijkstraSource, target);
    }

    // Producers
    public void addNode(Node n) {
        nodes.add(n);
    }

    public void addEdge(Node n1, Node n2, Integer weight) {
        Edge e = new Edge(n1, n2, weight);
        edges.add(e);
    }
}
