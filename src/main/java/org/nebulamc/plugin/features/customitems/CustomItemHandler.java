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
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.persistence.PersistentDataType;
import org.nebulamc.plugin.features.customitems.items.CustomItem;
import org.nebulamc.plugin.features.playerdata.PlayerData;
import org.nebulamc.plugin.features.playerdata.PlayerManager;

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
                if (inActiveSlot(player, i)){
                    ItemManager.items.get(getItemId(i)).handleAttackEntity(player, i, event);
                }
            }

        } if (event.getEntity().getType().equals(EntityType.PLAYER)){
            Player player = (Player) event.getEntity();
            for (ItemStack i : getCustomItems(player)){
                if (inActiveSlot(player, i)){
                    ItemManager.items.get(getItemId(i)).handleDamagedByEntity(player, i, event);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onOtherDamage(EntityDamageEvent event){
        if (event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            for (ItemStack i : getCustomItems(player)){
                if (inActiveSlot(player, i)){
                    ItemManager.items.get(getItemId(i)).handleDamaged(player, i, event);
                }
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
        changeSlotTimers(player, oldItem, newItem, event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onArmorChange(PlayerArmorChangeEvent event){
        changeSlotTimers(event.getPlayer(), event.getOldItem(), event.getNewItem(), event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event){
        equipAllItems(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event){
        unequipAllItems(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onItemDrop(PlayerDropItemEvent event){
        ItemStack item = event.getItemDrop().getItemStack();
        unequipItem(event.getPlayer(), item);

        if (isCustomItem(item)){
            event.getItemDrop().setInvulnerable(true);
        }

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onItemBreak(PlayerItemBreakEvent event){
        unequipItem(event.getPlayer(), event.getBrokenItem());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDeath(PlayerDeathEvent event){
        unequipAllItems(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onRespawn(PlayerRespawnEvent event){
        equipAllItems(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBowShoot(EntityShootBowEvent event){
        if (event.getEntity() instanceof Player && isCustomItem(event.getBow())){
            CustomItem customItem = ItemManager.items.get(getItemId(event.getBow()));
            customItem.handleShootBow((Player) event.getEntity(), event.getBow(), event);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onItemDamage(PlayerItemDamageEvent event){

        ItemStack item = event.getItem();
        CustomItem customItem = ItemManager.items.get(getItemId(item));

        customItem.handleItemDamaged(event.getPlayer(), item, event);
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
        if (itemStack == null || itemStack.getItemMeta() == null || !isCustomItem(itemStack)){
            return "null";
        }
        return itemStack.getItemMeta().getPersistentDataContainer().get(ItemManager.customItemKey, PersistentDataType.STRING);
    }

    public static void changeSlotTimers(Player player, ItemStack oldItem, ItemStack newItem, PlayerItemHeldEvent event){
        if (oldItem != null){
            unequipItem(player, oldItem);
        }
        if (newItem != null){
            equipItem(player, newItem);
        }
    }

    public static void changeSlotTimers(Player player, ItemStack oldItem, ItemStack newItem, PlayerArmorChangeEvent event){
        if (oldItem != null){
            unequipItem(player, oldItem);
        }
        if (newItem != null){
            equipItem(player, newItem);
        }
    }

    public static void equipAllItems(Player player){
        for (ItemStack i : getCustomItems(player)){
            equipItem(player, i);
        }
    }

    public static void unequipAllItems(Player player){
        for (ItemStack i : getCustomItems(player)){
            unequipItem(player, i);
        }
    }

    public static boolean inActiveSlot(Player player, ItemStack itemStack){
        CustomItem item = ItemManager.items.get(getItemId(itemStack));
        if (item.activeSlots() == null){
            return false;
        }
        for (EquipmentSlot slot : item.activeSlots()){
            if (getItemId(player.getInventory().getItem(slot)).equals(item.getId())){
                return true;
            }
        }
        return false;
    }


    public static void unequipItem(Player player, ItemStack item){
        if (isCustomItem(item)){
            CustomItem customItem = ItemManager.items.get(getItemId(item));
            if (customItem.activeSlots() != null && !inActiveSlot(player, item)){
                PlayerData data = PlayerManager.getPlayerData(player);
                if (customItem.hasTimerAction()){
                    ItemManager.removePlayerFromTimer(player, customItem);
                }
                if (data.activeItems.contains(customItem.getId())){
                    customItem.handleUnequip(player, item);
                    data.removeActiveItem(customItem.getId());
                }
            }
        }
    }

    public static void equipItem(Player player, ItemStack item){
        if (isCustomItem(item)){
            CustomItem customItem = ItemManager.items.get(getItemId(item));
            if (customItem.activeSlots() != null && inActiveSlot(player, item)) {
                PlayerData data = PlayerManager.getPlayerData(player);
                if (customItem.hasTimerAction()) {
                    ItemManager.addPlayerToTimer(player, customItem);
                }
                if (!data.activeItems.contains(customItem.getId())) {
                    customItem.handleEquip(player, item);
                    data.addActiveItem(customItem.getId());
                }
            }
        }
    }
}
