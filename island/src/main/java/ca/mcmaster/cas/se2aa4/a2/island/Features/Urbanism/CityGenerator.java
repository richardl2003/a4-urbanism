package ca.mcmaster.cas.se2aa4.a2.island.Features.Urbanism;

import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;

/**
 * Interface for generating a city when called upon
 */
public interface CityGenerator {

    void process(Island container, Integer cities);

}
