package org.nebulamc.plugin.features.customitems.items.utility;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.nebulamc.plugin.features.customitems.items.CustomItem;

import java.util.Arrays;
import java.util.List;

public class MechanicalLegs extends CustomItem {
    @Override
    public String getName() {
        return "&eMechanic Legs";
    }

    @Override
    public Material getMaterial() {
        return Material.LEATHER_LEGGINGS;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("\n",
                "&eGreatly increase jumping ability!");
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
        return Color.fromRGB(168, 193, 192);
    }

    @Override
    public List<EquipmentSlot> activeSlots() {
        return Arrays.asList(EquipmentSlot.LEGS);
    }

    @Override
    public void doTimerAction(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 30, 2, false, false, false));
    }

    @Override
    public boolean hasTimerAction() {
        return true;
    }

    @Override
    public int getTimerPeriod() {
        return 20;
    }

    @Override
    public void handleJump(Player player, ItemStack itemStack, PlayerJumpEvent event) {

    }
}
