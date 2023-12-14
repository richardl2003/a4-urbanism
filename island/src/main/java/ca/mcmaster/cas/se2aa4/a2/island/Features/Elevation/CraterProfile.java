package ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation;

import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;

public class CraterProfile extends ElevationUtil{
    /**
     * Implementation of calculateAltitude for a Crater
     * @param vertex, VertexDecorator
     * @return Integer, represents the altitude level
     */
    protected Double calculateAltitude(VertexDecorator vertex) {
        Double distanceFromCenter = getDistance(center,vertex.getVertex().getCoordinate());
        Double slope = 200/(0.35* Math.min(island.width(), island.height()));
        Double y = minAltitude + 1 + distanceFromCenter * slope;
        if (y > maxAltitude) return maxAltitude;
        return y;
    }
}
