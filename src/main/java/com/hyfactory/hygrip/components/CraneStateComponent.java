package com.hyfactory.hygrip.components;

import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.universe.world.meta.BlockState;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Component holding crane/arm state data for HyGrip.
 * Compatible with Hytale ECS (BlockState / Component<ChunkStore>).
 * Delegates all state to internal CraneStateData for testability.
 */
public class CraneStateComponent extends BlockState {

    public static final Codec<CraneStateComponent> CODEC = new Codec<CraneStateComponent>() {
        @Override
        public org.bson.BsonValue encode(CraneStateComponent value, com.hypixel.hytale.codec.ExtraInfo extraInfo) {
            org.bson.BsonDocument doc = new org.bson.BsonDocument();
            if (value.getHeldItem() != null) {
                // Using manual serialization here as held item encoding might have changed too
            }
            return doc;
        }

        @Override
        public CraneStateComponent decode(org.bson.BsonValue value, com.hypixel.hytale.codec.ExtraInfo extraInfo) {
            CraneStateComponent component = new CraneStateComponent();
            if (value != null && value.isDocument()) {
                org.bson.BsonDocument doc = value.asDocument();
            }
            return component;
        }

        @Override
        public com.hypixel.hytale.codec.schema.config.Schema toSchema(com.hypixel.hytale.codec.schema.SchemaContext context) {
            return new com.hypixel.hytale.codec.schema.config.NullSchema();
        }
    };

    private CraneStateData data = new CraneStateData();

    //* Position getters/setters - delegate to CraneStateData
    public int getBaseX() { return data.getBaseX(); }
    public void setBaseX(int baseX) { data.setBaseX(baseX); }
    public int getBaseY() { return data.getBaseY(); }
    public void setBaseY(int baseY) { data.setBaseY(baseY); }
    public int getBaseZ() { return data.getBaseZ(); }
    public void setBaseZ(int baseZ) { data.setBaseZ(baseZ); }

    public int getArmX() { return data.getArmX(); }
    public void setArmX(int armX) { data.setArmX(armX); }
    public int getArmY() { return data.getArmY(); }
    public void setArmY(int armY) { data.setArmY(armY); }
    public int getArmZ() { return data.getArmZ(); }
    public void setArmZ(int armZ) { data.setArmZ(armZ); }

    //* Phase getter/setter - delegate to CraneStateData
    public CranePhase getPhase() { return data.getPhase(); }
    public void setPhase(CranePhase phase) { data.setPhase(phase); }

    //* Source position getters/setters - delegate to CraneStateData
    public int getSourceX() { return data.getSourceX(); }
    public void setSourceX(int sourceX) { data.setSourceX(sourceX); }
    public int getSourceY() { return data.getSourceY(); }
    public void setSourceY(int sourceY) { data.setSourceY(sourceY); }
    public int getSourceZ() { return data.getSourceZ(); }
    public void setSourceZ(int sourceZ) { data.setSourceZ(sourceZ); }

    //* Target position getters/setters - delegate to CraneStateData
    public int getTargetX() { return data.getTargetX(); }
    public void setTargetX(int targetX) { data.setTargetX(targetX); }
    public int getTargetY() { return data.getTargetY(); }
    public void setTargetY(int targetY) { data.setTargetY(targetY); }
    public int getTargetZ() { return data.getTargetZ(); }
    public void setTargetZ(int targetZ) { data.setTargetZ(targetZ); }

    //* Grip definition getter/setter - delegate to CraneStateData
    public String getGripDefinitionId() { return data.getGripDefinitionId(); }
    public void setGripDefinitionId(String gripDefinitionId) { data.setGripDefinitionId(gripDefinitionId); }

    //* Held item entity getters/setters - delegate to CraneStateData
    public int getHeldItemEntityId() { return data.getHeldItemEntityId(); }
    public void setHeldItemEntityId(int heldItemEntityId) { data.setHeldItemEntityId(heldItemEntityId); }
    public boolean hasHeldItemEntity() { return data.hasHeldItemEntity(); }

    //* Held item type ID getters/setters - delegate to CraneStateData
    @Nullable
    public String getHeldItemTypeId() { return data.getHeldItemTypeId(); }
    public void setHeldItemTypeId(@Nullable String heldItemTypeId) { data.setHeldItemTypeId(heldItemTypeId); }

    //* heldItem stays in component due to Hytale API dependency (ItemStack)
    @Nullable
    private ItemStack heldItem;

    @Nullable
    public ItemStack getHeldItem() { return heldItem; }
    public void setHeldItem(@Nullable ItemStack heldItem) { this.heldItem = heldItem; }

    //* Business logic delegates
    public void setJob(int sourceX, int sourceY, int sourceZ, int targetX, int targetY, int targetZ) {
        data.setJob(sourceX, sourceY, sourceZ, targetX, targetY, targetZ);
    }

    public boolean isArmAt(int x, int y, int z) {
        return data.isArmAt(x, y, z);
    }

    @Nonnull
    @Override
    public Component<ChunkStore> clone() {
        CraneStateComponent copy = new CraneStateComponent();
        copy.data = this.data.clone();
        copy.heldItem = this.heldItem != null ? this.heldItem.withQuantity(this.heldItem.getQuantity()) : null;
        return copy;
    }
}
