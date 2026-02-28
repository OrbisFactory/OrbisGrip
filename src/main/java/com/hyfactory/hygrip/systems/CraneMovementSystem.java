package com.hyfactory.hygrip.systems;

import com.hyfactory.hygrip.components.CranePhase;
import com.hyfactory.hygrip.components.CraneStateComponent;
import com.hyfactory.hygrip.plugin.GripDefinition;
import com.hyfactory.hygrip.plugin.GripRegistry;
import com.hypixel.hytale.server.core.universe.world.World;

/**
 * Updates crane arm position and phase transitions that depend only on movement.
 * Run before CraneInteractionSystem each tick.
 * 
 * Phase B: Also updates visual entity position to follow crane arm (bone_hook attachment).
 */
public final class CraneMovementSystem {

    private final CraneInteractionSystem interactionSystem = new CraneInteractionSystem();
    
    public void tick(World world, CraneStateComponent state, GripRegistry registry) {
        CranePhase phase = state.getPhase();
        
        int step = 1;
        String defId = state.getGripDefinitionId();
        if (defId != null && registry != null) {
            GripDefinition def = registry.getGrip(defId);
            if (def != null) {
                step = Math.max(1, (int) def.getSpeed());
            }
        }

        int dx = 0, dy = 0, dz = 0;

        if (phase == CranePhase.MOVING_TO_SOURCE) {
            dx = clampStep(state.getArmX(), state.getSourceX(), step);
            dy = clampStep(state.getArmY(), state.getSourceY(), step);
            dz = clampStep(state.getArmZ(), state.getSourceZ(), step);
        } else if (phase == CranePhase.MOVING_TO_TARGET) {
            dx = clampStep(state.getArmX(), state.getTargetX(), step);
            dy = clampStep(state.getArmY(), state.getTargetY(), step);
            dz = clampStep(state.getArmZ(), state.getTargetZ(), step);
        }

        state.setArmX(state.getArmX() + dx);
        state.setArmY(state.getArmY() + dy);
        state.setArmZ(state.getArmZ() + dz);
        
        // Phase B: Update visual entity position to follow crane arm (bone_hook tracking)
        if (state.hasHeldItemEntity()) {
            interactionSystem.updateVisualEntityPosition(world, state);
        }

        if (phase == CranePhase.MOVING_TO_SOURCE && state.isArmAt(state.getSourceX(), state.getSourceY(), state.getSourceZ())) {
            state.setPhase(CranePhase.PICKING);
        } else if (phase == CranePhase.MOVING_TO_TARGET && state.isArmAt(state.getTargetX(), state.getTargetY(), state.getTargetZ())) {
            state.setPhase(CranePhase.DEPOSITING);
        }
    }
    
    /**
     * Tick overload for systems that don't have world access (e.g., pure state logic).
     */
    public void tick(CraneStateComponent state, GripRegistry registry) {
        CranePhase phase = state.getPhase();
        if (phase != CranePhase.MOVING_TO_SOURCE && phase != CranePhase.MOVING_TO_TARGET) {
            return;
        }

        int step = 1;
        String defId = state.getGripDefinitionId();
        if (defId != null && registry != null) {
            GripDefinition def = registry.getGrip(defId);
            if (def != null) {
                step = Math.max(1, (int) def.getSpeed());
            }
        }

        int dx = 0, dy = 0, dz = 0;

        if (phase == CranePhase.MOVING_TO_SOURCE) {
            dx = clampStep(state.getArmX(), state.getSourceX(), step);
            dy = clampStep(state.getArmY(), state.getSourceY(), step);
            dz = clampStep(state.getArmZ(), state.getSourceZ(), step);
        } else {
            dx = clampStep(state.getArmX(), state.getTargetX(), step);
            dy = clampStep(state.getArmY(), state.getTargetY(), step);
            dz = clampStep(state.getArmZ(), state.getTargetZ(), step);
        }

        state.setArmX(state.getArmX() + dx);
        state.setArmY(state.getArmY() + dy);
        state.setArmZ(state.getArmZ() + dz);

        if (phase == CranePhase.MOVING_TO_SOURCE && state.isArmAt(state.getSourceX(), state.getSourceY(), state.getSourceZ())) {
            state.setPhase(CranePhase.PICKING);
        } else if (phase == CranePhase.MOVING_TO_TARGET && state.isArmAt(state.getTargetX(), state.getTargetY(), state.getTargetZ())) {
            state.setPhase(CranePhase.DEPOSITING);
        }
    }

    private static int clampStep(int from, int to, int step) {
        int d = to - from;
        if (d == 0) return 0;
        if (d > 0) return Math.min(step, d);
        return Math.max(-step, d);
    }
}
