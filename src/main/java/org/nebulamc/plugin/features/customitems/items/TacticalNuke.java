package org.nebulamc.plugin.features.customitems.items;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
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
import org.bukkit.util.Vector;
import org.nebulamc.plugin.features.customitems.actions.*;
import org.nebulamc.plugin.features.customitems.area.SphericArea;
import org.nebulamc.plugin.features.customitems.entity.ItemEntity;
import org.nebulamc.plugin.features.customitems.source.EntitySource;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TacticalNuke extends CustomItem{
    @Override
    public String getName() {
        return "&cTactical Nuke";
    }

    @Override
    public Material getMaterial() {
        return Material.MAGMA_CREAM;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("\n", "&4&lHANDLE WITH CAUTION");
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

    ListAction nukeAction = new ListAction(
            new BlocksInAreaAction(new SphericArea(new Vector(0, 0, 0), 50, false),
                    new ListAction(
                            new ParticleAction(Particle.ASH, 1, 0, 0, 0, 0),
                            new BreakBlockAction(200, false, 0)
                   )
                ),
            new EntitiesInAreaAction(new SphericArea(new Vector(0, 0, 0), 50, false), new DamageAction(100))
    );

    ProjectileAction nukeLaunch = new ProjectileAction(
            50, 0,
            nukeAction,
            new NullAction(),
            nukeAction,
            new ParticleAction(Particle.SMOKE_LARGE, 1, 0, 0, 0, 0.2),
            new ItemEntity(new ItemStack(Material.TNT), false),
            2, 200, 0, false, false
    );

    @Override
    public void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        if (player.isOp()){
            nukeLaunch.execute(new EntityTarget(player), new EntitySource(player));
        } else {
            player.sendMessage("You are not authorized to use this.");
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
    public void handleEquip(Player player, ItemStack itemStack) {

    }

    @Override
    public void handleUnequip(Player player, ItemStack itemStack) {

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
}
