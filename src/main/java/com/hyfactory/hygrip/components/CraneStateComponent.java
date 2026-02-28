package com.hyfactory.hygrip.components;

import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Component holding crane/arm state data for HyGrip.
 * Compatible with Hytale ECS (Component&lt;EntityStore&gt;).
 */
public class CraneStateComponent implements Component<EntityStore> {

    private int baseX;
    private int baseY;
    private int baseZ;
    private int armX;
    private int armY;
    private int armZ;
    private CranePhase phase = CranePhase.IDLE;
    private int sourceX;
    private int sourceY;
    private int sourceZ;
    private int targetX;
    private int targetY;
    private int targetZ;
    @Nullable
    private ItemStack heldItem;
    /** ID of the grip definition for this crane (from JSON). */
    private String gripDefinitionId;
    /** Network ID of the visual entity holding the picked item (for ModelAttachment). */
    private int heldItemEntityId = -1;
    /** Item type ID for restoration (e.g., "block:stone"). */
    @Nullable
    private String heldItemTypeId;

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

    public CranePhase getPhase() { return phase; }
    public void setPhase(CranePhase phase) { this.phase = phase; }

    public int getSourceX() { return sourceX; }
    public void setSourceX(int sourceX) { this.sourceX = sourceX; }
    public int getSourceY() { return sourceY; }
    public void setSourceY(int sourceY) { this.sourceY = sourceY; }
    public int getSourceZ() { return sourceZ; }
    public void setSourceZ(int sourceZ) { this.sourceZ = sourceZ; }

    public int getTargetX() { return targetX; }
    public void setTargetX(int targetX) { this.targetX = targetX; }
    public int getTargetY() { return targetY; }
    public void setTargetY(int targetY) { this.targetY = targetY; }
    public int getTargetZ() { return targetZ; }
    public void setTargetZ(int targetZ) { this.targetZ = targetZ; }

    @Nullable
    public ItemStack getHeldItem() { return heldItem; }
    public void setHeldItem(@Nullable ItemStack heldItem) { this.heldItem = heldItem; }

    public String getGripDefinitionId() { return gripDefinitionId; }
    public void setGripDefinitionId(String gripDefinitionId) { this.gripDefinitionId = gripDefinitionId; }

    /** Returns the network ID of the visual item entity, or -1 if none. */
    public int getHeldItemEntityId() { return heldItemEntityId; }
    /** Sets the network ID of the visual item entity. */
    public void setHeldItemEntityId(int heldItemEntityId) { this.heldItemEntityId = heldItemEntityId; }
    /** Checks if there is a visual item entity attached. */
    public boolean hasHeldItemEntity() { return heldItemEntityId != -1; }

    @Nullable
    public String getHeldItemTypeId() { return heldItemTypeId; }
    public void setHeldItemTypeId(@Nullable String heldItemTypeId) { this.heldItemTypeId = heldItemTypeId; }

    /** Sets current job: source and target block positions; moves arm to base and starts MOVING_TO_SOURCE. */
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

    public boolean isArmAt(int x, int y, int z) {
        return armX == x && armY == y && armZ == z;
    }

    @Nonnull
    @Override
    public Component<EntityStore> clone() {
        CraneStateComponent copy = new CraneStateComponent();
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
        copy.heldItem = this.heldItem != null ? this.heldItem.withQuantity(this.heldItem.getQuantity()) : null;
        copy.gripDefinitionId = this.gripDefinitionId;
        copy.heldItemEntityId = this.heldItemEntityId;
        copy.heldItemTypeId = this.heldItemTypeId;
        return copy;
    }
}
