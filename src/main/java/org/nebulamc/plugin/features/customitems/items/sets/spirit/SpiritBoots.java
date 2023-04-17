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

public class SpiritBoots extends CustomItem {
    @Override
    public String getName() {
        return "&fSpirit Boots";
    }

    @Override
    public Material getMaterial() {
        return Material.LEATHER_BOOTS;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&a+0.5 &7Mana Regen");
    }

    @Override
    public List<ItemFlag> getFlags() {
        return Arrays.asList(ItemFlag.HIDE_DYE);
    }

    @Override
    public Map<Attribute, AttributeModifier> getAttributes() {
        Map<Attribute, AttributeModifier> attributes = new HashMap<>();
        attributes.put(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "generic.armor", 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
        attributes.put(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armorToughness", 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
        return attributes;
    }

    @Override
    public Color getColor() {
        return Color.fromRGB(87, 151, 233);
    }

    @Override
    public List<EquipmentSlot> activeSlots() {
        return Arrays.asList(EquipmentSlot.FEET);
    }

    @Override
    public void handleItemDamaged(Player player, ItemStack itemStack, PlayerItemDamageEvent event) {
        Utils.handleCustomDurability(player, itemStack, event, 8, getClass().getSimpleName());
    }

    @Override
    public void handleEquip(Player player, ItemStack itemStack) {
        ManaBar manaBar = PlayerManager.getPlayerData(player).getManaBar();
        manaBar.setRegenRate(manaBar.getRegenRate() + 0.5f);
    }

    @Override
    public void handleUnequip(Player player, ItemStack itemStack) {
        ManaBar manaBar = PlayerManager.getPlayerData(player).getManaBar();
        manaBar.setRegenRate(manaBar.getRegenRate() - 0.5f);
    }
}
