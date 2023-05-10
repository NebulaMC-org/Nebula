package org.nebulamc.plugin.features.customitems.items;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class NoFallBoots extends CustomItem {
    @Override
    public String getName() {
        return "&eNo-Fall Boots";
    }

    @Override
    public Material getMaterial() {
        return Material.LEATHER_BOOTS;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("\n",
                "&eReduce all fall damage by 70%!");
    }

    @Override
    public boolean isUnbreakable() {
        return true;
    }

    @Override
    public List<ItemFlag> getFlags() {
        return Arrays.asList(ItemFlag.HIDE_DYE);
    }

    @Override
    public Color getColor() {
        return Color.fromRGB(128, 211, 246);
    }

    @Override
    public List<EquipmentSlot> activeSlots() {
        return Arrays.asList(EquipmentSlot.FEET);
    }

    @Override
    public void handleDamaged(Player player, ItemStack itemStack, EntityDamageEvent event) {
        if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL)){
            event.setDamage(event.getDamage() * 0.3f);
        }
    }
}
