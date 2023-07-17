package org.nebulamc.plugin.features.customitems.items.consumables;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.nebulamc.plugin.features.customitems.actions.ExplosionAction;
import org.nebulamc.plugin.features.customitems.items.CustomItem;
import org.nebulamc.plugin.features.customitems.source.LocationSource;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpicyApple extends CustomItem {
    @Override
    public String getName() {
        return "&fSpicy Apple";
    }

    @Override
    public Material getMaterial() {
        return Material.APPLE;
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

    ExplosionAction explode = new ExplosionAction(
            40, 1.5f, 0
    );

    @Override
    public void handleConsumption(Player player, ItemStack itemStack, PlayerItemConsumeEvent event) {
        explode.execute(new EntityTarget(player), new LocationSource(player.getLocation(), player));
    }
}
