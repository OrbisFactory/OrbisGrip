package com.hyfactory.hygrip.plugin;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Registry for Grip definitions, loaded from JSON.
 */
public class GripRegistry {
    private static final Logger LOGGER = Logger.getLogger(GripRegistry.class.getName());
    private final Map<String, GripDefinition> grips = new HashMap<>();
    private final Gson gson = new Gson();

    /**
     * Loads a single Grip definition from a JSON file path.
     * @param path the path to the JSON file
     */
    public void load(Path path) {
        try (InputStream is = Files.newInputStream(path);
             InputStreamReader reader = new InputStreamReader(is)) {
            GripDefinition grip = gson.fromJson(reader, GripDefinition.class);
            if (grip != null && grip.getId() != null) {
                grips.put(grip.getId(), grip);
                LOGGER.info("Loaded grip definition: " + grip.getId());
            }
        } catch (Exception e) {
            LOGGER.severe("Failed to load grip definition from " + path + ": " + e.getMessage());
        }
    }

    /**
     * Gets a Grip definition by its ID.
     * @param id the definition ID
     * @return the Grip definition or null if not found
     */
    public GripDefinition getGrip(String id) {
        return grips.get(id);
    }

    /**
     * Clears and returns the current grip registry map.
     */
    public Map<String, GripDefinition> getAllGrips() {
        return grips;
    }
}
