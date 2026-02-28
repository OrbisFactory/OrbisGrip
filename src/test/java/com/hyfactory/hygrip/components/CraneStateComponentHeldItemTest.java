package com.hyfactory.hygrip.components;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CraneStateComponentHeldItemTest {

    private CraneStateComponent state;

    @BeforeEach
    void setUp() {
        state = new CraneStateComponent();
    }

    @Test
    void heldItemTypeIdIsNullByDefault() {
        assertNull(state.getHeldItemTypeId());
    }

    @Test
    void setHeldItemTypeIdStoresBlockItemType() {
        state.setHeldItemTypeId("block:stone");
        assertEquals("block:stone", state.getHeldItemTypeId());
    }

    @Test
    void setHeldItemTypeIdStoresItemType() {
        state.setHeldItemTypeId("item:iron_ingot");
        assertEquals("item:iron_ingot", state.getHeldItemTypeId());
    }

    @Test
    void setHeldItemTypeIdCanBeCleared() {
        state.setHeldItemTypeId("block:diamond_ore");
        assertNotNull(state.getHeldItemTypeId());
        
        state.setHeldItemTypeId(null);
        assertNull(state.getHeldItemTypeId());
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
        assertEquals(100, state.getHeldItemEntityId());
    }

    @Test
    void hasHeldItemEntityReturnsFalseWhenEntityIdIsReset() {
        state.setHeldItemEntityId(100);
        assertTrue(state.hasHeldItemEntity());
        
        state.setHeldItemEntityId(-1);
        assertFalse(state.hasHeldItemEntity());
    }

    @Test
    void clonePreservesHeldItemTypeId() {
        state.setHeldItemTypeId("block:redstone_ore");
        state.setHeldItemEntityId(500);
        
        CraneStateComponent cloned = (CraneStateComponent) state.clone();
        
        assertEquals("block:redstone_ore", cloned.getHeldItemTypeId());
        assertEquals(500, cloned.getHeldItemEntityId());
    }

    @Test
    void clonePreservesNullHeldItemTypeId() {
        CraneStateComponent cloned = (CraneStateComponent) state.clone();
        assertNull(cloned.getHeldItemTypeId());
    }

    @Test
    void heldItemTypeIdWithVariousFormats() {
        String[] testCases = {
            "block:coal_block",
            "item:bow",
            "block:emerald_block",
            "item:nether_star",
            "custom:my_mod_item"
        };
        
        for (String itemTypeId : testCases) {
            state.setHeldItemTypeId(itemTypeId);
            assertEquals(itemTypeId, state.getHeldItemTypeId());
        }
    }

    @Test
    void setJobDoesNotAffectHeldItemTypeId() {
        state.setHeldItemTypeId("block:gold_block");
        state.setJob(10, 20, 30, 40, 50, 60);
        
        assertEquals("block:gold_block", state.getHeldItemTypeId());
    }
}
