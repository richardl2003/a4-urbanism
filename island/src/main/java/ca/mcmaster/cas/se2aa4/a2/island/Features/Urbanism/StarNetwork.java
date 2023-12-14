package ca.mcmaster.cas.se2aa4.a2.island.Features.Urbanism;

import Geometries.Segment;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Seed;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Urbanism.GraphAdapter.IslandToGraph;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;
import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import ca.mcmaster.cas.se2aa4.a2.pathfinder.Graph.Graph;

import java.util.*;

/**
 * Implementation of the CityGenerator, provides a star network
 * Paths spawn from the capital and connect to all other villages and hamlets
 */
public class StarNetwork implements CityGenerator {
    Island island;
    List<VertexDecorator> cityVertices;
    Graph graph;
    IslandToGraph adapter;
    List<Border> roads = new ArrayList<>();

    @Override
    public void process(Island container, Integer cities) {
        Map<VertexDecorator, List<VertexDecorator>> shortestPaths;
        island = container;
        cityVertices = new ArrayList<>(cities);
        Seed seed = Seed.getInstance();
        selectCapitalAndCities(seed, cities);

        // Adapt the mesh into a graph
        adaptMeshToGraph(island);

        // Get shortest paths
        shortestPaths = adapter.findShortestPath();

        // Make roads
        getRoads(shortestPaths);
        createRoads();

    }

    /**
     * Takes in Island information (VertexDecorators, Borders) and adapts them to a Graph purpose
     * @param island, type Island
     */
    private void adaptMeshToGraph(Island island) {
        adapter = new IslandToGraph(cityVertices);
        graph = adapter.getPointsAndConnections(island);
    }

    /**
     * Randomly generates a capital city, and corresponding villages and hamlets
     * @param seed, type Seed
     * @param cities, num of cities from CLI
     */
    private void selectCapitalAndCities(Seed seed, int cities) {
        selectCapital(seed);
        int numCities = seed.nextInt(1, cities - 1);
        selectCities(numCities, seed, City.Village);
        int numHamlets = cities - 1 - numCities;
        selectCities(numHamlets, seed, City.Hamlet);
    }

    private void selectCapital(Seed seed) {
        // Randomly select a tile
        VertexDecorator capital = getRandomVertexDecorator(seed);
        cityVertices.add(capital);
        capital.setCity(City.Capital);
    }

    private void selectCities(int num, Seed seed, City type) {
        for (int i = 0; i < num; i++) {
            VertexDecorator city = getRandomVertexDecorator(seed);
            for (VertexDecorator v: cityVertices) {
                while (city.getX() == v.getX() && city.getY() == v.getY()) {
                    city = getRandomVertexDecorator(seed);
                }
            }
            cityVertices.add(city);
            city.setCity(type);
        }
    }

    private VertexDecorator getRandomVertexDecorator(Seed seed) {
        List<Tile> landTiles = island.getLandTiles();
        Tile source = landTiles.get(seed.nextInt(landTiles.size()));
        while (source.hasLake()) {
            source = landTiles.get(seed.nextInt(landTiles.size()));
        }
        return source.getCentroid();
    }

    private void createRoads() {
        List<Border> currentBorders = island.getBorders();
        // Merge currentBorders with roads
        currentBorders.addAll(roads);
        // Generate a new island with borders from the shortest path implementation
        island.register(island.getVertexDecorators(), currentBorders, island.getTiles());
    }

    private void getRoads(Map<VertexDecorator, List<VertexDecorator>> shortestVertices) {
        for (VertexDecorator city: shortestVertices.keySet()) {
            List<VertexDecorator> cityList = shortestVertices.get(city);
            for (int i = 0; i < cityList.size() - 1; i++) {
                Segment segment = new Segment(cityList.get(i).getVertex(),
                        cityList.get(i+1).getVertex());
                Border border = Border.newBuilder().addSegment(segment)
                        .addV1(cityList.get(i)).addV2(cityList.get(i+1)).build();
                border.setRoad(true);
                roads.add(border);
            }
        }
    }
}
