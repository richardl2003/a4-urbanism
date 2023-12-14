package ca.mcmaster.cas.se2aa4.a2.island.Features.Water;

import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Seed;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;

import java.util.*;
import java.util.List;

public class RiverGenerator implements WaterGenerator {

    Island island;
    List<List<River>> rivers = new ArrayList<>();
    List<Border> uncheckedBorders;

    @Override
    public void process(Island island, Integer nums) {
        this.island = island;
        uncheckedBorders = new ArrayList<>(island.getBorders());
        // Iterate n times (user specified)
        // generateWater method
        int i = 0;

        // try to generate water on every tile until you've checked them all
        while (i < nums && !uncheckedBorders.isEmpty()) {
            if (generateWater()) i++;
        }
    }

    // Generates a single river
    public boolean generateWater() {

        VertexDecorator spring;

        // Randomly select a tile
        List<Tile> landTiles = island.getLandTiles();
        Seed seed = Seed.getInstance();
        Tile source = landTiles.get(seed.nextInt(landTiles.size()));

        // Randomly select a border
        Border springBorder = source.getBorders().get(seed.nextInt(source.getBorders().size()));
        uncheckedBorders.remove(springBorder);

        // Check to see which vertex is higher
        if (springBorder.getV1().getAltitude() >= springBorder.getV2().getAltitude()) {
            spring = springBorder.getV1();
        } else {
            spring = springBorder.getV2();
        }
        // Get neighbouring vertices and check that there are no rivers in the borders from current spring to neighbouring vertices
        if (isInRiver(spring)) return false;
        if (decoratorOfLandWater(spring)) return false;
        if (getLowerVertices(spring, getNeighbouringVertices(spring, island.getTiles())).isEmpty()) {
            return false;
        }

        spring.setSpring(true);
        VertexDecorator previousSpring = spring;
        List<River> riverList = new ArrayList<>();
        while (spring != null) {
            // Check all neighbouring vertices for a lower altitude
            List<VertexDecorator> springNeighbours = getNeighbouringVertices(spring, island.getTiles());
            previousSpring = spring;
            spring = riverFlow(spring, springNeighbours, riverList);
        }
        rivers.add(riverList);

        //If river doesn't end in a lake or an ocean
        if (!decoratorOfLandWater(previousSpring)) {
            // Check the shared tiles with that final vertex and search for the one that doesn't contain a river border.
            for (Tile tile : getNeighbouringTiles(previousSpring)) {
                // Create a lake at that tile.
                if (!tileContainsRiverBorder(tile)) tile.setWater(new Lake());
            }
        }
        return true;
    }

    private boolean isInRiver(VertexDecorator spring) {
        List<VertexDecorator> neighbouringVertices = getNeighbouringVertices(spring, island.getTiles());

        for (Tile tile: island.getTiles()) {
            // Iterate through the border
            for (Border border: tile.getBorders()) {
                if ((spring == border.getV1() && neighbouringVertices.contains(border.getV2())) ||
                        (spring == border.getV2() && neighbouringVertices.contains(border.getV1()))) {
                    if (border.hasRiver()) return true;
                }
            }
        }
        return false;
    }

    private boolean tileContainsRiverBorder(Tile tile) {
        for (Border border : tile.getBorders()) {
            if (border.hasRiver()) return true;
        }
        return false;
    }

    /**
     * For a given tile, gather the neighbouring tiles
     * @param source, land tile
     * @return Collection of Tiles
     */
    private List<Tile> getNeighbouringTiles(VertexDecorator source) {
        Set<Tile> neighbouringTiles = new HashSet<>();
        for (Tile tile : island.getTiles()) {
            for (Border border : tile.getBorders()) {
                if (border.getV1() == source || border.getV2() == source) neighbouringTiles.add(tile);
            }
        }
        return new ArrayList<>(neighbouringTiles);
    }

    /**
     * Returns a VertexDecorator that the river should connect to next if valid
     * @param spring, source vertex
     * @param springNeighbours, neighbours of the source
     * @param currentRiver, current path
     * @return VertexDecorator
     */
    private VertexDecorator riverFlow(VertexDecorator spring, List<VertexDecorator> springNeighbours, List<River> currentRiver) {
        List<VertexDecorator> lowerAltitudeNeighbours = getLowerVertices(spring, springNeighbours);
        VertexDecorator minValue;
        if (lowerAltitudeNeighbours.isEmpty()) return null;
        else {
            // Choose the lowest neighbour from neighbours lower than it (gravity :))
            minValue = lowerAltitudeNeighbours.stream()
                    .min(Comparator.comparing(VertexDecorator::getAltitude)).get();
            Border border = searchForBorder(spring, minValue);
            if (borderOfLandWater(border)) return null;
            assert border != null;
            if (border.hasRiver()) {
                Integer multiplicity = border.getWater().multiplicity();
                ((Water) border.getWater()).setMultiplicity(multiplicity + 1);
            } else {
                River newRiver = new River();
                border.setWater(newRiver);
                currentRiver.add(newRiver);
            }
        }
        return minValue;
    }

    private boolean borderOfLandWater(Border border) {
        for (Tile tile : island.getTiles()) {
            if (tile.getBorders().contains(border) && (tile.isOcean() || tile.hasLake())) return true;
        }
        return false;
    }

    /**
     * Checks if the current vertex also belongs to an ocean or lake
     * @param vertexDecorator, current vertex
     * @return true if belongs to an ocean or lake, false if not
     */
    private boolean decoratorOfLandWater(VertexDecorator vertexDecorator) {
        for (Tile tile : island.getTiles()) {
            for (Border border : tile.getBorders()){
                if ((border.getV1() == vertexDecorator || border.getV2() == vertexDecorator) && (tile.isOcean() || tile.hasLake())) return true;
            }

        }
        return false;
    }

    private List<VertexDecorator> getNeighbouringVertices(VertexDecorator spring, List<Tile> tilesToCheck) {
        Set<VertexDecorator> neighbours = new HashSet<>();

        for (Tile tile: tilesToCheck) {
            // Iterate through the border
            for (Border border: tile.getBorders()) {
                if (border.getV1() == spring) {
                    neighbours.add(border.getV2());
                } else if (border.getV2() == spring) {
                    neighbours.add(border.getV1());
                }
            }
        }

        return new ArrayList<>(neighbours);
    }

    /**
     * Gets the vertices at a lower altitude compared to the source
     * @param spring, source vertex
     * @param neighbours, vertex that neighbours the spring
     * @return collection of vertices that are lower
     */
    private List<VertexDecorator> getLowerVertices(VertexDecorator spring, List<VertexDecorator> neighbours) {
        List<VertexDecorator> lowerAltitudes = new ArrayList<>();

        for (VertexDecorator neighbour: neighbours) {
            if (neighbour.getAltitude() < spring.getAltitude()) {
                lowerAltitudes.add(neighbour);
            }
        }

        return lowerAltitudes;
    }

    private Border searchForBorder(VertexDecorator spring, VertexDecorator lowerAltitude) {
        List<Border> borders = island.getBorders();

        for (Border border: borders) {
            if ((border.getV1() == spring && border.getV2() == lowerAltitude) ||
                    (border.getV1() == lowerAltitude && border.getV2() == spring)) {
                return border;
            }
        }
        return null;
    }
}
