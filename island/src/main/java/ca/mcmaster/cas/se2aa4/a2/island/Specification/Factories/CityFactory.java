package ca.mcmaster.cas.se2aa4.a2.island.Specification.Factories;

import ca.mcmaster.cas.se2aa4.a2.island.Configuration.Configuration;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Urbanism.CityGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Urbanism.StarNetwork;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Water.RiverGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;

import java.util.HashMap;
import java.util.Map;

public class CityFactory implements FeatureRunner {

    private static final Map<String, Class> bindings = new HashMap<>();
    private static final String DEFAULT = null;

    static {
        bindings.put("star", StarNetwork.class);
        bindings.put(DEFAULT, StarNetwork.class);
        // TODO: Implement a non-star option later
    }

    @Override
    public void run(Island island, Configuration config) {
        try {
            String input = config.export(Configuration.CITY);
            String cityType = config.export(Configuration.NETWORK);
            Integer numOfCities;
            if (input != null) {
                numOfCities = Integer.parseInt(input);
            } else {
                numOfCities = 0;
            }
            Class network = bindings.get(cityType);
            CityGenerator city = (CityGenerator) network.getDeclaredConstructor().newInstance();
            city.process(island, numOfCities);

        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
