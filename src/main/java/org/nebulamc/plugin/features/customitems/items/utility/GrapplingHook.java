package org.nebulamc.plugin.features.customitems.items.utility;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.nebulamc.plugin.features.customitems.actions.*;
import org.nebulamc.plugin.features.customitems.entity.NoEntity;
import org.nebulamc.plugin.features.customitems.items.CustomItem;
import org.nebulamc.plugin.features.customitems.source.EntitySource;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;
import org.nebulamc.plugin.features.playerdata.PlayerData;
import org.nebulamc.plugin.features.playerdata.PlayerManager;
import org.nebulamc.plugin.utils.Utils;

import java.util.Arrays;
import java.util.List;

public class GrapplingHook extends CustomItem {
    @Override
    public String getName() {
        return "&eGrappling Hook";
    }

    @Override
    public Material getMaterial() {
        return Material.IRON_HORSE_ARMOR;
    }

    @Override
    public int getModelData() {
        return 1;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&7Ammo: &8Chain", "\n", "&eHook a block to pull yourself to it.", "&eHook an enemy to pull them towards you!");
    }

    ProjectileAction projAction = new ProjectileAction(60, 0,
            new PullAction(false, 0.5),
            new SoundAction(Sound.BLOCK_DISPENSER_LAUNCH, 1f, 1),
            new PullAction(true, 0.35),
            new ListAction(
                    new ParticleAction(Particle.CRIT, 1, 0, 0, 0 ,0),
                    new SoundAction(Sound.BLOCK_CHAIN_PLACE, 1.5f, 1)
            ), new NoEntity(), 1.25, 20, 0,false, false
    );

    @Override
    public void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        PlayerData playerData = PlayerManager.getPlayerData(player);
        String name = getClass().getSimpleName();
        if (playerData.cooldownOver(name) && Utils.removeItem(player, new ItemStack(Material.CHAIN, 1))) {
            playerData.setItemCooldown(name, 2);
            projAction.execute(new EntityTarget(player), new EntitySource(player));
        }
    }

    @Override
    public void handleOffHandClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        PlayerData playerData = PlayerManager.getPlayerData(player);
        String name = getClass().getSimpleName() + "OffHand";
        if (playerData.cooldownOver(name) && Utils.removeItem(player, new ItemStack(Material.CHAIN, 1))) {
            playerData.setItemCooldown(name, 2);
            projAction.execute(new EntityTarget(player), new EntitySource(player));
        }
    }
}
