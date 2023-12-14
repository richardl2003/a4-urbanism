package ca.mcmaster.cas.se2aa4.a2.island.Features.Water;

import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Seed;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;

import java.util.*;

/**
 * Contains methods that all Land Water (lakes, aquifers) must contain
 */
public abstract class LandWaterGenerator implements WaterGenerator {
    Island island;
    List<Tile> uncheckedTiles;

    /**
     * For user specified number of water elements, and for all land tiles, create bodies of water
     * @param island, container for all Vertex Decorator, Borders, and Tiles
     * @param numOfBodies
     */
    public void process(Island island, Integer numOfBodies) {
        this.island = island;
        uncheckedTiles = new ArrayList<>(island.getLandTiles());
        // Iterate n times (user specified)
        // generateWater method
        int i = 0;

        // try to generate water on every tile until you've checked them all
        while (i < numOfBodies && !uncheckedTiles.isEmpty()) {
            if (generateWater(getLayers())) i++;
        }
    }

    /**
     * Obtaining the number of tile layers the body of water will extrude from the source
     * @return
     */
    protected abstract Integer getLayers();

    /**
     * Sets the number of layers for the body of water
     * @param layers, how big the water will be
     * @return true if the water was successfully generated
     */
    public boolean generateWater(Integer layers) {
        // get random source tile for lake
        List<Tile> landTiles = uncheckedTiles;
        Seed seed = Seed.getInstance();
        Tile source = landTiles.get(seed.nextInt(landTiles.size()));

        List<Tile> sourceNeighbours = source.getNeighbours().stream()
                .map(id -> island.getTile(id)).toList();

        // remove it from the list of tiles to check
        uncheckedTiles.remove(source);
        // if source or neighbours are water, then don't make it a water source
        if (source.hasBodyOfWater()) return false;
        if (containsWater(sourceNeighbours)) return false;
        source.setWater(getNewWater(1));
        source.setWaterCenter(true);

        // start with only expanding source
        List<Tile> currentSetOfSources = new ArrayList<>();
        currentSetOfSources.add(source);

        // go through each "layer" of neighbours and expand
        for (int i=0; i<layers; i++) {
            List<Tile> nextSetOfSources = new ArrayList<>();
            for (Tile tile : currentSetOfSources) {
                nextSetOfSources.addAll(tile.getNeighbours().stream()
                        .map(id -> island.getTile(id)).toList());
                List<Tile> partOfBodyOfWater = new ArrayList<>();
                partOfBodyOfWater.addAll(nextSetOfSources);
                partOfBodyOfWater.addAll(currentSetOfSources);
                expandWater(tile, tile.getNeighbours().size(), partOfBodyOfWater, Math.abs(i-layers));
            }
            currentSetOfSources.addAll(nextSetOfSources);
        }
        return true;
    }

    /**
     * How many tiles to expand by, can be lower if no tiles qualified
     * @param source, original tile where the water starts
     * @param expansion, the maximum value to expand by
     * @param currentBodyOfWater, current body of water (collection of tiles)
     * @param multiplicity, integer
     */
    // expansion = how many tiles to EXPAND by (max), can be lower if no tiles qualified
    private void expandWater(Tile source, Integer expansion, List<Tile> currentBodyOfWater, Integer multiplicity) {
        Integer[] neighbourIds = source.getNeighbours().toArray(new Integer[0]);

        // get random neighbours up to value of amount to expand
        List<Tile> selectedNeighbours = new ArrayList<>();
        while (selectedNeighbours.size() < expansion) {
            Seed seed = Seed.getInstance();
            int index = seed.nextInt(neighbourIds.length);
            Tile neighbour = island.getTile(neighbourIds[index]);
            selectedNeighbours.add(neighbour);
        }

        // loop through selected neighbours and give them water
        for (Tile neighbour : selectedNeighbours) {
            if (neighbour.hasBodyOfWater() || (hasOceanNeighbours(neighbour) && !canBeAdjacentWater())) continue;
            // get neighbours of the neighbour
            // if any are bodies of water that aren't already part of the current body, don't make this a water
            List<Tile> neighboursNeighbours = new ArrayList<>(neighbour.getNeighbours().stream()
                    .map(id -> island.getTile(id)).toList());
            neighboursNeighbours.removeAll(currentBodyOfWater);

            if (containsWater(neighboursNeighbours) && !canBeAdjacentWater()) continue;

            neighbour.setWater(getNewWater(multiplicity));
            if (neighbour.hasLake()) updateTileAltitude(neighbour, source.getCentroid().getAltitude());
        }
    }

    /**
     * Checks if the body of water cna be adjacent to an ocean
     * @return true for aquifer, false for lake
     */
    protected abstract boolean canBeAdjacentWater();

    /**
     * Updates all the associated vertices and the centroid of the tile to the given altitude
     * @param tile The tile to update
     * @param altitude The altitude to update to
     */
    private void updateTileAltitude(Tile tile, Double altitude) {
        tile.getCentroid().setAltitude(altitude);

        for (Border border : tile.getBorders()) {
            border.getV1().setAltitude(altitude);
            border.getV2().setAltitude(altitude);
        }
    }

    private boolean containsWater(List<Tile> tiles) {
        for (Tile neighbour : tiles) {
            if (neighbour.hasBodyOfWater()) return true;
        }
        return false;
    }

    protected abstract BodyOfWater getNewWater(Integer multiplicity);


    private boolean hasOceanNeighbours(Tile tile) {
        List<Tile> neighbours = new ArrayList<>(tile.getNeighbours().stream()
                .map(id -> island.getTile(id)).toList());
        for (Tile neighbour : neighbours) {
            if (neighbour.isOcean()) return true;
        }
        return false;
    }
}

