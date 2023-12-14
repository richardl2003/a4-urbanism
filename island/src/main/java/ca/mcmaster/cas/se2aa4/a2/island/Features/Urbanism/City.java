package ca.mcmaster.cas.se2aa4.a2.island.Features.Urbanism;

import java.awt.*;

/**
 * Enumeration for cities, each city is associated with a color and thickness value
 */
public enum City {

    Capital(15.0f, new Color(181, 14, 229,255)),
    Village(7.5f, new Color(255, 0, 0,255)),
    Hamlet(5.0f, new Color(255, 183, 0,255));

    private final Float thickness;
    private final Color color;

    City(Float thickness, Color color) {
        this.thickness = thickness;
        this.color = color;
    }

    public float getThickness() {
        return thickness;
    }
    public Color toColor() {
        return color;
    }

}
