package org.nebulamc.plugin.features.customitems.items;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
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
import org.nebulamc.plugin.features.customitems.source.EntitySource;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;
import org.nebulamc.plugin.features.customitems.targeter.LocationTarget;
import org.nebulamc.plugin.features.playerdata.ManaBar;
import org.nebulamc.plugin.features.playerdata.PlayerManager;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SeismicHammer extends CustomItem {
    @Override
    public String getName() {
        return "&eSeismic Hammer";
    }

    @Override
    public Material getMaterial() {
        return Material.NETHERITE_AXE;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&7Mana Use: &b40", "\n", "&eRight-click to slam enemies into the air!", "&eDeal more damage to airborne enemies.");
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

    Action slamAction = new EntitiesInAreaAction(4, new ListAction(new SetVelocityAction(new Vector(0, 1, 0))), true);

    @Override
    public void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        ManaBar manaBar = PlayerManager.getPlayerData(player).getManaBar();
        if (manaBar.getMana() >= 40){
            slamAction.execute(new LocationTarget(player.getLocation()), new EntitySource(player));
            manaBar.setMana(manaBar.getMana() - 40);
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

    Action pushAction = new PushAction(0.75, 0.1);

    @Override
    public void handleAttackEntity(Player player, ItemStack itemStack, EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity && !entity.isInWater() && !entity.isOnGround() && event.getDamage() >= 10){
            event.setDamage(event.getDamage() + 7);
            pushAction.execute(new EntityTarget((LivingEntity) entity), new EntitySource(player));
        }
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
