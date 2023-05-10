package org.nebulamc.plugin.features.customitems.items.sets.vertus;

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
import org.nebulamc.plugin.features.customitems.items.CustomItem;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VertusCrystal extends CustomItem {
    @Override
    public void handleShootBow(Player player, ItemStack itemStack, EntityShootBowEvent event) {

    }

    @Override
    public List<EquipmentSlot> activeSlots() {
        return null;
    }

    @Override
    public void handleEquip(Player player, ItemStack itemStack) {

    }

    @Override
    public void handleUnequip(Player player, ItemStack itemStack) {

    }

    @Override
    public String getName() {
        return "&fVertus Crystal";
    }

    @Override
    public Material getMaterial() {
        return Material.RED_DYE;
    }

    @Override
    public void doTimerAction(Player player) {

    }

    @Override
    public boolean hasTimerAction() {
        return false;
    }

    @Override
    public int getTimerPeriod() {
        return 0;
    }

    @Override
    public List<String> getLore() {
        return null;
    }

    @Override
    public Map<Enchantment, Integer> getEnchants() {
        Map<Enchantment, Integer> enchants = new HashMap<>();
        enchants.put(Enchantment.ARROW_INFINITE, 1);
        return enchants;
    }

    @Override
    public List<ItemFlag> getFlags() {
        return Arrays.asList(ItemFlag.HIDE_ENCHANTS);
    }

    @Override
    public Map<Attribute, AttributeModifier> getAttributes() {
        return null;
    }

    @Override
    public int getModelData() {
        return 2;
    }

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public boolean isUnbreakable() {
        return false;
    }

    @Override
    public void handleLeftClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {

    }

    @Override
    public void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {

    }

    @Override
    public void handleOffHandClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {

    }

    @Override
    public void handleConsumption(Player player, ItemStack itemStack, PlayerItemConsumeEvent event) {

    }

    @Override
    public void handleDamagedByEntity(Player player, ItemStack itemStack, EntityDamageByEntityEvent event) {

    }

    @Override
    public void handleAttackEntity(Player player, ItemStack itemStack, EntityDamageByEntityEvent event) {

    }

    @Override
    public void handleDamaged(Player player, ItemStack itemStack, EntityDamageEvent event) {

    }

    @Override
    public void handlePlaceBlock(Player player, ItemStack itemStack, BlockPlaceEvent event) {

    }
}
