package org.nebulamc.plugin.features.customitems.items;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.nebulamc.plugin.features.customitems.CustomItem;

import java.util.Arrays;
import java.util.List;

public class GoldenCookie extends CustomItem {
    @Override
    public String getName() {
        return "&dGolden Cookie";
    }

    @Override
    public Material getMaterial() {
        return Material.COOKIE;
    }

    @Override
    public List<String> getLore() {
        return null;
    }

    @Override
    public List<Enchantment> getEnchants() {
        return Arrays.asList(Enchantment.ARROW_INFINITE);
    }

    @Override
    public void handleLeftClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {

    }

    @Override
    public void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {

    }

    @Override
    public void handleConsumption(Player player, ItemStack itemStack, PlayerItemConsumeEvent event) {
        player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        player.setFoodLevel(20);
        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 60*240, 6));
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60*240, 1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 60*240, 0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 60*60, 2));
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 60*180, 0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60*180, 0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 60*180, 1));
    }
}
