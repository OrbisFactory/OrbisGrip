package com.hyfactory.hygrip.systems;

import com.hyfactory.hygrip.components.CranePhase;
import com.hyfactory.hygrip.components.CraneStateData;
import com.hyfactory.hygrip.plugin.GripDefinition;
import com.hyfactory.hygrip.plugin.GripRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CraneInteractionSystemTest {

    private CraneInteractionSystem system;
    private CraneStateData state;

    @BeforeEach
    void setUp() {
        system = new CraneInteractionSystem();
        state = new CraneStateData();
    }

    @Test
    void updateVisualEntityPositionDoesNotThrowWithNoEntity() {
        state.setHeldItemEntityId(-1);
        
        assertDoesNotThrow(() -> {
            system.updateVisualEntityPosition(null, state);
        });
    }

    @Test
    void heldItemEntityIdIsNegativeByDefault() {
        assertEquals(-1, state.getHeldItemEntityId());
        assertFalse(state.hasHeldItemEntity());
    }

    @Test
    void hasHeldItemEntityReturnsTrueWhenEntityIdIsSet() {
        state.setHeldItemEntityId(100);
        assertTrue(state.hasHeldItemEntity());
    }

    @Test
    void hasHeldItemEntityReturnsFalseWhenReset() {
        state.setHeldItemEntityId(100);
        assertTrue(state.hasHeldItemEntity());
        state.setHeldItemEntityId(-1);
        assertFalse(state.hasHeldItemEntity());
    }

    @Test
    void heldItemTypeIdIsNullByDefault() {
        assertNull(state.getHeldItemTypeId());
    }

    @Test
    void heldItemTypeIdCanBeSet() {
        state.setHeldItemTypeId("block:stone");
        assertEquals("block:stone", state.getHeldItemTypeId());
    }

    @Test
    void heldItemTypeIdCanBeCleared() {
        state.setHeldItemTypeId("block:gold");
        state.setHeldItemTypeId(null);
        assertNull(state.getHeldItemTypeId());
    }

    @Test
    void phaseIsIdleByDefault() {
        assertEquals(CranePhase.IDLE, state.getPhase());
    }

    @Test
    void phaseCanBeChanged() {
        state.setPhase(CranePhase.PICKING);
        assertEquals(CranePhase.PICKING, state.getPhase());
    }
}
