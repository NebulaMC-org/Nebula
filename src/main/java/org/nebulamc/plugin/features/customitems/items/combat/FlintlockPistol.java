package org.nebulamc.plugin.features.customitems.items.combat;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.nebulamc.plugin.features.customitems.actions.*;
import org.nebulamc.plugin.features.customitems.entity.ItemEntity;
import org.nebulamc.plugin.features.customitems.items.CustomItem;
import org.nebulamc.plugin.features.customitems.source.EntitySource;
import org.nebulamc.plugin.features.customitems.source.LocationSource;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;
import org.nebulamc.plugin.features.playerdata.PlayerData;
import org.nebulamc.plugin.features.playerdata.PlayerManager;
import org.nebulamc.plugin.utils.Utils;

import java.util.Arrays;
import java.util.List;

public class FlintlockPistol extends CustomItem {
    @Override
    public String getName() {
        return "&eFlintlock Pistol";
    }

    @Override
    public Material getMaterial() {
        return Material.IRON_HORSE_ARMOR;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&7Ammo: &8Flint", "\n", "&eRight-click to shoot a powerful flint bullet!");
    }

    ProjectileAction flintShoot = new ProjectileAction(
            60, 0,
            new ListAction(
                    new DamageAction(12),
                    new PushAction(2, 0.5, false)
            ),
            new NullAction(),
            new NullAction(),
            new ParticleAction(Particle.SMOKE_NORMAL, 1, 0, 0, 0, 0.1),
            new ItemEntity(new ItemStack(Material.FLINT), false),
            0.75, 100, 1, false, false
    );

    @Override
    public void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        PlayerData playerData = PlayerManager.getPlayerData(player);
        String name = getClass().getSimpleName();
        if (playerData.cooldownOver(name)) {
            if (Utils.removeItem(player, new ItemStack(Material.FLINT, 1))) {
                player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 2f);
                playerData.setItemCooldown(name, 2);
                flintShoot.execute(new EntityTarget(player), new EntitySource(player));
                new PullAction(1, false).execute(
                        new EntityTarget(player),
                        new LocationSource(player.getEyeLocation().add(player.getEyeLocation().getDirection().multiply(-2)), player)
                );
            } else {
                player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 1.5f, 0f);
            }
        }
    }

    @Override
    public void handleOffHandClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        handleRightClick(player, itemStack, event);
    }
}
