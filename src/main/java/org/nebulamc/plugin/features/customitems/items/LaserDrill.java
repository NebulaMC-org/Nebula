package org.nebulamc.plugin.features.customitems.items;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.nebulamc.plugin.features.customitems.actions.BreakBlockAction;
import org.nebulamc.plugin.features.customitems.actions.NullAction;
import org.nebulamc.plugin.features.customitems.actions.ParticleAction;
import org.nebulamc.plugin.features.playerdata.PlayerData;
import org.nebulamc.plugin.features.playerdata.PlayerManager;
import org.nebulamc.plugin.utils.Utils;

import java.util.Arrays;
import java.util.List;

public class LaserDrill extends CustomItem{
    @Override
    public String getName() {
        return "&dLaser Drill";
    }

    @Override
    public Material getMaterial() {
        return Material.BLAZE_ROD;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&eRight-click to shoot out a mining laser!");
    }

    @Override
    public int getModelData() {
        return 2;
    }

    ParticleAction tickAction = new ParticleAction(Particle.REDSTONE, 1, 0, 0, 0, 0, new Particle.DustOptions(Color.RED, 1));
    BreakBlockAction endAction = new BreakBlockAction(60, true, 0);

    @Override
    public void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        PlayerData playerData = PlayerManager.getPlayerData(player);
        String name = getClass().getSimpleName();
        if (playerData.cooldownOver(name)){
            playerData.setItemCooldown(name, 0.55);
            Utils.straightRayCast(player, 12, 1, 0.5, false,
                    tickAction,
                    new NullAction(),
                    new NullAction()
            );
            Utils.rayCast(player, 12, 1, false,
                    new NullAction(),
                    new NullAction(),
                    endAction
            );
        }

    }

    @Override
    public void handleOffHandClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        handleRightClick(player, itemStack, event);
    }
}
