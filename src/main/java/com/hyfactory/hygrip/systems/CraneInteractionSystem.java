package com.hyfactory.hygrip.systems;

import com.hyfactory.hygrip.components.CranePhase;
import com.hyfactory.hygrip.components.CraneStateComponent;
import com.hyfactory.hygrip.plugin.GripDefinition;
import com.hyfactory.hygrip.plugin.GripRegistry;
import com.hyfactory.hygrip.util.BlockInventoryHelper;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.universe.world.World;

/**
 * Executes pick and deposit when crane phase is PICKING or DEPOSITING.
 * Run after CraneMovementSystem each tick. Requires World to access block inventories.
 */
public final class CraneInteractionSystem {

    /**
     * Runs interaction logic for the given crane state. Requires the world where the crane is.
     */
    public void tick(World world, CraneStateComponent state, GripRegistry registry) {
        CranePhase phase = state.getPhase();
        
        int capacity = 1;
        String defId = state.getGripDefinitionId();
        if (defId != null && registry != null) {
            GripDefinition def = registry.getGrip(defId);
            if (def != null) {
                capacity = Math.max(1, def.getCapacity());
            }
        }

        if (phase == CranePhase.PICKING) {
            // Future improvement: use capacity for multiple items
            ItemStack taken = BlockInventoryHelper.takeOneItemFromBlock(
                    world,
                    state.getArmX(),
                    state.getArmY(),
                    state.getArmZ());
            if (taken != null && !ItemStack.isEmpty(taken)) {
                state.setHeldItem(taken);
                state.setPhase(CranePhase.MOVING_TO_TARGET);
            } else {
                state.setPhase(CranePhase.IDLE);
            }
        } else if (phase == CranePhase.DEPOSITING) {
            ItemStack held = state.getHeldItem();
            if (ItemStack.isEmpty(held)) {
                state.setPhase(CranePhase.IDLE);
                return;
            }
            ItemStack remainder = BlockInventoryHelper.putItemAtBlock(
                    world,
                    state.getArmX(),
                    state.getArmY(),
                    state.getArmZ(),
                    held);
            if (remainder == null || ItemStack.isEmpty(remainder)) {
                state.setHeldItem(null);
                state.setPhase(CranePhase.IDLE);
            } else {
                state.setHeldItem(remainder);
                state.setPhase(CranePhase.IDLE);
            }
        }
    }
}
