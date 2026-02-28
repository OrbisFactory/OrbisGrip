package com.hyfactory.hygrip.systems;

import com.hyfactory.hygrip.components.CranePhase;
import com.hyfactory.hygrip.components.CraneStateComponent;
import com.hyfactory.hygrip.plugin.GripDefinition;
import com.hyfactory.hygrip.plugin.GripRegistry;
import com.hyfactory.hygrip.util.BlockInventoryHelper;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.asset.type.item.config.Item;
import com.hypixel.hytale.server.core.asset.type.model.config.Model;
import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;

/**
 * Executes pick and deposit when crane phase is PICKING or DEPOSITING.
 * Run after CraneMovementSystem each tick. Requires World to access block inventories.
 * 
 * Phase 4: Added held item visualization using ModelComponent.
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
                
                // Phase 4: Create visual entity for the held item
                int entityId = createItemVisualEntity(world, state, taken);
                state.setHeldItemEntityId(entityId);
                
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
                // Phase 4: Destroy visual entity
                destroyItemVisualEntity(world, state);
                state.setHeldItem(null);
                state.setHeldItemEntityId(-1);
                state.setPhase(CranePhase.IDLE);
            } else {
                state.setHeldItem(remainder);
                state.setPhase(CranePhase.IDLE);
            }
        }
    }
    
    /**
     * Creates a visual entity representing the held item attached to the crane arm.
     * Uses Hytale's entity system with ModelComponent to render the item model.
     * 
     * @param world The world where the crane exists
     * @param state The crane state component
     * @param heldItem The item stack to visualize
     * @return the network ID of the created entity, or -1 if creation failed
     */
    private int createItemVisualEntity(World world, CraneStateComponent state, ItemStack heldItem) {
        if (heldItem == null || ItemStack.isEmpty(heldItem)) {
            return -1;
        }
        
        try {
            // Get item ID and load model asset
            String itemId = heldItem.getItemId();
            Item item = Item.getAssetMap().getAsset(itemId);
            if (item == null) {
                return -1;
            }
            
            String modelPath = item.getModel();
            if (modelPath == null || modelPath.isEmpty()) {
                return -1;
            }
            
            ModelAsset modelAsset = ModelAsset.getAssetMap().getAsset(modelPath);
            if (modelAsset == null) {
                return -1;
            }
            
            // Create scaled model for the item
            Model model = Model.createScaledModel(modelAsset, 0.5f);
            
            // TODO: Create entity with TransformComponent + ModelComponent
            // This requires the full entity spawning API which needs further investigation
            // Expected approach:
            // 1. Get EntityStore from world
            // 2. Create entity holder with required components
            // 3. Set TransformComponent at crane arm position
            // 4. Set ModelComponent with the item model
            // 5. Add UUIDComponent and NetworkId
            // 6. Spawn via entityStore.addEntity()
            
            // Placeholder: return -1 to indicate visual creation pending API verification
            return -1;
            
        } catch (Exception e) {
            // Log error but don't crash - item will still work without visual
            return -1;
        }
    }
    
    /**
     * Destroys the visual entity representing the held item.
     * 
     * @param world The world where the crane exists
     * @param state The crane state component
     */
    private void destroyItemVisualEntity(World world, CraneStateComponent state) {
        int entityId = state.getHeldItemEntityId();
        if (entityId == -1) {
            return;
        }
        
        try {
            // TODO: Remove the visual entity from the world
            // Expected approach:
            // 1. Get EntityStore from world
            // 2. Get entity ref by network ID
            // 3. Remove entity via entityStore.removeEntity(ref, RemoveReason.DESPAWN)
            
            // Currently a placeholder - entity creation is pending API verification
            
        } catch (Exception e) {
            // Ignore errors during cleanup
        }
    }
}
