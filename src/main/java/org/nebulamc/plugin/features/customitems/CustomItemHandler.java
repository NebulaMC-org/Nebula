package org.nebulamc.plugin.features.customitems;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class CustomItemHandler implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack heldItem = player.getInventory().getItemInMainHand();
        ItemStack offHandItem = player.getInventory().getItemInOffHand();

        if ((event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR))){
            if (isCustomItem(heldItem)){
                CustomItem customItem = ItemManager.items.get(getItemId(heldItem));
                customItem.handleRightClick(player, heldItem, event);
            }
            if (isCustomItem(offHandItem)){
                CustomItem customItem = ItemManager.items.get(getItemId(offHandItem));
                customItem.handleOffHandClick(player, offHandItem, event);
            }

        }

        if ((event.getAction().equals(Action.LEFT_CLICK_BLOCK) || event.getAction().equals(Action.LEFT_CLICK_AIR)) && isCustomItem(heldItem)){
            CustomItem customItem = ItemManager.items.get(getItemId(heldItem));
            customItem.handleLeftClick(player, heldItem, event);
        }

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerConsume(PlayerItemConsumeEvent event){
        Player player = event.getPlayer();
        ItemStack consumedItem = event.getItem();

        if (isCustomItem(consumedItem)){
            CustomItem customItem = ItemManager.items.get(getItemId(consumedItem));
            customItem.handleConsumption(player, consumedItem, event);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDamage(EntityDamageByEntityEvent event){
        if (event.getDamager().getType().equals(EntityType.PLAYER)){
            Player player = (Player) event.getDamager();
            ItemStack heldItem = player.getInventory().getItemInMainHand();
            if (isCustomItem(heldItem)){
                CustomItem customItem = ItemManager.items.get(getItemId(heldItem));
                customItem.handleAttackEntity(player, heldItem, event);
            }

        } if (event.getEntity().getType().equals(EntityType.PLAYER)){
            Player player = (Player) event.getEntity();
            for (ItemStack i : player.getInventory().getArmorContents()){
                if (isCustomItem(i)){
                    CustomItem customItem = ItemManager.items.get(getItemId(i));
                    customItem.handleDamagedByEntity(player, i, event);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onOtherDamage(EntityDamageEvent event){
        if (event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            for (ItemStack i : player.getInventory().getArmorContents()){
                if (isCustomItem(i)){
                    CustomItem customItem = ItemManager.items.get(getItemId(i));
                    customItem.handleDamaged(player, i, event);
                }
            }
        }
    }

    private boolean isCustomItem(ItemStack itemStack){
        if (itemStack != null && itemStack.hasItemMeta()){
            return (itemStack.getItemMeta().getPersistentDataContainer().has(ItemManager.customItemKey, PersistentDataType.STRING));
        }
        return false;
    }

    private String getItemId(ItemStack itemStack){
        return itemStack.getItemMeta().getPersistentDataContainer().get(ItemManager.customItemKey, PersistentDataType.STRING);
    }


}
