package org.nebulamc.plugin.features.customitems.items;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.nebulamc.plugin.features.customitems.CustomItem;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SacrificialBlade extends CustomItem {
    @Override
    public String getName() {
        return "&dSacrificial Blade";
    }

    @Override
    public Material getMaterial() {
        return Material.NETHERITE_SWORD;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("\n",
                "&eDeal higher damage at lower health.",
                "&eRight-click to sacrifice.");
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
    public void handleLeftClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {

    }

    @Override
    public void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        if (player.getHealth() > 4){
            player.damage(0.1);
            player.setHealth(player.getHealth() - 4);
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT_SWEET_BERRY_BUSH, 1.0f, 0f);
            setCooldown(0.4);
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
        double health = player.getHealth();
        Location entityLocation = event.getEntity().getLocation().add(0, 1.2, 0);

        if (health <= 2){
            event.setDamage(event.getDamage() + 10);
            player.getWorld().spawnParticle(Particle.ITEM_CRACK, entityLocation, 10, 0.1, 0.2, 0.1, 0, new ItemStack(Material.REDSTONE_BLOCK));
        } else if (health <= 4){
            event.setDamage(event.getDamage() + 5);
            player.getWorld().spawnParticle(Particle.ITEM_CRACK, entityLocation, 5, 0.1, 0.2, 0.1, 0, new ItemStack(Material.REDSTONE_BLOCK));
        } else if (health <= 6){
            event.setDamage(event.getDamage() + 2);
            player.getWorld().spawnParticle(Particle.ITEM_CRACK, entityLocation, 2, 0.1, 0.2, 0.1, 0, new ItemStack(Material.REDSTONE_BLOCK));
        }
    }

    @Override
    public void handleDamaged(Player player, ItemStack itemStack, EntityDamageEvent event) {
        player.sendMessage("Cause: " + event.getCause() + ", Damage: " + event.getDamage());
    }
}
