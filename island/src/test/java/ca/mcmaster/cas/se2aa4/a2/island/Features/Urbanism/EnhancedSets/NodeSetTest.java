package ca.mcmaster.cas.se2aa4.a2.island.Features.Urbanism.EnhancedSets;

import Geometries.Vertex;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;
import ca.mcmaster.cas.se2aa4.a2.pathfinder.Elements.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NodeSetTest {

    Vertex v1;
    Node node1 = new Node(1);
    Node node2 = new Node(2);
    VertexDecorator vertex1;
    NodeSet nodeSet = new NodeSet();

    @BeforeEach
    public void setUp() {
        v1 = new Vertex(0.0,0.0);
        vertex1 = VertexDecorator.newBuilder().addVertex(v1).build();

    }

    @Test
    public void addTest() {
        nodeSet.add(node1, vertex1);

        assertEquals(vertex1, nodeSet.get(node1));
        assertEquals(node1, nodeSet.get(vertex1));
    }

    @Test
    public void containsTest() {
        nodeSet.add(node1, vertex1);
        assertTrue(nodeSet.contains(node1));
        assertFalse(nodeSet.contains(node2));
    }

}
