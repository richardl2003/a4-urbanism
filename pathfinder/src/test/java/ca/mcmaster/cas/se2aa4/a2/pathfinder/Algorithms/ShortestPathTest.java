package ca.mcmaster.cas.se2aa4.a2.pathfinder.Algorithms;

import ca.mcmaster.cas.se2aa4.a2.pathfinder.Elements.Edge;
import ca.mcmaster.cas.se2aa4.a2.pathfinder.Elements.Node;
import ca.mcmaster.cas.se2aa4.a2.pathfinder.Graph.Graph;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ShortestPathTest {

    Set<Node> nodes = new LinkedHashSet<>();
    Set<Edge> edges = new LinkedHashSet<>();

    Graph graph;

    ShortestPath shortestPath;

    Node node1 = new Node(1);
    Node node2 = new Node(2);
    Node node3 = new Node(3);
    Node node4 = new Node(4);
    Node node5 = new Node(5);
    Node node6 = new Node(6);
    Node node7 = new Node(7);


    @BeforeEach
    public void graphSetup() {

        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node3);
        nodes.add(node4);
        nodes.add(node5);
        nodes.add(node6);

        edges.add(new Edge(node1, node2, 2));
        edges.add(new Edge(node2, node1, 2));
        edges.add(new Edge(node1, node3, 4));
        edges.add(new Edge(node3, node1, 4));
        edges.add(new Edge(node2, node3, 3));
        edges.add(new Edge(node3, node2, 3));
        edges.add(new Edge(node2, node4, 1));
        edges.add(new Edge(node4, node2, 1));
        edges.add(new Edge(node2, node5, 5));
        edges.add(new Edge(node5, node2, 5));
        edges.add(new Edge(node3, node4, 2));
        edges.add(new Edge(node4, node3, 2));
        edges.add(new Edge(node4, node5, 1));
        edges.add(new Edge(node5, node4, 1));
        edges.add(new Edge(node4, node6, 4));
        edges.add(new Edge(node6, node4, 4));
        edges.add(new Edge(node5, node6, 2));
        edges.add(new Edge(node6, node5, 2));

        graph = new Graph(nodes, edges);

    }

    @Test
    public void SSSP_DijkstraTestStartNode1() {

        // Fixture
        shortestPath = new ShortestPath();

        // Experience
        Map<Node, List<Node>> dijkstra = graph.findAllShortestPaths(shortestPath, node1);
        shortestPath.printPaths(List.copyOf(nodes));
        Map<Node, List<Integer>> dijkstraInt = new HashMap<>();
        for (Node node: dijkstra.keySet()) {
            List<Integer> ids = new ArrayList<>();
            for (Node n: dijkstra.get(node)) {
                ids.add(n.getId());
            }
            dijkstraInt.put(node, ids);
        }

        // Oracle and Assertion
        assertEquals(List.of(1,2,4,5,6), dijkstraInt.get(node6));
        assertEquals(List.of(1,3), dijkstraInt.get(node3));
        assertEquals(List.of(1,2), dijkstraInt.get(node2));
        assertEquals(List.of(1,2,4,5), dijkstraInt.get(node5));
        assertEquals(List.of(1), dijkstraInt.get(node1));
        assertEquals(List.of(1,2,4), dijkstraInt.get(node4));

    }

    @Test
    public void SSSP_Dijkstra_Boundary() {
        // Testing running dijkstra's on a node not connected to the graph

        // Fixture
        shortestPath = new ShortestPath();

        // Experience
        Map<Node, List<Node>> dijkstra = graph.findAllShortestPaths(shortestPath, node1);
        shortestPath.printPaths(List.copyOf(nodes));
        Map<Node, List<Integer>> dijkstraInt = new HashMap<>();
        for (Node node: dijkstra.keySet()) {
            List<Integer> ids = new ArrayList<>();
            for (Node n: dijkstra.get(node)) {
                ids.add(n.getId());
            }
            dijkstraInt.put(node, ids);
        }

        assertNull(dijkstraInt.get(node7));
    }

}
