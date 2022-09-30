package org.nebulamc.plugin.features.customitems.items;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.nebulamc.plugin.features.customitems.CustomItem;
import org.nebulamc.plugin.features.mana.ManaBar;
import org.nebulamc.plugin.features.mana.ManaManager;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ImpetusHammer extends CustomItem {
    @Override
    public String getName() {
        return "&dImpetus Hammer";
    }

    @Override
    public Material getMaterial() {
        return Material.NETHERITE_AXE;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&7Mana Use: &b40",
                "\n",
                "&eDeal more damage at higher velocities.",
                "&eRight-click to launch forward!");
    }

    @Override
    public Map<Enchantment, Integer> getEnchants() {
        return null;
    }

    @Override
    public List<ItemFlag> getFlags() {
        return null;
    }

    @Override
    public Map<Attribute, AttributeModifier> getAttributes() {
        return null;
    }

    @Override
    public int getModelData() {
        return 0;
    }

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public void handleLeftClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {

    }

    @Override
    public void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        ManaBar manaBar = ManaManager.manaBars.get(player.getUniqueId());
        if (manaBar.getMana() >= 40){
            Location location = player.getLocation();
            Vector direction = location.getDirection();

            player.setVelocity(direction.multiply(4));
            manaBar.setMana(manaBar.getMaxMana() - 40);
        }
    }

    @Override
    public void handleConsumption(Player player, ItemStack itemStack, PlayerItemConsumeEvent event) {

    }

    @Override
    public void handleDamaged(Player player, ItemStack itemStack, EntityDamageByEntityEvent event) {

    }

    @Override
    public void handleAttack(Player player, ItemStack itemStack, EntityDamageByEntityEvent event) {
        player.sendMessage("Angle: " + player.getVelocity().angle(event.getEntity().getVelocity()));
    }
}
