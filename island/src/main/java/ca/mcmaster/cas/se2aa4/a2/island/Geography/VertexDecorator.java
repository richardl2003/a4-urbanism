package ca.mcmaster.cas.se2aa4.a2.island.Geography;

import Geometries.Vertex;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Urbanism.City;

import java.awt.*;

/**
 * VertexDecorator holds a mesh Vertex and adds additional properties to it
 */
public class VertexDecorator {
    Vertex vertex;
    Boolean isSpring = false;
    Double altitude;
    City city;

    public void setCity(City city) {
        this.city = city;
    }

    public City getCity() {
        return city;
    }

    public boolean isCity() {
        return city != null;
    }
    public boolean isSpring() {
        return isSpring;
    }

    public void setSpring(Boolean spring) {
        isSpring = spring;
    }

    public void setThickness(Float thickness) {
        vertex.setThickness(thickness);
    }

    public Vertex getVertex() {
        return vertex;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public static VertexDecoratorBuilder newBuilder() {
        return new VertexDecoratorBuilder();
    }

    protected VertexDecorator(Vertex vertex) {
        this.vertex = vertex;
    }

    public double getX() {
        return this.vertex.getX();
    }

    public double getY() {
        return this.vertex.getY();
    }

    public void setColor(Color color) {
        vertex.setColor(color);
    }

    @Override
    public String toString() {
        return "("+getX()+", "+getY()+")";
    }
}
