package ca.mcmaster.cas.se2aa4.a2.pathfinder.Elements;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class NodeTest {

    Node node = new Node(1);

    @Test
    public void getNodeIdTest() {
        assertEquals(1, node.getId());
        assertNotEquals(2, node.getId());
    }

    @Test
    public void getDistanceTest() {
        assertEquals(Integer.MAX_VALUE, node.getDistance());
    }

    @Test
    public void setDistanceTestCorrect() {
        node.setDistance(5);
        assertNotEquals(Integer.MAX_VALUE, node.getDistance());
        assertEquals(5, node.getDistance());
    }

}
