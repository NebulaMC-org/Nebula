package org.nebulamc.plugin.features.customitems.items.consumables;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.nebulamc.plugin.features.customitems.items.CustomItem;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ambrosia extends CustomItem {
    @Override
    public String getName() {
        return "&eAmbrosia";
    }

    @Override
    public Material getMaterial() {
        return Material.HONEY_BOTTLE;
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
    }
}
