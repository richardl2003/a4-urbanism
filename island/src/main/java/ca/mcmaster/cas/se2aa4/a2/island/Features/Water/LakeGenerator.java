package ca.mcmaster.cas.se2aa4.a2.island.Features.Water;

import ca.mcmaster.cas.se2aa4.a2.island.Features.Seed;


public class LakeGenerator extends LandWaterGenerator {
    @Override
    protected Integer getLayers() {
        Seed seed = Seed.getInstance();
        if (seed.nextBoolean()) return 1;
        else return 2;
    }

    @Override
    protected boolean canBeAdjacentWater() {
        return false;
    }

    protected BodyOfWater getNewWater(Integer multiplicity) {
        return new Lake(multiplicity);
    }
}
