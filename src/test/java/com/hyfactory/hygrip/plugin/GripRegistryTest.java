package com.hyfactory.hygrip.plugin;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class GripRegistryTest {

    @Test
    public void testLoadStandardGrip() {
        GripRegistry registry = new GripRegistry();
        
        // Path to the real standard.json in src/main/resources
        Path resourcePath = Paths.get("src", "main", "resources", "definitions", "grips", "standard.json");
        
        // Ensure the file exists before testing
        assertTrue(Files.exists(resourcePath), "standard.json should exist in resources");
        
        registry.load(resourcePath);
        
        GripDefinition standardGrip = registry.getGrip("hygrip:standard_hook");
        
        assertNotNull(standardGrip, "Standard hook should be loaded");
        assertEquals("hygrip:standard_hook", standardGrip.getId());
        assertEquals("Standard Iron Hook", standardGrip.getName());
        assertEquals(1.0f, standardGrip.getSpeed());
        assertEquals(16, standardGrip.getMaxReach());
    }

    @Test
    public void testLoadNonExistentFile() {
        GripRegistry registry = new GripRegistry();
        Path nonExistent = Paths.get("non_existent.json");
        
        registry.load(nonExistent);
        
        assertTrue(registry.getAllGrips().isEmpty(), "Registry should be empty when loading non-existent file");
    }

    @Test
    public void testLoadInvalidJson(@TempDir Path tempDir) throws IOException {
        GripRegistry registry = new GripRegistry();
        Path invalidJson = tempDir.resolve("invalid.json");
        Files.writeString(invalidJson, "{ invalid json }");
        
        registry.load(invalidJson);
        
        assertTrue(registry.getAllGrips().isEmpty(), "Registry should be empty when loading invalid JSON");
    }
}
