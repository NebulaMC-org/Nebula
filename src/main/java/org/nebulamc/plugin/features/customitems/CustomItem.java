package org.nebulamc.plugin.features.customitems;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.nebulamc.plugin.utils.Common;

import java.util.ArrayList;
import java.util.List;

public abstract class CustomItem {

    public abstract String getName();

    public abstract Material getMaterial();

    public abstract List<String> getLore();

    public abstract List<Enchantment> getEnchants();

    public abstract void handleLeftClick(Player player, ItemStack itemStack, PlayerInteractEvent event);

    public abstract void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event);

    public abstract void handleConsumption(Player player, ItemStack itemStack, PlayerItemConsumeEvent event);

    public String getId(){
        return getClass().getSimpleName();
    }

    public ItemStack getItem(){
        ItemStack itemStack = new ItemStack(getMaterial(), 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();


        itemMeta.setDisplayName(Common.colorize(getName()));

        if (getLore() != null){
            List<String> lore = new ArrayList<>();
            getLore().forEach(l-> lore.add(Common.colorize(l)));
            itemMeta.setLore(lore);
        }

        container.set(ItemManager.customItemKey, PersistentDataType.STRING, getId());
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
