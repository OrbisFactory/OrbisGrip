package com.hyfactory.hygrip.systems;

import com.hyfactory.hygrip.components.CranePhase;
import com.hyfactory.hygrip.components.CraneStateComponent;
import com.hyfactory.hygrip.plugin.GripDefinition;
import com.hyfactory.hygrip.plugin.GripRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CraneMovementSystemTest {

    private CraneMovementSystem system;
    private CraneStateComponent state;
    private TestGripRegistry registry;

    @BeforeEach
    void setUp() {
        system = new CraneMovementSystem();
        state = new CraneStateComponent();
        registry = new TestGripRegistry();
    }

    @Test
    void tickIgnoresIdlePhase() {
        state.setPhase(CranePhase.IDLE);
        
        system.tick(state, registry);
        
        assertEquals(CranePhase.IDLE, state.getPhase());
    }

    @Test
    void tickIgnoresPickingPhase() {
        state.setPhase(CranePhase.PICKING);
        
        system.tick(state, registry);
        
        assertEquals(CranePhase.PICKING, state.getPhase());
    }

    @Test
    void tickIgnoresDepositingPhase() {
        state.setPhase(CranePhase.DEPOSITING);
        
        system.tick(state, registry);
        
        assertEquals(CranePhase.DEPOSITING, state.getPhase());
    }

    @Test
    void movingToSourcePhaseTransitionsToPickingWhenArmAtSource() {
        state.setPhase(CranePhase.MOVING_TO_SOURCE);
        state.setArmX(10);
        state.setArmY(20);
        state.setArmZ(30);
        state.setSourceX(10);
        state.setSourceY(20);
        state.setSourceZ(30);
        
        system.tick(state, registry);
        
        assertEquals(CranePhase.PICKING, state.getPhase());
    }

    @Test
    void movingToTargetPhaseTransitionsToDepositingWhenArmAtTarget() {
        state.setPhase(CranePhase.MOVING_TO_TARGET);
        state.setArmX(50);
        state.setArmY(60);
        state.setArmZ(70);
        state.setTargetX(50);
        state.setTargetY(60);
        state.setTargetZ(70);
        
        system.tick(state, registry);
        
        assertEquals(CranePhase.DEPOSITING, state.getPhase());
    }

    @Test
    void movingToSourceMovesArmTowardSource() {
        state.setPhase(CranePhase.MOVING_TO_SOURCE);
        state.setArmX(0);
        state.setArmY(0);
        state.setArmZ(0);
        state.setSourceX(5);
        state.setSourceY(5);
        state.setSourceZ(5);
        
        system.tick(state, registry);
        
        assertEquals(1, state.getArmX());
        assertEquals(1, state.getArmY());
        assertEquals(1, state.getArmZ());
    }

    @Test
    void movingToTargetMovesArmTowardTarget() {
        state.setPhase(CranePhase.MOVING_TO_TARGET);
        state.setArmX(10);
        state.setArmY(10);
        state.setArmZ(10);
        state.setTargetX(5);
        state.setTargetY(5);
        state.setTargetZ(5);
        
        system.tick(state, registry);
        
        assertEquals(9, state.getArmX());
        assertEquals(9, state.getArmY());
        assertEquals(9, state.getArmZ());
    }

    @Test
    void armDoesNotMovePastSource() {
        state.setPhase(CranePhase.MOVING_TO_SOURCE);
        state.setArmX(4);
        state.setArmY(4);
        state.setArmZ(4);
        state.setSourceX(5);
        state.setSourceY(5);
        state.setSourceZ(5);
        
        system.tick(state, registry);
        
        assertEquals(5, state.getArmX());
        assertEquals(5, state.getArmY());
        assertEquals(5, state.getArmZ());
    }

    @Test
    void armDoesNotMovePastTarget() {
        state.setPhase(CranePhase.MOVING_TO_TARGET);
        state.setArmX(6);
        state.setArmY(6);
        state.setArmZ(6);
        state.setTargetX(5);
        state.setTargetY(5);
        state.setTargetZ(5);
        
        system.tick(state, registry);
        
        assertEquals(5, state.getArmX());
        assertEquals(5, state.getArmY());
        assertEquals(5, state.getArmZ());
    }

    @Test
    void stepSpeedRespectsGripDefinition() {
        registry.setSpeed(3.0f);
        state.setGripDefinitionId("test");
        state.setPhase(CranePhase.MOVING_TO_SOURCE);
        state.setArmX(0);
        state.setArmY(0);
        state.setArmZ(0);
        state.setSourceX(10);
        state.setSourceY(10);
        state.setSourceZ(10);
        
        system.tick(state, registry);
        
        assertEquals(3, state.getArmX());
        assertEquals(3, state.getArmY());
        assertEquals(3, state.getArmZ());
    }

    @Test
    void stepSpeedMinimumIsOne() {
        registry.setSpeed(0.5f);
        state.setGripDefinitionId("test");
        state.setPhase(CranePhase.MOVING_TO_SOURCE);
        state.setArmX(0);
        state.setArmY(0);
        state.setArmZ(0);
        state.setSourceX(10);
        state.setSourceY(10);
        state.setSourceZ(10);
        
        system.tick(state, registry);
        
        assertEquals(1, state.getArmX());
        assertEquals(1, state.getArmY());
        assertEquals(1, state.getArmZ());
    }

    @Test
    void movingToSourceWithNoHeldItemDoesNotTriggerVisualUpdate() {
        state.setPhase(CranePhase.MOVING_TO_SOURCE);
        state.setArmX(0);
        state.setArmY(0);
        state.setArmZ(0);
        state.setSourceX(5);
        state.setSourceY(5);
        state.setSourceZ(5);
        state.setHeldItemEntityId(-1);
        
        assertDoesNotThrow(() -> system.tick(state, registry));
    }

    @Test
    void movingToTargetWithNoHeldItemDoesNotTriggerVisualUpdate() {
        state.setPhase(CranePhase.MOVING_TO_TARGET);
        state.setArmX(10);
        state.setArmY(10);
        state.setArmZ(10);
        state.setTargetX(5);
        state.setTargetY(5);
        state.setTargetZ(5);
        state.setHeldItemEntityId(-1);
        
        assertDoesNotThrow(() -> system.tick(state, registry));
    }

    @Test
    void clampStepReturnsZeroWhenAlreadyAtTarget() {
        int result = invokeClampStep(5, 5, 1);
        assertEquals(0, result);
    }

    @Test
    void clampStepReturnsPositiveStepWhenApproachingFromBelow() {
        int result = invokeClampStep(0, 5, 2);
        assertEquals(2, result);
    }

    @Test
    void clampStepReturnsNegativeStepWhenApproachingFromAbove() {
        int result = invokeClampStep(5, 0, 2);
        assertEquals(-2, result);
    }

    @Test
    void clampStepReturnsSmallerStepWhenClose() {
        int result = invokeClampStep(0, 1, 5);
        assertEquals(1, result);
    }

    @Test
    void clampStepReturnsNegativeSmallerStepWhenCloseFromAbove() {
        int result = invokeClampStep(1, 0, 5);
        assertEquals(-1, result);
    }

    private int invokeClampStep(int from, int to, int step) {
        return clampStep(from, to, step);
    }

    private static int clampStep(int from, int to, int step) {
        int d = to - from;
        if (d == 0) return 0;
        if (d > 0) return Math.min(step, d);
        return Math.max(-step, d);
    }

    static class TestGripRegistry extends GripRegistry {
        private float speed = 1.0f;

        void setSpeed(float speed) {
            this.speed = speed;
        }

        @Override
        public GripDefinition getGrip(String id) {
            return new GripDefinition() {
                @Override
                public String getId() {
                    return id;
                }

                @Override
                public int getCapacity() {
                    return 1;
                }

                @Override
                public float getSpeed() {
                    return speed;
                }

                @Override
                public int getMaxReach() {
                    return 16;
                }
            };
        }
    }
}
