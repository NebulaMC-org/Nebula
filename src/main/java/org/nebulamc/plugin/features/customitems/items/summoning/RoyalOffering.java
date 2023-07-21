package org.nebulamc.plugin.features.customitems.items.summoning;

import org.bukkit.*;
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
import org.nebulamc.plugin.features.customitems.area.CylindricArea;
import org.nebulamc.plugin.features.customitems.entity.ItemEntity;
import org.nebulamc.plugin.features.customitems.items.CustomItem;
import org.nebulamc.plugin.features.customitems.source.EntitySource;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;
import org.nebulamc.plugin.features.customitems.targeter.LocationTarget;
import org.nebulamc.plugin.utils.Utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoyalOffering extends CustomItem {
    @Override
    public String getName() {
        return "&6Royal Offering";
    }

    @Override
    public Material getMaterial() {
        return Material.RAW_COPPER;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&7Summons: &eThe Mountain King", "&7Recommended Players: &e1-3");
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
        return 1;
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

    ProjectileAction sigilProj = new ProjectileAction(
            0, 0, new NullAction(), new NullAction(), new NullAction(), new ParticleAction(Particle.FIREWORKS_SPARK, 1, 0, 0, 0, 0.3),
            new ItemEntity(new ItemStack(Material.RAW_COPPER),false, 1), 0, 140, 0, false, false
    );

    @Override
    public void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        if (Utils.removeItem(player, getItem())){
            Location loc = player.getLocation();
            sigilProj.execute(new EntityTarget(player), new EntitySource(player));
            new TimedAction(
                    0, 60, 2,
                    new SoundAction(Sound.BLOCK_BEACON_AMBIENT, 4, 2)
            ).execute(new LocationTarget(loc), new EntitySource(player));
            new DelayedAction(
                    60,
                    new ListAction(
                            new ChangeTargetAction(
                                    new TimedAction(0, 5, 16,
                                            new ListAction(
                                                    new BlocksInAreaAction(
                                                            new CylindricArea(new Vector(0, -15, 0), 90, 0, false),
                                                            new ParticleAction(Particle.FIREWORKS_SPARK, 1, 0, 0, 0, 0)
                                                    ),
                                                    new SoundAction(Sound.ENTITY_GUARDIAN_ATTACK, 2, 1)
                                            )

                                    ),
                                    new LocationTarget(loc)
                            ),
                            new DelayedAction(
                                    80,
                                    new ChangeTargetAction(
                                            new ListAction(
                                                    new RunCommandAction("mm mobs spawn -s MountainKing 1 " + player.getWorld().getName() + "," + loc.getX() + "," + loc.getY() + "," + loc.getZ() + ",1,1"),
                                                    new SoundAction(Sound.ENTITY_WITHER_SPAWN, 5, 1),
                                                    new SoundAction(Sound.ENTITY_GENERIC_EXPLODE, 5, 1),
                                                    new ParticleAction(Particle.FIREWORKS_SPARK, 30, 0, 0, 0, 0.5),
                                                    new ParticleAction(Particle.EXPLOSION_HUGE, 1, 0, 0, 0, 0),
                                                    new LightningAction(false)

                                            ), new LocationTarget(loc)
                                    )
                            )

                    )
            ).execute(new EntityTarget(player), new EntitySource(player));
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
    public void handleEquip(Player player, ItemStack itemStack) {

    }

    @Override
    public void handleUnequip(Player player, ItemStack itemStack) {

    }
}
