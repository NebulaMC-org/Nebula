package org.nebulamc.plugin.features.customitems.items.materials;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.nebulamc.plugin.features.customitems.items.CustomItem;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolarEssence extends CustomItem {

    @Override
    public String getName() {
        return "&eSolar Essence";
    }

    @Override
    public Material getMaterial() {
        return Material.YELLOW_DYE;
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
    public int getModelData() {
        return 1;
    }
}
