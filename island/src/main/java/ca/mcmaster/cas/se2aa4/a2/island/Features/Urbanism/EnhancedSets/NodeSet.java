package ca.mcmaster.cas.se2aa4.a2.island.Features.Urbanism.EnhancedSets;

import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;
import ca.mcmaster.cas.se2aa4.a2.pathfinder.Elements.Node;

import java.util.*;

/**
 * Created own Node data structure that stores a reference to it's associated Island VertexDecorators
 */
public class NodeSet implements Iterable<VertexDecorator> {
    private final Map<Node, VertexDecorator> nodeToVD = new LinkedHashMap<>();
    private final Map<VertexDecorator, Node> VDToNode = new LinkedHashMap<>();

    public void add(Node node, VertexDecorator vertexDecorator) {
        if (contains(node)) return;
        nodeToVD.put(node, vertexDecorator);
        VDToNode.put(vertexDecorator, node);
    }

    public boolean contains(Node node) {
        return nodeToVD.containsKey(node);
    }

    public VertexDecorator get(Node node) {
        return nodeToVD.get(node);
    }

    public Node get(VertexDecorator vertexDecorator) {
        return VDToNode.get(vertexDecorator);
    }

    public Map<Node, VertexDecorator> getNodeToVD() {
        return new HashMap<>(nodeToVD);
    }

    public Set<Node> getNodeSet() {
        return nodeToVD.keySet();
    }

    @Override
    public Iterator<VertexDecorator> iterator() {
        return nodeToVD.values().iterator();
    }

}
