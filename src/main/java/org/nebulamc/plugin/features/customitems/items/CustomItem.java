package org.nebulamc.plugin.features.customitems.items;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.nebulamc.plugin.features.customitems.ItemManager;
import org.nebulamc.plugin.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class CustomItem {

    public abstract String getName();

    public abstract Material getMaterial();

    public List<String> getLore(){ return null; }

    public Map<Enchantment, Integer> getEnchants(){ return null; }

    public List<ItemFlag> getFlags(){ return null; }

    public Map<Attribute, AttributeModifier> getAttributes(){ return null; }

    public int getModelData(){ return 0; }

    public Color getColor(){ return null; }

    public boolean isUnbreakable(){ return false; }

    public List<EquipmentSlot> activeSlots(){ return null; }

    public void handleLeftClick(Player player, ItemStack itemStack, PlayerInteractEvent event){}

    public void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event){}

    public void handleOffHandClick(Player player, ItemStack itemStack, PlayerInteractEvent event){}

    public void handleConsumption(Player player, ItemStack itemStack, PlayerItemConsumeEvent event){}

    public void handleDamagedByEntity(Player player, ItemStack itemStack, EntityDamageByEntityEvent event){}

    public void handleAttackEntity(Player player, ItemStack itemStack, EntityDamageByEntityEvent event){}

    public void handleDamaged(Player player, ItemStack itemStack, EntityDamageEvent event){}

    public void handlePlaceBlock(Player player, ItemStack itemStack, BlockPlaceEvent event){}

    public void handleBreakBlock(Player player, ItemStack itemStack, BlockBreakEvent event){}

    public void handleJump(Player player, ItemStack itemStack, PlayerJumpEvent event){}

    public void handleShootBow(Player player, ItemStack itemStack, EntityShootBowEvent event){}

    public void handleItemDamaged(Player player, ItemStack itemStack, PlayerItemDamageEvent event){}

    public void handleEquip(Player player, ItemStack itemStack){}

    public void handleUnequip(Player player, ItemStack itemStack){}

    public void doTimerAction(Player player){}

    public boolean hasTimerAction(){ return false; }

    public int getTimerPeriod(){ return 0; }

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
