package Geometries;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.awt.*;
import java.util.List;
import java.util.Random;

/**
 * Util class that handles properties for all geometries
 */
public class PropertyHandler {
    /**
     * Generates random color in rgba format
     * @return Color
     */
    Color generateColors() {
        Random bag = new Random();
        int red = bag.nextInt(255);
        int green = bag.nextInt(255);
        int blue = bag.nextInt(255);
        int alpha = bag.nextInt(1,255);
        return new Color(red, green, blue, alpha);
    }

    /**
     * Takes a Color in and turns it into a property
     * @param color: a color to turn into a property
     * @return Structs.Property
     */
    public Structs.Property setColorProperty(Color color) {
        String colorStr = ""+color.getRed()+","+color.getGreen()+","+color.getBlue()+","+color.getAlpha();
        return Structs.Property.newBuilder().setKey("rgba_color").setValue(colorStr).build();
    }

    /**
     * Takes a Thickness in and turns it into a property
     * @param thickness: Float
     * @return Structs.Property
     */
    public Structs.Property setThicknessProperty(Float thickness) {
        String segmentThickness = Float.toString(thickness);
        return Structs.Property.newBuilder().setKey("thickness").setValue(segmentThickness).build();
    }

    /**
     * Takes whether a vertex is a centroid and turns it into a property
     * @param isCentroid: Boolean
     * @return Structs.Property
     */
    public Structs.Property setCentroidProperty(Boolean isCentroid) {
        String stringCentroid = ""+isCentroid;
        return Structs.Property.newBuilder().setKey("is_centroid").setValue(stringCentroid).build();
    }

    /**
     * Gets the average color of a list of colors
     * @param colors: List of colors
     * @return Color: The average color
     */
    Color averageColor(List<Color> colors) {
        int totalColors = colors.size();
        int red = 0;
        int blue = 0;
        int green = 0;
        int alpha = 0;

        for (Color color : colors) {
            red += color.getRed();
            blue += color.getBlue();
            green += color.getGreen();
            alpha += color.getAlpha();
        }

        red = red/totalColors;
        blue = blue/totalColors;
        green = green/totalColors;
        alpha = alpha/totalColors;
        return new Color(red, green, blue, alpha);
    }


    /**
     * Determines the color of a data structure
     */
    public Color extractColorProperty(List<Structs.Property> properties) {
        String val = null;
        for(Structs.Property p: properties) {
            if (p.getKey().equals("rgba_color")) {
                val = p.getValue();
            }
        }
        if (val == null)
            return Color.BLACK;
        String[] raw = val.split(",");
        int red = Integer.parseInt(raw[0]);
        int green = Integer.parseInt(raw[1]);
        int blue = Integer.parseInt(raw[2]);
        int alpha = Integer.parseInt(raw[3]);
        return new Color(red, green, blue, alpha);
    }

    /**
     * Determines the thickness of a data structure
     */
    public Float extractThicknessProperty(List<Structs.Property> properties) {
        String val = null;
        for(Structs.Property p: properties) {
            if (p.getKey().equals("thickness")) {
                val = p.getValue();
            }
        }
        if (val == null)
            return 2f;
        return Float.parseFloat(val);
    }

    /**
     * Determines if a vertex is a centroid from the vertex's properties.
     */

    public boolean isCentroid(List<Structs.Property> properties) {
        String val = "false";
        for (Structs.Property p : properties) {
            if (p.getKey().equals("is_centroid")) {
                val = p.getValue();
            }
        }
        return val.equals("true");
    }


}
