package org.nebulamc.plugin.features.customitems.items.sets.spirit;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.nebulamc.plugin.features.customitems.items.CustomItem;
import org.nebulamc.plugin.features.playerdata.ManaBar;
import org.nebulamc.plugin.features.playerdata.PlayerManager;
import org.nebulamc.plugin.utils.Utils;

import java.util.*;

public class SpiritHelmet extends CustomItem {
    @Override
    public String getName() {
        return "&fSpirit Helmet";
    }

    @Override
    public Material getMaterial() {
        return Material.LEATHER_HELMET;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&a+0.4 &7Mana Regen");
    }

    @Override
    public List<ItemFlag> getFlags() {
        return Arrays.asList(ItemFlag.HIDE_DYE);
    }

    @Override
    public Map<Attribute, AttributeModifier> getAttributes() {
        Map<Attribute, AttributeModifier> attributes = new HashMap<>();
        attributes.put(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "generic.armor", 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));
        attributes.put(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armorToughness", 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));
        return attributes;
    }

    @Override
    public Color getColor() {
        return Color.fromRGB(87, 151, 233);
    }

    @Override
    public List<EquipmentSlot> activeSlots() {
        return Arrays.asList(EquipmentSlot.HEAD);
    }

    @Override
    public void handleEquip(Player player, ItemStack itemStack) {
        ManaBar manaBar = PlayerManager.getPlayerData(player).getManaBar();
        manaBar.setRegenRate(manaBar.getRegenRate() + 0.4f);
    }

    @Override
    public void handleUnequip(Player player, ItemStack itemStack) {
        ManaBar manaBar = PlayerManager.getPlayerData(player).getManaBar();
        manaBar.setRegenRate(manaBar.getRegenRate() - 0.4f);
    }

    @Override
    public void handleItemDamaged(Player player, ItemStack itemStack, PlayerItemDamageEvent event) {
        Utils.handleCustomDurability(player, itemStack, event, 7, getClass().getSimpleName());
    }
}
