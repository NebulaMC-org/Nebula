package org.nebulamc.plugin.features.customitems.items;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
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
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.nebulamc.plugin.features.customitems.actions.*;
import org.nebulamc.plugin.features.customitems.entity.NoEntity;
import org.nebulamc.plugin.features.customitems.source.EntitySource;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Flamethrower extends CustomItem {
    @Override
    public String getName() {
        return "&eFlamethrower";
    }

    @Override
    public Material getMaterial() {
        return Material.GOLDEN_HORSE_ARMOR;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&7Fuel: &6Blaze Powder", "\n", "&eRight-click to spew flames!");
    }

    @Override
    public Map<Enchantment, Integer> getEnchants() {
        return null;
    }

    @Override
    public List<ItemFlag> getFlags() {
        return null;
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
        return null;
    }

    @Override
    public boolean isUnbreakable() {
        return false;
    }

    @Override
    public List<EquipmentSlot> activeSlots() {
        return null;
    }

    @Override
    public void handleLeftClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {

    }

    ProjectileAction projAction = new ProjectileAction(70, 0,
            new ListAction(new SetOnFireAction(100), new DamageAction(3)),
            new NullAction(),
            new NullAction(),
            new ParticleAction(Particle.FLAME, 10, 0.2, 0.2, 0.2, 0.1),
            new NoEntity(),
            1.5, 5, 0, true);

    @Override
    public void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        Inventory inv = player.getInventory();
        ItemStack fuel = new ItemStack(Material.BLAZE_POWDER, 1);
        HashMap<Integer, ItemStack> result = inv.removeItem(fuel);
        if (result.isEmpty()){

            player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1.5f, 0.8f);
            player.playSound(player.getLocation(), Sound.BLOCK_FIRE_AMBIENT, 0.5f, 1f);
            projAction.execute(new EntityTarget(player), new EntitySource(player));

        } else {

            player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 1.5f, 0f);

        }

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

    @Override
    public void handleShootBow(Player player, ItemStack itemStack, EntityShootBowEvent event) {

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
    public int getTimerDelay() {
        return 0;
    }
}
