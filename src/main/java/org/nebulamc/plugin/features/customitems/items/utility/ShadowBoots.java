package org.nebulamc.plugin.features.customitems.items.utility;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.nebulamc.plugin.features.customitems.items.CustomItem;

import java.util.Arrays;
import java.util.List;

public class ShadowBoots extends CustomItem {

    @Override
    public String getName() {
        return "&fShadow Boots";
    }

    @Override
    public Material getMaterial() {
        return Material.LEATHER_BOOTS;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("\n", "&eSneak to become invisible!");
    }

    @Override
    public Color getColor() {
        return Color.BLACK;
    }

    @Override
    public boolean isUnbreakable() {
        return true;
    }

    @Override
    public List<EquipmentSlot> activeSlots() {
        return Arrays.asList(EquipmentSlot.FEET);
    }

    @Override
    public void doTimerAction(Player player) {
        if (player.isSneaking()){
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 10 , 0, false, false,true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10 , 5, false, false,true));
            player.getWorld().spawnParticle(Particle.SMOKE_NORMAL, player.getLocation().add(0, 1, 0), 10, 0.2, 0.5, 0.2, 0);
            player.getWorld().spawnParticle(Particle.SMOKE_LARGE, player.getLocation().add(0, 1, 0), 3, 0.2, 0.5, 0.2, 0);
        }
    }

    @Override
    public boolean hasTimerAction() {
        return true;
    }

    @Override
    public int getTimerPeriod() {
        return 5;
    }

}
