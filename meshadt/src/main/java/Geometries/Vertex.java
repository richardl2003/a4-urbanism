package Geometries;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.awt.*;
import java.util.Objects;

/**
 * Mutable Vertex which contains a Coordinate(x,y)
 */
public class Vertex {
    private Structs.Vertex vertex;
    private Color color;
    private Integer id;
    private final Coordinate coordinate;
    private final PropertyHandler propertyHandler = new PropertyHandler();
    private Float thickness;

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public boolean isCentroid() {
        return false;
    }

    public Vertex(Double x, Double y) {
        // randomly generates colour
        color = propertyHandler.generateColors();
        coordinate = new Coordinate(x,y);
        setVertex(x, y, color);
    }

    public Vertex(Double x, Double y, Float thickness) {
        // randomly generates colour
        color = propertyHandler.generateColors();
        this.thickness = thickness;
        coordinate = new Coordinate(x,y);
        setVertex(x, y, color, thickness);
    }

    public Vertex(Double x, Double y, Color color) {
        this.color = color;
        coordinate = new Coordinate(x,y);
        setVertex(x, y, color);
    }

    public Vertex(Double x, Double y, Color color, Float thickness) {
        this.color = color;
        coordinate = new Coordinate(x,y);
        this.thickness = thickness;
        setVertex(x, y, color, thickness);
    }

    public Structs.Vertex getVertex() {
        return vertex;
    }

    public Double getX() {
        return vertex.getX();
    }

    public Double getY() {
        return vertex.getY();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        setVertex(getX(), getY(), color, propertyHandler.extractThicknessProperty(getVertex().getPropertiesList()));
    }

    public void setThickness(Float thickness) {
        this.thickness = thickness;
        setVertex(getX(), getY(), propertyHandler.extractColorProperty(getVertex().getPropertiesList()), thickness);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer newId) {
        id = newId;
    }

    private void setVertex(Double x, Double y, Color color) {
        vertex = Structs.Vertex.newBuilder().setX(x).setY(y)
                .addProperties(propertyHandler.setColorProperty(color))
                .addProperties(propertyHandler.setCentroidProperty(isCentroid()))
                .build();
    }

    private void setVertex(Double x, Double y, Color color, Float thickness) {
        vertex = Structs.Vertex.newBuilder().setX(x).setY(y)
                .addProperties(propertyHandler.setColorProperty(color))
                .addProperties(propertyHandler.setThicknessProperty(thickness))
                .addProperties(propertyHandler.setCentroidProperty(isCentroid()))
                .build();
    }

    /**
     * A vertex is equal if its coordinates are equal
     * @param o: Takes in any object o
     * @return boolean: True if they are equal, false if not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return Objects.equals(coordinate, vertex.coordinate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinate);
    }
}
