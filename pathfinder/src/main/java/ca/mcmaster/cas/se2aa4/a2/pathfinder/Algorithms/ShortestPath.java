package ca.mcmaster.cas.se2aa4.a2.pathfinder.Algorithms;

import ca.mcmaster.cas.se2aa4.a2.pathfinder.Elements.Edge;
import ca.mcmaster.cas.se2aa4.a2.pathfinder.Elements.Node;
import ca.mcmaster.cas.se2aa4.a2.pathfinder.Graph.Graph;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Implementation of a pathfinder
 */
public class ShortestPath implements FindPath {

    Map<Node, List<Node>> paths = new HashMap<>();

    @Override
    public Map<Node, List<Node>> pathFinder(Graph graph, Node source) {

        // Set all adjacent nodes for each node
        setAdjacentNodes(graph);

        // Perform Dijkstra's algorithm on the source node
        SSSP_Dijkstra(source);

        addTargetToPathList();

        return paths;

    }

    private void addTargetToPathList() {
        for (Node node: paths.keySet()) {
            List<Node> updated = new LinkedList<>(paths.get(node));
            updated.add(node);
            paths.put(node, updated);
        }
    }

    /**
     * Implementation of Dijkstra's algorithm
     * @param source, type Node
     */
    private void SSSP_Dijkstra(Node source) {
        source.setDistance(0);
        paths.put(source, new LinkedList<>());
        Set<Node> visited = new HashSet<>();
        Queue<Node> Q = new PriorityQueue<>(Collections.singleton(source));
        while (!Q.isEmpty()) {
            Node currentNode = Q.remove();
            for (Edge edge: currentNode.getNeighbours()) {
                Node neighbour = edge.getOtherNode(currentNode);
                if (!visited.contains(neighbour)) {
                    updateAdjacentDistance(neighbour, edge.getWeight(), currentNode);
                    if (Q.contains(neighbour)) continue;
                    Q.add(neighbour);
                }
            }
            visited.add(currentNode);
        }
    }

    /**
     * Gets all neighbouring nodes for a node
     * @param graph, Graph
     */
    private void setAdjacentNodes(Graph graph) {
        // Iterate through all nodes
        for (Node node: graph.getNodes()) {
            findAdjacentNodes(graph, node);
        }
    }

    public void findAdjacentNodes(Graph graph, Node source) {
        List<Edge> adjacent = new ArrayList<>();
        // Check for edges that contain the source node, append the other node
        for (Edge e: graph.getEdges()) {
            if (Objects.equals(e.getN1().getId(), source.getId()) ||
            Objects.equals(e.getN2().getId(), source.getId())) {
                adjacent.add(e);
            }
        }
        source.setNeighbours(adjacent);
    }

    /**
     * Checks if the distance of the current node is less than the distance currently stored
     * @param n, Neighbour node
     * @param weight, distance value from the edge connecting the nodes
     * @param currentNode, current node
     */
    private void updateAdjacentDistance(Node n, Integer weight, Node currentNode) {
        if (currentNode.getDistance() + weight < n.getDistance()) {
            n.setDistance(currentNode.getDistance() + weight);
            paths.put(n, Stream.concat(paths.get(currentNode).stream(), Stream.of(currentNode)).toList());
        }
    }

    // For debugging purposes
    public void printPaths(List<Node> nodes) {
        nodes.forEach(node -> {
            String path = paths.get(node).stream()
                    .map(Node::getId).map(Objects::toString)
                    .collect(Collectors.joining(" -> "));
            System.out.println((path.isBlank()
                    ? "%s : %s".formatted(node.getId(), node.getDistance())
                    : "%s -> %s : %s".formatted(path, node.getId(), node.getDistance()))
            );
        });
    }

}