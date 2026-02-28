package com.hyfactory.hygrip.plugin;

/**
 * Data model for a Grip definition, loaded from JSON.
 */
public class GripDefinition {
    private String id;
    private String name;
    private String model;
    private float speed;
    private int maxReach;
    private int capacity;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public float getSpeed() { return speed; }
    public void setSpeed(float speed) { this.speed = speed; }

    public int getMaxReach() { return maxReach; }
    public void setMaxReach(int maxReach) { this.maxReach = maxReach; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
}
