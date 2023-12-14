package ca.mcmaster.cas.se2aa4.a2.island.Features.Urbanism.GraphAdapter;

import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;
import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import ca.mcmaster.cas.se2aa4.a2.pathfinder.Graph.Graph;

import java.util.List;
import java.util.Map;

/**
 * An adapter interface that aims to incompatible interfaces together
 */
public interface ConverterAdapter {

    /**
     * Run Dijkstra's algorithm on a graph and on the capital source node
     * @return Map<VertexDecorator, List<VertexDecorator>
     */
    Map<VertexDecorator, List<VertexDecorator>> findShortestPath();

    /**
     * Converts all Island elements into Graph elements
     * VertexDecorators -> Node
     * Border -> Edge
     * @param island, type Island
     * @return type Graph
     */
    Graph getPointsAndConnections(Island island);

}
