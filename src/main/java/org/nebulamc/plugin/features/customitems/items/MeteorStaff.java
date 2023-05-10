package org.nebulamc.plugin.features.customitems.items;

import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.nebulamc.plugin.features.customitems.actions.*;
import org.nebulamc.plugin.features.customitems.area.CylindricArea;
import org.nebulamc.plugin.features.customitems.entity.GenericEntity;
import org.nebulamc.plugin.features.customitems.source.LocationSource;
import org.nebulamc.plugin.features.customitems.targeter.LocationTarget;
import org.nebulamc.plugin.features.playerdata.PlayerData;
import org.nebulamc.plugin.features.playerdata.PlayerManager;
import org.nebulamc.plugin.utils.Utils;

import java.util.Arrays;
import java.util.List;

public class MeteorStaff extends CustomItem{
    @Override
    public String getName() {
        return "&dMeteor Staff";
    }

    @Override
    public Material getMaterial() {
        return Material.BLAZE_ROD;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&7Mana Use: &b65", "\n", "&eRight-click the ground to summon a meteor!");
    }

    @Override
    public int getModelData() {
        return 3;
    }

    ExplosionAction meteorExplosion = new ExplosionAction(30, 1, 60);

    @Override
    public void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        PlayerData data = PlayerManager.getPlayerData(player);
        if (data.getManaBar().getMana() >= 65) {
            data.getManaBar().subtractMana(65);
            Location endLocation =
                    Utils.rayCast(player, 50, 1, false,
                            new NullAction(),
                            new NullAction(),
                            new BlocksInAreaAction(
                                    new CylindricArea(new Vector(0, 1.5, 0), 1, 4, true),
                                    new ParticleAction(Particle.REDSTONE, 1, 0, 0, 0, 0, new Particle.DustOptions(Color.RED, 1.5f))
                            )
                    );

            if (endLocation != null) {
                new ProjectileAction(
                        60, 50,
                        meteorExplosion,
                        new NullAction(),
                        meteorExplosion,
                        new ParticleAction(Particle.SMOKE_LARGE, 3, 0, 0, 0, 0.1),
                        new GenericEntity(EntityType.FIREBALL), 2, 200, 360, false, false
                ).execute(new LocationTarget(endLocation), new LocationSource(endLocation.add(0, 30, 0), player));
                player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1.5f, 0f);
            }
        }
    }

    @Override
    public void handleOffHandClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        handleRightClick(player, itemStack, event);
    }
}
