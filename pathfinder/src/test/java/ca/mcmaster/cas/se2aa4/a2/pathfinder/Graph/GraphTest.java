package ca.mcmaster.cas.se2aa4.a2.pathfinder.Graph;

import ca.mcmaster.cas.se2aa4.a2.pathfinder.Algorithms.ShortestPath;
import ca.mcmaster.cas.se2aa4.a2.pathfinder.Elements.Edge;
import ca.mcmaster.cas.se2aa4.a2.pathfinder.Elements.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class GraphTest {
    Set<Node> nodes = new LinkedHashSet<>();
    Set<Edge> edges = new LinkedHashSet<>();
    Graph graph;
    Node node1 = new Node(1);
    Node node2 = new Node(2);
    Node node3 = new Node(3);
    Node node4 = new Node(4);
    Node node5 = new Node(5);
    Node node6 = new Node(6);

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
    public void getNodesTest() {
        Set<Node> nodeSet = graph.getNodes();
        assertTrue(nodeSet.contains(node1));
        assertTrue(nodeSet.contains(node2));
        assertTrue(nodeSet.contains(node3));
        assertTrue(nodeSet.contains(node4));
        assertTrue(nodeSet.contains(node5));
        assertTrue(nodeSet.contains(node6));
    }

    @Test
    public void getNumNodesTest() {
        Set<Node> nodeSet = graph.getNodes();
        assertEquals(6, nodeSet.size());

        // Force error condition
        graph.addNode(new Node(7));
        assertNotEquals(6, nodeSet.size());
        assertEquals(7, nodeSet.size());
    }

    @Test
    public void getNumEdgesTest() {
        Set<Edge> edgeSet = graph.getEdges();
        assertEquals(18, edgeSet.size());
    }

}
