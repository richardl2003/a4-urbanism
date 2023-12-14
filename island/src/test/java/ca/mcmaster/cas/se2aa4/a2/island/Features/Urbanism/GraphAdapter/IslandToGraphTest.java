package ca.mcmaster.cas.se2aa4.a2.island.Features.Urbanism.GraphAdapter;

import Geometries.Polygon;
import Geometries.Segment;
import Geometries.Vertex;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;
import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IslandToGraphTest {

    List<VertexDecorator> vertices = new ArrayList<>();
    List<Border> borders = new ArrayList<>();
    List<Tile> tiles = new ArrayList<>();
    List<Segment> segmentList = new ArrayList<>();
    Island island;

    @BeforeEach
    public void setUp() {

        vertices = new ArrayList<>();
        borders = new ArrayList<>();
        tiles = new ArrayList<>();

        // Tile 1
        //Context for Polygon 1 (near edge of map)
        Vertex v1 = new Vertex(0.0,0.0);
        Vertex v2 = new Vertex(0.0,10.0);
        Vertex v3 = new Vertex(10.0,10.0);
        Vertex v4 = new Vertex(10.0,0.0);

        segmentList.add(new Segment(v1, v2));
        segmentList.add(new Segment(v2, v3));
        segmentList.add(new Segment(v3, v4));
        segmentList.add(new Segment(v4, v1));

        Polygon mockPolygon = new Polygon(segmentList);
        vertices.add(VertexDecorator.newBuilder().addVertex(v1).build());
        vertices.add(VertexDecorator.newBuilder().addVertex(v2).build());
        vertices.add(VertexDecorator.newBuilder().addVertex(v3).build());
        vertices.add(VertexDecorator.newBuilder().addVertex(v4).build());

        borders.add(Border.newBuilder().addV1(vertices.get(0)).addV2(vertices.get(1)).addSegment(segmentList.get(0)).build());
        borders.add(Border.newBuilder().addV1(vertices.get(1)).addV2(vertices.get(2)).addSegment(segmentList.get(1)).build());
        borders.add(Border.newBuilder().addV1(vertices.get(2)).addV2(vertices.get(3)).addSegment(segmentList.get(2)).build());
        borders.add(Border.newBuilder().addV1(vertices.get(3)).addV2(vertices.get(0)).addSegment(segmentList.get(3)).build());

        VertexDecorator mockCentroid = VertexDecorator.newBuilder().addVertex(new Vertex(5.0,5.0)).build();
        vertices.add(mockCentroid);

        tiles.add(Tile.newBuilder().addPolygon(mockPolygon).addBorders(borders).addCentroid(mockCentroid).build());

        //Context for Tile 2
        borders = new ArrayList<>();
        Vertex v1_1 = new Vertex(10.0,0.0);
        Vertex v2_1 = new Vertex(10.0,10.0);
        Vertex v3_1 = new Vertex(20.0,10.0);
        Vertex v4_1 = new Vertex(20.0,0.0);

        segmentList.add(new Segment(v1_1, v2_1));
        segmentList.add(new Segment(v2_1, v3_1));
        segmentList.add(new Segment(v3_1, v4_1));
        segmentList.add(new Segment(v4_1, v1_1));

        mockPolygon = new Polygon(segmentList);
        vertices.add(VertexDecorator.newBuilder().addVertex(v1_1).build());
        vertices.add(VertexDecorator.newBuilder().addVertex(v2_1).build());
        vertices.add(VertexDecorator.newBuilder().addVertex(v3_1).build());
        vertices.add(VertexDecorator.newBuilder().addVertex(v4_1).build());

        borders.add(Border.newBuilder().addV1(vertices.get(5)).addV2(vertices.get(6)).addSegment(segmentList.get(4)).build());
        borders.add(Border.newBuilder().addV1(vertices.get(6)).addV2(vertices.get(7)).addSegment(segmentList.get(5)).build());
        borders.add(Border.newBuilder().addV1(vertices.get(7)).addV2(vertices.get(8)).addSegment(segmentList.get(6)).build());
        borders.add(Border.newBuilder().addV1(vertices.get(8)).addV2(vertices.get(5)).addSegment(segmentList.get(7)).build());

        VertexDecorator mockCentroid_1 = VertexDecorator.newBuilder().addVertex(new Vertex(15.0,5.0)).build();
        vertices.add(mockCentroid_1);
        tiles.add(Tile.newBuilder().addPolygon(mockPolygon).addBorders(borders).addCentroid(mockCentroid_1).build());

        //Context for Tile 3
        borders = new ArrayList<>();
        Vertex v1_2 = new Vertex(0.0,10.0);
        Vertex v2_2 = new Vertex(0.0,20.0);
        Vertex v3_2 = new Vertex(10.0,20.0);
        Vertex v4_2 = new Vertex(10.0,10.0);

        segmentList.add(new Segment(v1_2, v2_2));
        segmentList.add(new Segment(v2_2, v3_2));
        segmentList.add(new Segment(v3_2, v4_2));
        segmentList.add(new Segment(v4_2, v1_2));

        mockPolygon = new Polygon(segmentList);
        vertices.add(VertexDecorator.newBuilder().addVertex(v1_2).build());
        vertices.add(VertexDecorator.newBuilder().addVertex(v2_2).build());
        vertices.add(VertexDecorator.newBuilder().addVertex(v3_2).build());
        vertices.add(VertexDecorator.newBuilder().addVertex(v4_2).build());

        borders.add(Border.newBuilder().addV1(vertices.get(10)).addV2(vertices.get(11)).addSegment(segmentList.get(8)).build());
        borders.add(Border.newBuilder().addV1(vertices.get(11)).addV2(vertices.get(12)).addSegment(segmentList.get(9)).build());
        borders.add(Border.newBuilder().addV1(vertices.get(12)).addV2(vertices.get(13)).addSegment(segmentList.get(10)).build());
        borders.add(Border.newBuilder().addV1(vertices.get(13)).addV2(vertices.get(10)).addSegment(segmentList.get(11)).build());

        VertexDecorator mockCentroid_2 = VertexDecorator.newBuilder().addVertex(new Vertex(5.0,15.0)).build();
        vertices.add(mockCentroid_2);
        tiles.add(Tile.newBuilder().addPolygon(mockPolygon).addBorders(borders).addCentroid(mockCentroid_2).build());

        // Make island
        island = new Island();
        island.register(vertices, borders, tiles);

    }

//    @Test
//    public void getNeighbouringTilesTest() {
//        IslandToGraph converter = new IslandToGraph();
//        List<Tile> neighbours = converter.getNeighbouringTiles(tiles.get(0), island);
//        assertEquals(Set.of(List.of(tiles.get(1), tiles.get(2))), Set.of(neighbours));
//    }

    @Test
    public void calculateEuclideanDistanceTest() {
        IslandToGraph converter = new IslandToGraph();
        Integer distance0To1 = converter.calculateEuclideanDistance(vertices.get(4), vertices.get(9), 0);
        Integer distance0To2 = converter.calculateEuclideanDistance(vertices.get(4), vertices.get(14), 0);
        assertEquals(10, distance0To1);
        assertEquals(10, distance0To2);

    }

//    @Test
//    public void getPointsAndConnectionsTest() {
//        // TODO
//        IslandToGraph converter = new IslandToGraph();
//        converter.getPointsAndConnections(island);
//        System.out.println(converter.nodeSet.getNodeToVD());
//        System.out.println(converter.edgeSet);
//    }

}
