package org.nebulamc.plugin.features.customitems.items;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
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
import org.nebulamc.plugin.utils.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ImpetusHammer extends CustomItem {
    @Override
    public void handlePlaceBlock(Player player, ItemStack itemStack, BlockPlaceEvent event) {

    }

    @Override
    public String getName() {
        return "&dImpetus Hammer";
    }

    @Override
    public boolean isUnbreakable() {
        return false;
    }

    @Override
    public Material getMaterial() {
        return Material.NETHERITE_AXE;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("\n",
                "&eDeal more damage at higher velocities!");
    }

    @Override
    public void handleOffHandClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {

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
        return 2;
    }

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public void handleLeftClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {

    }

    @Override
    public void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {

    }

    @Override
    public void handleConsumption(Player player, ItemStack itemStack, PlayerItemConsumeEvent event) {

    }

    @Override
    public void handleDamagedByEntity(Player player, ItemStack itemStack, EntityDamageByEntityEvent event) {

    }

    @Override
    public void handleShootBow(Player player, ItemStack itemStack, EntityShootBowEvent event) {

    }

    @Override
    public List<EquipmentSlot> activeSlots() {
        return null;
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

    @Override
    public void handleAttackEntity(Player player, ItemStack itemStack, EntityDamageByEntityEvent event) {
        if (event.getDamage() >= 9){
            Vector direction = player.getLocation().getDirection();
            Vector velocity = player.getVelocity();
            Entity entity = event.getEntity();

            double xDamage = velocity.getX() * direction.getX() * 10;
            double yDamage = velocity.getY() * direction.getY() * 10;
            double zDamage = velocity.getZ() * direction.getZ() * 10;
            int addedDamage = (int) Math.abs(xDamage + yDamage + zDamage);

            if (addedDamage >= 2 && entity instanceof LivingEntity){
                Location location = player.getLocation();
                event.setDamage(event.getDamage() + addedDamage);
                entity.setVelocity(velocity.multiply(
                        Utils.absoluteVector(direction)).
                        multiply(5).setY(entity.getVelocity().
                        getY() + 0.2));

                if (addedDamage >= 20) {
                    player.playSound(location, Sound.BLOCK_ANVIL_PLACE, 1.5f, 0f);
                    player.playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 1.5f, 2f);
                } else if (addedDamage >= 15){
                    player.playSound(location, Sound.BLOCK_ANVIL_PLACE, 1.5f, 0f);
                } else if (addedDamage >= 10){
                    player.playSound(location, Sound.BLOCK_ANVIL_PLACE, 1.5f, 1f);
                } else {
                    player.playSound(location, Sound.BLOCK_ANVIL_PLACE, 1.5f, 2f);
                }

            }
        }
    }

    @Override
    public void handleDamaged(Player player, ItemStack itemStack, EntityDamageEvent event) {

    }
}
