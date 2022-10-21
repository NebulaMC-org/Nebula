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
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.nebulamc.plugin.features.customitems.CustomItem;
import org.nebulamc.plugin.features.playerdata.PlayerData;
import org.nebulamc.plugin.features.playerdata.PlayerManager;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Jetpack extends CustomItem {
    @Override
    public String getName() {
        return "&dJetpack";
    }

    @Override
    public Material getMaterial() {
        return Material.NETHERITE_CHESTPLATE;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("\n", "&eSneak to thrust upward!");
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
        return true;
    }

    @Override
    public List<EquipmentSlot> activeSlots() {
        return null;
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

    @Override
    public void doTimerAction(Player player) {
        PlayerData data = PlayerManager.getPlayerData(player);
        int fuel = data.getJetpackFuel();
        if (player.isOnGround()){
            data.setJetpackFuel(data.getJetpackMaxFuel());
        }
        if (player.isSneaking() && data.getJetpackFuel() > 0){
            Vector unitVector = new Vector(player.getLocation().getDirection().getX(), 0, player.getLocation().getDirection().getZ());
            unitVector = unitVector.normalize();

            player.setVelocity(player.getVelocity().add(new Vector(0, 0.25, 0)).add(unitVector.multiply(0.3)));

            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 5 , 0, false, false,false));
            player.getWorld().spawnParticle(Particle.FLAME, player.getLocation().add(0, -0.2, 0), 2, 0, 0, 0, 0.2);
            player.getWorld().spawnParticle(Particle.SMOKE_LARGE, player.getLocation().add(0, 0, 0), 1, 0, 0, 0, 0);

            if (fuel >= 60){
                player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_SHOOT, 1f, 1.2f);
            } else if (fuel >= 20){
                player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_SHOOT, 1f, 0.8f);
            } else {
                player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_SHOOT, 1f, 0.4f);
            }

            data.setJetpackFuel(fuel-3);
        }
    }

    @Override
    public boolean hasTimerAction() {
        return true;
    }

    @Override
    public void handleShootBow(Player player, ItemStack itemStack, EntityShootBowEvent event) {

    }

    @Override
    public int getTimerPeriod() {
        return 2;
    }

    @Override
    public int getTimerDelay() {
        return 0;
    }
}
