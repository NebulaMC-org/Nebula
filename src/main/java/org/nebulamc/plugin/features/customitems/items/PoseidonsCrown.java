package org.nebulamc.plugin.features.customitems.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;

public class PoseidonsCrown extends CustomItem{
    @Override
    public String getName() {
        return "&fPoseidon's Crown";
    }

    @Override
    public Material getMaterial() {
        return Material.DIAMOND_HELMET;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("\n", "&eGrants water breathing, at the cost of durability.");
    }

    @Override
    public List<EquipmentSlot> activeSlots() {
        return Arrays.asList(EquipmentSlot.HEAD);
    }

    @Override
    public void doTimerAction(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 30, 0));
        if (player.isUnderWater()){

            player.getInventory().getItem(EquipmentSlot.HEAD).damage(1, player);
        }
    }

    @Override
    public boolean hasTimerAction() {
        return true;
    }

    @Override
    public int getTimerPeriod() {
        return 20;
    }
}
