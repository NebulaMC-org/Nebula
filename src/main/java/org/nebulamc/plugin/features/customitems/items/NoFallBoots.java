package org.nebulamc.plugin.features.customitems.items;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class NoFallBoots extends CustomItem {
    @Override
    public String getName() {
        return "&eNo-Fall Boots";
    }

    @Override
    public Material getMaterial() {
        return Material.LEATHER_BOOTS;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("\n",
                "&eNever worry about fall damage again!");
    }

    @Override
    public Map<Enchantment, Integer> getEnchants() {
        return null;
    }

    @Override
    public boolean isUnbreakable() {
        return true;
    }

    @Override
    public List<ItemFlag> getFlags() {
        return Arrays.asList(ItemFlag.HIDE_DYE);
    }

    @Override
    public Map<Attribute, AttributeModifier> getAttributes() {
        return null;
    }

    @Override
    public int getModelData() {
        return 0;
    }

    @Override
    public Color getColor() {
        return Color.fromRGB(128, 211, 246);
    }

    @Override
    public void handleLeftClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {

    }

    @Override
    public void doTimerAction(Player player) {

    }

    @Override
    public boolean hasTimerAction() {
        return false;
    }

    @Override
    public void handleShootBow(Player player, ItemStack itemStack, EntityShootBowEvent event) {

    }

    @Override
    public List<EquipmentSlot> activeSlots() {
        return Arrays.asList(EquipmentSlot.FEET);
    }

    @Override
    public int getTimerPeriod() {
        return 0;
    }

    @Override
    public int getTimerDelay() {
        return 0;
    }

    @Override
    public void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {

    }

    @Override
    public void handleConsumption(Player player, ItemStack itemStack, PlayerItemConsumeEvent event) {

    }

    @Override
    public void handlePlaceBlock(Player player, ItemStack itemStack, BlockPlaceEvent event) {

    }

    @Override
    public void handleOffHandClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {

    }

    @Override
    public void handleDamagedByEntity(Player player, ItemStack itemStack, EntityDamageByEntityEvent event) {

    }

    @Override
    public void handleAttackEntity(Player player, ItemStack itemStack, EntityDamageByEntityEvent event) {

    }

    @Override
    public void handleDamaged(Player player, ItemStack itemStack, EntityDamageEvent event) {
        if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL)){
            event.setCancelled(true);
        }
    }
}
