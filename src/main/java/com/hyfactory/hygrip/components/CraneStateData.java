package com.hyfactory.hygrip.components;

import javax.annotation.Nullable;

/**
 * Pure POJO holding crane state data.
 * No Hytale runtime dependencies - safe for unit testing.
 */
public class CraneStateData {

    // Position fields
    private int baseX;
    private int baseY;
    private int baseZ;
    private int armX;
    private int armY;
    private int armZ;

    // Job state
    private CranePhase phase = CranePhase.IDLE;
    private int sourceX;
    private int sourceY;
    private int sourceZ;
    private int targetX;
    private int targetY;
    private int targetZ;

    // Grip and item tracking
    private String gripDefinitionId;
    private int heldItemEntityId = -1;
    @Nullable
    private String heldItemTypeId;

    // Position getters/setters
    public int getBaseX() { return baseX; }
    public void setBaseX(int baseX) { this.baseX = baseX; }
    public int getBaseY() { return baseY; }
    public void setBaseY(int baseY) { this.baseY = baseY; }
    public int getBaseZ() { return baseZ; }
    public void setBaseZ(int baseZ) { this.baseZ = baseZ; }

    public int getArmX() { return armX; }
    public void setArmX(int armX) { this.armX = armX; }
    public int getArmY() { return armY; }
    public void setArmY(int armY) { this.armY = armY; }
    public int getArmZ() { return armZ; }
    public void setArmZ(int armZ) { this.armZ = armZ; }

    // Phase getter/setter
    public CranePhase getPhase() { return phase; }
    public void setPhase(CranePhase phase) { this.phase = phase; }

    // Source position getters/setters
    public int getSourceX() { return sourceX; }
    public void setSourceX(int sourceX) { this.sourceX = sourceX; }
    public int getSourceY() { return sourceY; }
    public void setSourceY(int sourceY) { this.sourceY = sourceY; }
    public int getSourceZ() { return sourceZ; }
    public void setSourceZ(int sourceZ) { this.sourceZ = sourceZ; }

    // Target position getters/setters
    public int getTargetX() { return targetX; }
    public void setTargetX(int targetX) { this.targetX = targetX; }
    public int getTargetY() { return targetY; }
    public void setTargetY(int targetY) { this.targetY = targetY; }
    public int getTargetZ() { return targetZ; }
    public void setTargetZ(int targetZ) { this.targetZ = targetZ; }

    // Grip definition getter/setter
    public String getGripDefinitionId() { return gripDefinitionId; }
    public void setGripDefinitionId(String gripDefinitionId) { this.gripDefinitionId = gripDefinitionId; }

    // Held item entity getters/setters
    public int getHeldItemEntityId() { return heldItemEntityId; }
    public void setHeldItemEntityId(int heldItemEntityId) { this.heldItemEntityId = heldItemEntityId; }
    public boolean hasHeldItemEntity() { return heldItemEntityId != -1; }

    // Held item type ID getters/setters
    @Nullable
    public String getHeldItemTypeId() { return heldItemTypeId; }
    public void setHeldItemTypeId(@Nullable String heldItemTypeId) { this.heldItemTypeId = heldItemTypeId; }

    /**
     * Sets current job: source and target block positions; moves arm to base and starts MOVING_TO_SOURCE.
     */
    public void setJob(int sourceX, int sourceY, int sourceZ, int targetX, int targetY, int targetZ) {
        this.sourceX = sourceX;
        this.sourceY = sourceY;
        this.sourceZ = sourceZ;
        this.targetX = targetX;
        this.targetY = targetY;
        this.targetZ = targetZ;
        this.armX = baseX;
        this.armY = baseY;
        this.armZ = baseZ;
        this.phase = CranePhase.MOVING_TO_SOURCE;
    }

    /**
     * Checks if the arm is at the specified position.
     */
    public boolean isArmAt(int x, int y, int z) {
        return armX == x && armY == y && armZ == z;
    }

    @Override
    public CraneStateData clone() {
        CraneStateData copy = new CraneStateData();
        copy.baseX = this.baseX;
        copy.baseY = this.baseY;
        copy.baseZ = this.baseZ;
        copy.armX = this.armX;
        copy.armY = this.armY;
        copy.armZ = this.armZ;
        copy.phase = this.phase;
        copy.sourceX = this.sourceX;
        copy.sourceY = this.sourceY;
        copy.sourceZ = this.sourceZ;
        copy.targetX = this.targetX;
        copy.targetY = this.targetY;
        copy.targetZ = this.targetZ;
        copy.gripDefinitionId = this.gripDefinitionId;
        copy.heldItemEntityId = this.heldItemEntityId;
        copy.heldItemTypeId = this.heldItemTypeId;
        return copy;
    }
}
