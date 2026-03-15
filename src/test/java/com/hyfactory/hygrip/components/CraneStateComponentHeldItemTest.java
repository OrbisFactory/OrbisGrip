package com.hyfactory.hygrip.components;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CraneStateComponentHeldItemTest {

    private CraneStateData data;

    @BeforeEach
    void setUp() {
        data = new CraneStateData();
    }

    @Test
    void heldItemTypeIdIsNullByDefault() {
        assertNull(data.getHeldItemTypeId());
    }

    @Test
    void setHeldItemTypeIdStoresBlockItemType() {
        data.setHeldItemTypeId("block:stone");
        assertEquals("block:stone", data.getHeldItemTypeId());
    }

    @Test
    void setHeldItemTypeIdStoresItemType() {
        data.setHeldItemTypeId("item:iron_ingot");
        assertEquals("item:iron_ingot", data.getHeldItemTypeId());
    }

    @Test
    void setHeldItemTypeIdCanBeCleared() {
        data.setHeldItemTypeId("block:diamond_ore");
        assertNotNull(data.getHeldItemTypeId());
        
        data.setHeldItemTypeId(null);
        assertNull(data.getHeldItemTypeId());
    }

    @Test
    void heldItemEntityIdIsNegativeByDefault() {
        assertEquals(-1, data.getHeldItemEntityId());
        assertFalse(data.hasHeldItemEntity());
    }

    @Test
    void hasHeldItemEntityReturnsTrueWhenEntityIdIsSet() {
        data.setHeldItemEntityId(100);
        assertTrue(data.hasHeldItemEntity());
        assertEquals(100, data.getHeldItemEntityId());
    }

    @Test
    void hasHeldItemEntityReturnsFalseWhenEntityIdIsReset() {
        data.setHeldItemEntityId(100);
        assertTrue(data.hasHeldItemEntity());
        
        data.setHeldItemEntityId(-1);
        assertFalse(data.hasHeldItemEntity());
    }

    @Test
    void clonePreservesHeldItemTypeId() {
        data.setHeldItemTypeId("block:redstone_ore");
        data.setHeldItemEntityId(500);
        
        CraneStateData cloned = data.clone();
        
        assertEquals("block:redstone_ore", cloned.getHeldItemTypeId());
        assertEquals(500, cloned.getHeldItemEntityId());
    }

    @Test
    void clonePreservesNullHeldItemTypeId() {
        CraneStateData cloned = data.clone();
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
            data.setHeldItemTypeId(itemTypeId);
            assertEquals(itemTypeId, data.getHeldItemTypeId());
        }
    }

    @Test
    void setJobDoesNotAffectHeldItemTypeId() {
        data.setHeldItemTypeId("block:gold_block");
        data.setJob(10, 20, 30, 40, 50, 60);
        
        assertEquals("block:gold_block", data.getHeldItemTypeId());
    }
}
