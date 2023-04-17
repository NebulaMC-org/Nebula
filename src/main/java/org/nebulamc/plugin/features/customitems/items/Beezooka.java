package org.nebulamc.plugin.features.customitems.items;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.nebulamc.plugin.features.customitems.actions.ExplosionAction;
import org.nebulamc.plugin.features.customitems.actions.NullAction;
import org.nebulamc.plugin.features.customitems.actions.ParticleAction;
import org.nebulamc.plugin.features.customitems.actions.ProjectileAction;
import org.nebulamc.plugin.features.customitems.entity.GenericEntity;
import org.nebulamc.plugin.features.customitems.source.EntitySource;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;
import org.nebulamc.plugin.features.playerdata.PlayerData;
import org.nebulamc.plugin.features.playerdata.PlayerManager;

import java.util.Arrays;
import java.util.List;

public class Beezooka extends CustomItem{
    @Override
    public String getName() {
        return "&eBeezooka";
    }

    @Override
    public Material getMaterial() {
        return Material.GOLDEN_HORSE_ARMOR;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&7Mana Use: &b30", "\n", "&eRight-click to shoot an explosive bee!");
    }

    @Override
    public int getModelData() {
        return 2;
    }

    ExplosionAction explosionAction = new ExplosionAction(10, 1, 0);
    ParticleAction particleAction = new ParticleAction(Particle.SMOKE_NORMAL, 1, 0, 0 ,0, 0.2);
    ProjectileAction beeProj = new ProjectileAction(55, 0, explosionAction,
            new NullAction(),
            explosionAction,
            particleAction,
            new GenericEntity(EntityType.BEE),
            1, 200, 2, false, false);

    @Override
    public void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        PlayerData playerData = PlayerManager.getPlayerData(player);
        String name = getClass().getSimpleName();
        if (playerData.cooldownOver(name) && PlayerManager.takeMana(player, 30)) {
            playerData.setItemCooldown(name, 0.2);
            player.playSound(player.getLocation(), Sound.BLOCK_BEEHIVE_EXIT, 1.5f, 0f);
            player.playSound(player.getLocation(), Sound.BLOCK_PISTON_EXTEND, 2.5f, 1f);
            beeProj.execute(new EntityTarget(player), new EntitySource(player));
        }
    }

    @Override
    public void handleOffHandClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        handleRightClick(player, itemStack, event);
    }
}
