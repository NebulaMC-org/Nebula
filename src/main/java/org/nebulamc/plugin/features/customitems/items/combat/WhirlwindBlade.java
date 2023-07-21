package org.nebulamc.plugin.features.customitems.items.combat;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.nebulamc.plugin.features.customitems.actions.BlocksInAreaAction;
import org.nebulamc.plugin.features.customitems.actions.DamageAction;
import org.nebulamc.plugin.features.customitems.actions.EntitiesInAreaAction;
import org.nebulamc.plugin.features.customitems.actions.ParticleAction;
import org.nebulamc.plugin.features.customitems.area.CylindricArea;
import org.nebulamc.plugin.features.customitems.area.SphericArea;
import org.nebulamc.plugin.features.customitems.items.CustomItem;
import org.nebulamc.plugin.features.customitems.source.EntitySource;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;
import org.nebulamc.plugin.features.playerdata.ManaBar;
import org.nebulamc.plugin.features.playerdata.PlayerData;
import org.nebulamc.plugin.features.playerdata.PlayerManager;

import java.util.Arrays;
import java.util.List;

public class WhirlwindBlade extends CustomItem {
    @Override
    public String getName() {
        return  "&fWhirlwind Blade";
    }

    @Override
    public Material getMaterial() {
        return Material.DIAMOND_SWORD;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&7Mana Use: &b30", "\n", "&eRight-click to slash in a circle", "&earound yourself!");
    }

    EntitiesInAreaAction slashAction = new EntitiesInAreaAction(
            new SphericArea(new Vector(0, 0, 0), 5, false),
            new DamageAction(7)
    );
    BlocksInAreaAction particleAction = new BlocksInAreaAction(
            new CylindricArea(new Vector(0, 1, 0), 1, 4, true),
            new ParticleAction(Particle.SWEEP_ATTACK, 1, 0 ,0, 0, 0)
    );

    @Override
    public void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        PlayerData playerData = PlayerManager.getPlayerData(player);
        ManaBar mana = playerData.getManaBar();
        String name = getClass().getSimpleName();
        if (mana.getMana() >= 30 && playerData.cooldownOver(name)){
            mana.subtractMana(30);
            playerData.setItemCooldown(name, 1);
            slashAction.execute(new EntityTarget(player), new EntitySource(player));
            particleAction.execute(new EntityTarget(player), new EntitySource(player));
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 2.5f, 0.8f);
        }
    }

    @Override
    public void handleOffHandClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        handleRightClick(player, itemStack, event);
    }
}
