package org.nebulamc.plugin.features.customitems.items.consumables;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.nebulamc.plugin.features.customitems.items.CustomItem;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<Enchantment, Integer> getEnchants() {

        Map<Enchantment, Integer> enchants = new HashMap<>();
        enchants.put(Enchantment.ARROW_INFINITE, 1);
        return enchants;
    }

    @Override
    public List<ItemFlag> getFlags() {
        return Arrays.asList(
                ItemFlag.HIDE_ENCHANTS
        );
    }

    @Override
    public int getModelData() {
        return 1;
    }

    @Override
    public void handleConsumption(Player player, ItemStack itemStack, PlayerItemConsumeEvent event) {
        player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        player.setFoodLevel(20);
        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20*240, 5));
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*240, 1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE,  20*240, 0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20*40, 2));
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20*180, 0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20*180, 0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20*180, 1));
        player.playSound(player.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 1f, 1f);
    }
}
