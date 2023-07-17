package org.nebulamc.plugin.features.customitems.items.utility;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.nebulamc.plugin.features.customitems.items.CustomItem;
import org.nebulamc.plugin.features.playerdata.ManaBar;
import org.nebulamc.plugin.features.playerdata.PlayerData;
import org.nebulamc.plugin.features.playerdata.PlayerManager;

import java.util.Arrays;
import java.util.List;

public class SlimeOrb extends CustomItem {
    @Override
    public String getName() {
        return "&fSlime Orb";
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
    public void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        PlayerData playerData = PlayerManager.playerData.get(player.getUniqueId());
        ManaBar manaBar = playerData.getManaBar();
        String name = getClass().getSimpleName();

        if (manaBar.getMana() >= 25 && player.isOnGround() && playerData.cooldownOver(name)) {
            Location location = player.getLocation();
            Vector direction = location.getDirection();

            player.playSound(location, Sound.ENTITY_SLIME_JUMP, 2.5f, 1f);
            player.getWorld().spawnParticle(Particle.ITEM_CRACK, location, 10, 0.4, 0.1, 0.4, 0, new ItemStack(Material.SLIME_BALL));
            player.setVelocity(direction.setY(0.8).multiply(2.5));

            manaBar.subtractMana(25);
            playerData.setItemCooldown(name, 0.5);
        }
    }

    @Override
    public void handleOffHandClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        handleRightClick(player, itemStack, event);
    }
}
