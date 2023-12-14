package ca.mcmaster.cas.se2aa4.a2.island.Features.Urbanism.GraphAdapter;

import ca.mcmaster.cas.se2aa4.a2.island.Features.Urbanism.City;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Urbanism.EnhancedSets.NodeSet;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;
import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import ca.mcmaster.cas.se2aa4.a2.pathfinder.Algorithms.ShortestPath;
import ca.mcmaster.cas.se2aa4.a2.pathfinder.Elements.Edge;
import ca.mcmaster.cas.se2aa4.a2.pathfinder.Elements.Node;
import ca.mcmaster.cas.se2aa4.a2.pathfinder.Graph.Graph;

import java.util.*;

/**
 * Implementation of the ConverterAdapter Interface, seeks to adapt Island elements into Graph elements
 * Goal is to be support the ShortestPath operations on Island elements
 */
public class IslandToGraph implements ConverterAdapter {

    Graph graph;
    NodeSet nodeSet = new NodeSet();
    Set<Edge> edgeSet = new LinkedHashSet<>();
    List<VertexDecorator> cityVertices;
    List<Node> cityNodes = new ArrayList<>();

    Map<Node, List<Node>> cityPaths = new HashMap<>();

    public IslandToGraph() {

    }

    public IslandToGraph(List<VertexDecorator> cityVertices) {
        this.cityVertices = cityVertices;
    }

    @Override
    public Map<VertexDecorator, List<VertexDecorator>> findShortestPath() {
        // Get the capital city
        Node capital = getCapitalNode();

        // Run dijkstra's algorithm
        Map<Node, List<Node>> shortestPathsFromCapital = graph.findAllShortestPaths(new ShortestPath(), capital);

        // Focus on the shortest paths on only the city vertices
        getCityPaths(shortestPathsFromCapital);

        // Convert cityPaths from nodes to VertexDecorators
        return convertCityPathsToVertexDecorator();
    }

    @Override
    public Graph getPointsAndConnections(Island island) {
        getPoints(island);
        getConnections(island);

        graph = new Graph(nodeSet.getNodeSet(), edgeSet);
        return graph;
    }

    private Node getCapitalNode() {
        for (VertexDecorator city: cityVertices) {
            if (city.getCity() == City.Capital) {
                return nodeSet.get(city);
            }
        }
        return null;
    }

    /**
     * Iterates through all tiles, and turns every tile centroid into a node
     * @param island, type Island
     */
    public void getPoints(Island island) {
        int id = 1;
        // Iterate through all tiles
        for (Tile tile: island.getLandTiles()) {
            // Create a new node and add new node
            nodeSet.add(new Node(id), tile.getCentroid());
            id++;
        }
    }

    /**
     * Iterates through all tiles, and then determines the neighbours for each tile
     * Creates a proxy edge for every centroid and their neighbour's centroid
     * @param island, type Island
     */
    public void getConnections(Island island) {
        // Iterate through all tiles
        for (Tile tile: island.getLandTiles()) {
            // Obtain the neighbours for a given tile
            List<Tile> neighbours = getNeighbouringTiles(tile, island);
            // Iterate through neighbours and create new edge for the current tile and the neighbours centroid
            for (Tile neighbour: neighbours) {
                // Calculate euclidean distance between the centroids
                if (neighbour.hasLake() || tile.hasLake()) {
                    Integer weight = calculateEuclideanDistance(tile.getCentroid(), neighbour.getCentroid(), 100);
                    edgeSet.add(new Edge(nodeSet.get(tile.getCentroid()), nodeSet.get(neighbour.getCentroid()), weight));
                } else {
                    Integer weight = calculateEuclideanDistance(tile.getCentroid(), neighbour.getCentroid(), 0);
                    edgeSet.add(new Edge(nodeSet.get(tile.getCentroid()), nodeSet.get(neighbour.getCentroid()), weight));
                }
            }
        }
    }

    /**
     * For each tile, finds all neighbouring tiles
     * All neighbouring tiles will share a border
     * @param source, type Tile
     * @param island, type Island
     * @return List<Tile>
     */
    public List<Tile> getNeighbouringTiles(Tile source, Island island) {
        Set<Tile> neighbouringTiles = new HashSet<>();
        for (Tile tile : island.getLandTiles()) {
            if (tile == source) continue;
            for (Border b: tile.getBorders()) {
                if (source.getBorders().contains(b)) {
                    neighbouringTiles.add(tile);
                    continue;
                }
                for (Border sb: source.getBorders()) {
                    if ((b.getV1().getX() == sb.getV2().getX()) && (b.getV1().getY() == sb.getV2().getY()) &&
                            (b.getV2().getX() == sb.getV1().getX()) && (b.getV2().getY() == sb.getV1().getY())) {
                        neighbouringTiles.add(tile);
                    }
                }
            }
        }
        neighbouringTiles.remove(source);
        return new ArrayList<>(neighbouringTiles);
    }

    /**
     * Weight function of an edge is the distance between the centroids
     * @param start, type VertexDecorator
     * @param end, type VertexDecorator
     * @return Integer
     */
    public Integer calculateEuclideanDistance(VertexDecorator start, VertexDecorator end, Integer lakeWeight) {
        Double xChange = Math.pow(end.getX() - start.getX(), 2);
        Double yChange = Math.pow(end.getY() - start.getY(), 2);
        return (Integer) (int) Math.sqrt(xChange + yChange) + lakeWeight;
    }

    private void convertCityVerticesToNodes() {
        for (VertexDecorator city: cityVertices) {
            cityNodes.add(nodeSet.get(city));
        }
    }

    /**
     * Dijkstra's returns the shortest path for all nodes
     * Extracts all the hamlet and village nodes from the overall data structure
     * @param capitalPaths, Map<Node, List<Node>>
     */
    private void getCityPaths(Map<Node, List<Node>> capitalPaths) {
        convertCityVerticesToNodes();
        for (Node city: cityNodes) {
            cityPaths.put(city, capitalPaths.get(city));
        }
    }

    /**
     * Converts Map<Node, List<Node>> to Map<VertexDecorator, List<VertexDecorator>>
     * This conversion will be returned back to the client
     * @return Map<VertexDecorator, List<VertexDecorator>>
     */
    private Map<VertexDecorator, List<VertexDecorator>> convertCityPathsToVertexDecorator() {
        Map<VertexDecorator, List<VertexDecorator>> nodeToVD = new HashMap<>();
        for (Node city: cityPaths.keySet()) {
            List<VertexDecorator> vertices = new ArrayList<>();
            for (Node p: cityPaths.get(city)) {
                vertices.add(nodeSet.get(p));
            }
            nodeToVD.put(nodeSet.get(city), vertices);
        }
        return nodeToVD;
    }
}
