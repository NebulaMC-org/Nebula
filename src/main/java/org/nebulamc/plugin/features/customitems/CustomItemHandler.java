package org.nebulamc.plugin.features.customitems;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

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
            for (ItemStack i : getCustomItems(player)){
                ItemManager.items.get(getItemId(i)).handleAttackEntity(player, i, event);
            }

        } if (event.getEntity().getType().equals(EntityType.PLAYER)){
            Player player = (Player) event.getEntity();
            for (ItemStack i : getCustomItems(player)){
                ItemManager.items.get(getItemId(i)).handleDamagedByEntity(player, i, event);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onOtherDamage(EntityDamageEvent event){
        if (event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            for (ItemStack i : getCustomItems(player)){
                ItemManager.items.get(getItemId(i)).handleDamaged(player, i, event);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlaceBlock(BlockPlaceEvent event){
        Player player = event.getPlayer();
        ItemStack heldItem = player.getInventory().getItemInMainHand();
        for (ItemStack i : getCustomItems(player)){
            ItemManager.items.get(getItemId(i)).handlePlaceBlock(player, heldItem, event);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onItemChange(PlayerItemHeldEvent event){
        Player player = event.getPlayer();
        ItemStack oldItem = player.getInventory().getItem(event.getPreviousSlot());
        ItemStack newItem = player.getInventory().getItem(event.getNewSlot());

        changeSlotTimers(player, oldItem, newItem);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onArmorChange(PlayerArmorChangeEvent event){
        Player player = event.getPlayer();

        changeSlotTimers(player, event.getOldItem(), event.getNewItem());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event){
        addAllInventoryTimers(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        ItemManager.removeFromAllTimers(player);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onItemDrop(PlayerDropItemEvent event){
        ItemStack item = event.getItemDrop().getItemStack();
        if (isCustomItem(item)){
            CustomItem customItem = ItemManager.items.get(getItemId(item));
            ItemManager.removePlayerFromTimer(event.getPlayer(), customItem);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onItemBreak(PlayerItemBreakEvent event){
        ItemStack item = event.getBrokenItem();
        if (isCustomItem(item)){
            CustomItem customItem = ItemManager.items.get(getItemId(item));
            ItemManager.removePlayerFromTimer(event.getPlayer(), customItem);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDeath(PlayerDeathEvent event){
        ItemManager.removeFromAllTimers(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onRespawn(PlayerRespawnEvent event){
        addAllInventoryTimers(event.getPlayer());
    }

    public static boolean isCustomItem(ItemStack itemStack){
        if (itemStack != null && itemStack.hasItemMeta()){
            return (itemStack.getItemMeta().getPersistentDataContainer().has(ItemManager.customItemKey, PersistentDataType.STRING));
        }
        return false;
    }

    public static List<ItemStack> getCustomItems(Player player){
        PlayerInventory inventory = player.getInventory();
        ItemStack hand = inventory.getItemInMainHand();
        ItemStack offHand = inventory.getItemInOffHand();

        List<ItemStack> customItems = new ArrayList<>();
        for (ItemStack i : inventory.getArmorContents()){
            if (isCustomItem(i)){
                customItems.add(i);
            }
        }
        if (isCustomItem(hand)){
            customItems.add(hand);
        }
        if (isCustomItem(offHand)) {
            customItems.add(offHand);
        }
        return customItems;
    }

    public static String getItemId(ItemStack itemStack){
        return itemStack.getItemMeta().getPersistentDataContainer().get(ItemManager.customItemKey, PersistentDataType.STRING);
    }

    public static void changeSlotTimers(Player player, ItemStack oldItem, ItemStack newItem){
        if (isCustomItem(oldItem)){
            CustomItem item = ItemManager.items.get(getItemId(oldItem));
            ItemManager.removePlayerFromTimer(player, item);
        }
        if (isCustomItem(newItem)){
            CustomItem item = ItemManager.items.get(getItemId(newItem));
            ItemManager.addPlayerToTimer(player, item);
        }
    }

    public static void addAllInventoryTimers(Player player){
        for (ItemStack i : getCustomItems(player)){
            CustomItem item = ItemManager.items.get(getItemId(i));
            ItemManager.addPlayerToTimer(player, item);
        }
    }
}
