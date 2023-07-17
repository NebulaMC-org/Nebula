package org.nebulamc.plugin.features.customitems.items.combat;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.nebulamc.plugin.features.customitems.actions.*;
import org.nebulamc.plugin.features.customitems.area.SphericArea;
import org.nebulamc.plugin.features.customitems.entity.NoEntity;
import org.nebulamc.plugin.features.customitems.items.CustomItem;
import org.nebulamc.plugin.features.customitems.source.EntitySource;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;
import org.nebulamc.plugin.features.playerdata.PlayerData;
import org.nebulamc.plugin.features.playerdata.PlayerManager;
import org.nebulamc.plugin.utils.Utils;

import java.util.Arrays;
import java.util.List;

public class SonicBlaster extends CustomItem {
    @Override
    public String getName() {
        return "&eSonic Blaster";
    }

    @Override
    public Material getMaterial() {
        return Material.IRON_HORSE_ARMOR;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&7Ammo: &3Echo Shard", "\n", "&eRight-click to create a sonic blast!", "&eGoes through walls.");
    }

    @Override
    public int getModelData() {
        return 2;
    }

    ProjectileAction sonicBlast = new ProjectileAction(
            60, 0,
            new NullAction(),
            new NullAction(),
            new NullAction(),
            new ListAction(
                    new ParticleAction(Particle.SONIC_BOOM, 1, 0, 0, 0, 0),
                    new EntitiesInAreaAction(
                            new SphericArea(new Vector(0, 0, 0), 4, false),
                            new DamageAction(20)
                    )
            ),
            new NoEntity(),
            0, 8, 0, true, false
    );

    @Override
    public void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        PlayerData playerData = PlayerManager.getPlayerData(player);
        String name = getClass().getSimpleName();
        if (playerData.cooldownOver(name)) {
            if (Utils.removeItem(player, new ItemStack(Material.ECHO_SHARD, 1))) {
                player.playSound(player.getLocation(), Sound.ENTITY_WARDEN_SONIC_BOOM, 1.0f, 2f);
                playerData.setItemCooldown(name, 2);
                sonicBlast.execute(new EntityTarget(player), new EntitySource(player));
            } else {
                player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 1.5f, 0f);
            }
        }
    }
}
