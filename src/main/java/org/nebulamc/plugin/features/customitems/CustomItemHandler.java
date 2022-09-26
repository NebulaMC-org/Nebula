package org.nebulamc.plugin.features.customitems;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class CustomItemHandler implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack heldItem = player.getInventory().getItemInMainHand();

        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR) && isCustomItem(heldItem)){
            CustomItem customItem = ItemManager.items.get(getItemId(heldItem));
            customItem.handleRightClick(player, heldItem, event);
        }

        if (event.getAction().equals(Action.LEFT_CLICK_BLOCK) || event.getAction().equals(Action.LEFT_CLICK_AIR) && isCustomItem(heldItem)){
            CustomItem customItem = ItemManager.items.get(getItemId(heldItem));
            customItem.handleLeftClick(player, heldItem, event);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerConsume(PlayerItemConsumeEvent event){

    }

    private boolean isCustomItem(ItemStack itemStack){
        if (itemStack.hasItemMeta()){
            return (itemStack.getItemMeta().getPersistentDataContainer().has(ItemManager.customItemKey, PersistentDataType.STRING));
        }
        return false;
    }

    private String getItemId(ItemStack itemStack){
        return itemStack.getItemMeta().getPersistentDataContainer().get(ItemManager.customItemKey, PersistentDataType.STRING);
    }


}
