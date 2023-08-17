package org.nebulamc.plugin.features.customitems.items.sets.spirit;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.nebulamc.plugin.features.customitems.items.CustomItem;
import org.nebulamc.plugin.features.playerdata.ManaBar;
import org.nebulamc.plugin.features.playerdata.PlayerManager;

import java.util.*;

public class SpiritChestplate extends CustomItem {
    @Override
    public String getName() {
        return "&fSpirit Chestplate";
    }

    @Override
    public Material getMaterial() {
        return Material.LEATHER_CHESTPLATE;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&a+30 &7Max Mana");
    }

    @Override
    public List<ItemFlag> getFlags() {
        return Arrays.asList(ItemFlag.HIDE_DYE);
    }

    @Override
    public Map<Attribute, AttributeModifier> getAttributes() {
        Map<Attribute, AttributeModifier> attributes = new HashMap<>();
        attributes.put(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "generic.armor", 6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
        attributes.put(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armorToughness", 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
        return attributes;
    }

    @Override
    public Color getColor() {
        return Color.fromRGB(87, 151, 233);
    }

    @Override
    public List<EquipmentSlot> activeSlots() {
        return Arrays.asList(EquipmentSlot.CHEST);
    }

    @Override
    public void handleEquip(Player player, ItemStack itemStack) {
        ManaBar manaBar = PlayerManager.getPlayerData(player).getManaBar();
        manaBar.setMaxMana(manaBar.getMaxMana() + 30);
    }

    @Override
    public void handleUnequip(Player player, ItemStack itemStack) {
        ManaBar manaBar = PlayerManager.getPlayerData(player).getManaBar();
        manaBar.setMaxMana(manaBar.getMaxMana() - 30);
    }

    @Override
    public boolean isUnbreakable() {
        return true;
    }
}
