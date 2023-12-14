package ca.mcmaster.cas.se2aa4.a2.island.Visualizer;

import ca.mcmaster.cas.se2aa4.a2.island.Features.Biomes.Biome;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;
import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityVisualizer implements Visualizer {

    private static final Map<String, Color> colors = new HashMap<>();

    // Initializes the colour map with its needed colours.
    static {
        colors.put("lake", new Color(103,168,209,255));
        colors.put("ocean", new Color(0,87,143,255));
        colors.put("river", new Color(103,168,209,255));
    }

    /**
     * Processes all the tiles, borders and decorators into their biome colours.
     * @param island: Given island to process
     */
    @Override
    public void process(Island island) {
        traverseTiles(island.getTiles());
        traverseBorders(island.getBorders());
        traverseVertexDecorators(island.getVertexDecorators());
    }

    /**
     * Sets the tile to its designated colours.
     * @param tiles: a list of tiles
     */
    private void traverseTiles(List<Tile> tiles) {
        for (Tile tile : tiles) {
            if (tile.hasLake()) tile.setColor(colors.get("lake"));
            else if (Arrays.asList(Biome.values()).contains(tile.getBiome()))
                tile.setColor(tile.getBiome().toColor());
            else tile.setColor(colors.get("ocean"));
        }
    }

    /**
     * Sets all borders colours to black and thicknesses to zero if no river is found.
     * Border is set to the multiplicity thickness if a river is found.
     * @param borders: a list of borders
     */
    private void traverseBorders(List<Border> borders) {
        for (Border border : borders) {
            if (border.hasRiver()) {
                border.setColor(colors.get("river"));
                border.setThickness(border.getWater().multiplicity());
            } else if (border.isRoad()) {
                border.setColor(Color.BLACK);
                border.setThickness(4.0f);
            } else {
                border.setColor(Color.BLACK);
                border.setThickness(0f);
            }
        }
    }

    /**
     * Sets all vertex decorators colours to black and thicknesses to zero.
     * @param vertices: a list of vertex decorators
     */
    private void traverseVertexDecorators(List<VertexDecorator> vertices) {
        for (VertexDecorator vertex : vertices) {
            if (vertex.isCity()) {
                vertex.setColor(vertex.getCity().toColor());
                vertex.setThickness(vertex.getCity().getThickness());
            }
        }
    }

}
