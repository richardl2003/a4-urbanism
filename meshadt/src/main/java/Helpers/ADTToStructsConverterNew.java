package Helpers;

import EnhancedSets.GeometrySet;
import EnhancedSets.PolygonSet;
import EnhancedSets.SegmentSet;
import EnhancedSets.VertexSet;
import Geometries.Polygon;
import Geometries.Segment;
import Geometries.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.LinkedHashSet;
import java.util.Set;

public class ADTToStructsConverterNew {
    Set<Structs.Vertex> vertices;
    Set<Structs.Segment> segments;
    Set<Structs.Polygon> polygons;

    public ADTToStructsConverterNew(GeometrySet<Vertex> vertices, GeometrySet<Segment> segments, GeometrySet<Polygon> polygons) {
        this.vertices = convertVertices(vertices);
        this.segments = convertSegments(segments);
        this.polygons = convertPolygons(polygons);
    }

    public Structs.Mesh process() {
        return Structs.Mesh.newBuilder().addAllVertices(vertices).addAllSegments(segments).addAllPolygons(polygons).build();
    }

    /**
     * Iterates through the GeometrySet, associates an ID for every Vertex, and returns a Set
     * @param vertices is a GeometrySet of type Vertex (Geometries Package)
     * @return Set that contains the type Structs.Vertex
     */
    private Set<Structs.Vertex> convertVertices(GeometrySet<Vertex> vertices) {
        Set<Structs.Vertex> vertexSet = new LinkedHashSet<>();
        int counter = 0;
        for (Vertex vertex : vertices) {
            vertex.setId(counter);
            vertexSet.add(vertex.getVertex());
            counter++;
        }
        return vertexSet;
    }

    /**
     * Iterates through the GeometrySet, associates an ID for every Segment, and returns a Set
     * @param segments is a GeometrySet of type Segment (Geometries Package)
     * @return Set that contains the type Structs.Segment
     */
    private Set<Structs.Segment> convertSegments(GeometrySet<Segment> segments) {
        Set<Structs.Segment> segmentSet = new LinkedHashSet<>();
        int counter = 0;
        for (Segment segment : segments) {
            segment.setId(counter);
            segment.generateSegment();
            segmentSet.add(segment.getSegment());
            counter++;
        }
        return segmentSet;
    }

    /**
     * Iterates through the GeometrySet, associates an ID for every Polygon, and returns a Set
     * @param polygons is a GeometrySet of type Polygon (Geometries Package)
     * @return Set that contains the type Structs.Polygon
     */
    private Set<Structs.Polygon> convertPolygons(GeometrySet<Polygon> polygons) {
        Set<Structs.Polygon> polygonSet = new LinkedHashSet<>();
        for (Polygon polygon: polygons) {
            polygon.generatePolygon();
            polygonSet.add(polygon.getPolygon());
        }
        return polygonSet;
    }
}
