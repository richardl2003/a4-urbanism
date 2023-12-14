package ca.mcmaster.cas.se2aa4.a2.generator.Meshs;

import org.locationtech.jts.geom.Coordinate;

import java.util.ArrayList;
import java.util.List;

/**
 * Mutable Mesh.Mesh that generates hexagonal tessellation.
 * Apothem is the length from the middle to a side
 */
public class HexRegularMesh extends RegularMesh {
    protected List<Coordinate> generatePoints() {
        List<Coordinate> coordList = new ArrayList<>();
        int hexWall = 15;
        int apothem = (int) (hexWall * Math.sqrt(3) / 2);
        int hexWidth = 2*apothem + hexWall;
        int spacing = apothem + hexWall/2;

        int rowCounter = 0;
        for (int i=apothem; i<height; i+=apothem) {
            for (int j=apothem; j<width; j+=hexWidth) {
                if (rowCounter%2 == 0) {
                    coordList.add(new Coordinate((j),(i)));
                }
                else {
                    coordList.add(new Coordinate((j+spacing),(i)));
                }
            }
            rowCounter++;
        }
        return coordList;
    }
}
