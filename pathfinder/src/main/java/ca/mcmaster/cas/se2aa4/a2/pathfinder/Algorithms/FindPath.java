package ca.mcmaster.cas.se2aa4.a2.pathfinder.Algorithms;

import ca.mcmaster.cas.se2aa4.a2.pathfinder.Elements.Node;
import ca.mcmaster.cas.se2aa4.a2.pathfinder.Graph.Graph;

import java.util.List;
import java.util.Map;

/**
 * Interface that is a public contract to find all the paths from a source node
 */
public interface FindPath {

    /**
     * Method that runs a path algorithm and returns all the nodes and the path that it needed to get there
     * Here's an example output, assuming the source node is 1
     * {1: [1], 2: [1, 4, 2] ...}
     * @param graph, type Graph
     * @param source, type Node
     * @return Map<Node, List<Node>>
     */
    Map<Node, List<Node>> pathFinder(Graph graph, Node source);

    /**
     * Takes in the output from pathFinder, and finds the associated path to the target node from the source
     * @param paths, Map<Node, List<Node>>
     * @param target, type Node
     * @return List<Node>
     */
    static List<Node> findTargetPath(Map<Node, List<Node>> paths, Node target) {
        return null;
    }

}
