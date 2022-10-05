package org.nebulamc.plugin.features.customitems;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.nebulamc.plugin.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class CustomItem {

    public long cooldownTime;

    public abstract String getName();

    public abstract Material getMaterial();

    public abstract List<String> getLore();

    public abstract Map<Enchantment, Integer> getEnchants();

    public abstract List<ItemFlag> getFlags();

    public abstract Map<Attribute, AttributeModifier> getAttributes();

    public abstract int getModelData();

    public abstract Color getColor();

    public abstract boolean isUnbreakable();

    public abstract void handleLeftClick(Player player, ItemStack itemStack, PlayerInteractEvent event);

    public abstract void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event);

    public abstract void handleOffHandClick(Player player, ItemStack itemStack, PlayerInteractEvent event);

    public abstract void handleConsumption(Player player, ItemStack itemStack, PlayerItemConsumeEvent event);

    public abstract void handleDamagedByEntity(Player player, ItemStack itemStack, EntityDamageByEntityEvent event);

    public abstract void handleAttackEntity(Player player, ItemStack itemStack, EntityDamageByEntityEvent event);

    public abstract void handleDamaged(Player player, ItemStack itemStack, EntityDamageEvent event);

    public abstract void handlePlaceBlock(Player player, ItemStack itemStack, BlockPlaceEvent event);

    public void setCooldown(double seconds){
        cooldownTime = System.currentTimeMillis() + (long) (1000 * seconds);
    }

    public boolean cooldownOver(){
        if (System.currentTimeMillis() >= cooldownTime){
            return true;
        }
        return false;
    }

    public String getId(){
        return getClass().getSimpleName();
    }

    public ItemStack getItem(){
        ItemStack itemStack = new ItemStack(getMaterial(), 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();

        //apply armor color
        if (getColor() != null){
            LeatherArmorMeta leatherMeta = (LeatherArmorMeta) itemMeta;
            leatherMeta.setColor(getColor());
            itemStack.setItemMeta(leatherMeta);
        }

        //set name
        itemMeta.setDisplayName(Utils.colorize(getName()));

        //set lore
        if (getLore() != null){
            List<String> lore = new ArrayList<>();
            getLore().forEach(l-> lore.add(Utils.colorize(l)));
            itemMeta.setLore(lore);
        }

        //set enchants
        if (getEnchants() != null){
            for (Enchantment e : getEnchants().keySet()){
                itemMeta.addEnchant(e, getEnchants().get(e), true);
            }
        }


        //set attributes/attribute modifiers
        if (getAttributes() != null){
            for (Attribute a : getAttributes().keySet()){
                itemMeta.addAttributeModifier(a, getAttributes().get(a));
            }
        }


        //set item flags
        if (getFlags() != null){
            getFlags().forEach(f-> itemMeta.addItemFlags(f));
        }

        if (isUnbreakable()){
            itemMeta.setUnbreakable(true);
        }

        itemMeta.setCustomModelData(getModelData());

        container.set(ItemManager.customItemKey, PersistentDataType.STRING, getId());
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
