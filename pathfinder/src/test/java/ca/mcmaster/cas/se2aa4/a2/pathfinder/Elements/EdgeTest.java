package ca.mcmaster.cas.se2aa4.a2.pathfinder.Elements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class EdgeTest {

    Edge edge;
    Node n1 = new Node(1);
    Node n2 = new Node(2);
    Integer weight;

    @BeforeEach
    public void setUp() {
        weight = 5;
        edge = new Edge(n1, n2, weight);
    }

    @Test
    public void getN1Test() {
        assertEquals(n1, edge.getN1());
    }

    @Test
    public void getN2Test() {
        assertEquals(n2, edge.getN2());
    }

    @Test
    public void getWeightCorrect() {
        assertEquals(5, edge.getWeight());
    }

    @Test
    public void getWeightIncorrect() {
        weight = 3;
        assertNotEquals(3, edge.getWeight());
    }
}
