package com.hyfactory.hygrip.components;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link CraneStateData} state logic (setJob, isArmAt).
 * Uses CraneStateData directly - no Hytale runtime dependency.
 */
class CraneStateComponentTest {

    private CraneStateData data;

    @BeforeEach
    void setUp() {
        data = new CraneStateData();
        data.setBaseX(0);
        data.setBaseY(0);
        data.setBaseZ(0);
    }

    @Test
    void setJobMovesArmToBaseAndStartsMovingToSource() {
        data.setJob(1, 0, 0, -1, 0, 0);

        assertEquals(0, data.getArmX());
        assertEquals(0, data.getArmY());
        assertEquals(0, data.getArmZ());
        assertEquals(CranePhase.MOVING_TO_SOURCE, data.getPhase());
        assertEquals(1, data.getSourceX());
        assertEquals(0, data.getSourceY());
        assertEquals(0, data.getSourceZ());
        assertEquals(-1, data.getTargetX());
        assertEquals(0, data.getTargetY());
        assertEquals(0, data.getTargetZ());
    }

    @Test
    void isArmAtReturnsTrueWhenPositionMatches() {
        data.setArmX(3);
        data.setArmY(5);
        data.setArmZ(7);
        assertTrue(data.isArmAt(3, 5, 7));
    }

    @Test
    void isArmAtReturnsFalseWhenAnyCoordinateDiffers() {
        data.setArmX(3);
        data.setArmY(5);
        data.setArmZ(7);
        assertFalse(data.isArmAt(2, 5, 7));
        assertFalse(data.isArmAt(3, 4, 7));
        assertFalse(data.isArmAt(3, 5, 8));
    }

    @Test
    void gettersAndSettersForGripDefinitionId() {
        // The new system uses gripDefinitionId instead of moveSpeed/maxReach
        // The actual speed/maxReach comes from GripRegistry based on the ID
        data.setGripDefinitionId("hygrip:standard_hook");
        assertEquals("hygrip:standard_hook", data.getGripDefinitionId());
        
        // Test held item entity tracking (Phase 4)
        data.setHeldItemEntityId(12345);
        assertTrue(data.hasHeldItemEntity());
        assertEquals(12345, data.getHeldItemEntityId());
        
        data.setHeldItemEntityId(-1);
        assertFalse(data.hasHeldItemEntity());
    }
}
