package ca.mcmaster.cas.se2aa4.a2.island.Specification.Factories;

import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Visualizer.*;
import ca.mcmaster.cas.se2aa4.a2.island.Configuration.Configuration;

import java.util.HashMap;
import java.util.Map;

public class VisualizerFactory implements FeatureRunner {

    private static final Map<String, Class> bindings = new HashMap<>();
    private static final String DEFAULT = null;

    static {
        bindings.put("altitude", AltitudeVisualizer.class);
        bindings.put("moisture", MoistureVisualizer.class);
        bindings.put("debug", DebugVisualizer.class);
        bindings.put("biome", BiomeVisualizer.class);
        bindings.put("city", CityVisualizer.class);
        bindings.put(DEFAULT, BiomeVisualizer.class);
    }

    @Override
    public void run(Island island, Configuration config) {
        try {
            String value = config.export(Configuration.VISUAL);
            value = value != null ? value.toLowerCase() : null;
            Class visualizerClass = bindings.get(value);
            Visualizer visualizer = ((Visualizer) visualizerClass.getDeclaredConstructor().newInstance());
            visualizer.process(island);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
