package org.nebulamc.plugin.features.customitems.items.materials;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.nebulamc.plugin.features.customitems.items.CustomItem;

public class ToxicVial extends CustomItem {

    @Override
    public String getName() {
        return "&fToxic Vial";
    }

    @Override
    public Material getMaterial() {
        return Material.HONEY_BOTTLE;
    }

    @Override
    public int getModelData() {
        return 1;
    }

    @Override
    public void handleConsumption(Player player, ItemStack itemStack, PlayerItemConsumeEvent event) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20*120, 3));
    }
}
