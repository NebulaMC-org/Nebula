package org.nebulamc.plugin.features.customitems.items;

import org.bukkit.*;
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

public class SlimeOrb extends CustomItem {
    @Override
    public String getName() {
        return "&eSlime Orb";
    }

    @Override
    public Material getMaterial() {
        return Material.SLIME_BALL;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&7Mana Use: &b25",
                "\n",
                "&eRight-click to launch yourself",
                "&eoff the ground!");
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
        if (manaBar.getMana() >= 25 && player.isOnGround()) {
            Location location = player.getLocation();
            Vector direction = location.getDirection();

            player.playSound(location, Sound.ENTITY_SLIME_JUMP, 2.5f, 1f);
            player.getWorld().spawnParticle(Particle.ITEM_CRACK, location, 10, 0.4, 0.1, 0.4, 0, new ItemStack(Material.SLIME_BALL));
            player.setVelocity(direction.setY(0.5).multiply(2.5));

            manaBar.setMana(manaBar.getMana() - 25);
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

    }
}
