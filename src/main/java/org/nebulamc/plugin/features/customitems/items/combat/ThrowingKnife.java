package org.nebulamc.plugin.features.customitems.items.combat;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.nebulamc.plugin.features.customitems.actions.DamageAction;
import org.nebulamc.plugin.features.customitems.actions.NullAction;
import org.nebulamc.plugin.features.customitems.actions.ParticleAction;
import org.nebulamc.plugin.features.customitems.actions.ProjectileAction;
import org.nebulamc.plugin.features.customitems.entity.ItemEntity;
import org.nebulamc.plugin.features.customitems.items.CustomItem;
import org.nebulamc.plugin.features.customitems.source.EntitySource;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;
import org.nebulamc.plugin.features.playerdata.PlayerData;
import org.nebulamc.plugin.features.playerdata.PlayerManager;

import java.util.Arrays;
import java.util.List;

public class ThrowingKnife extends CustomItem {
    @Override
    public String getName() {
        return "&fThrowing Knife";
    }

    @Override
    public Material getMaterial() {
        return Material.DIAMOND_SWORD;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&7Mana Use: &b10", "\n", "&eRight-click to throw a knife", "&ein front of you!");
    }

    ProjectileAction projAction = new ProjectileAction(
            70, 0.2,
            new DamageAction(5),
            new NullAction(),
            new NullAction(),
            new ParticleAction(Particle.CRIT, 1, 0, 0, 0, 0),
            new ItemEntity(new ItemStack(Material.DIAMOND_SWORD), false),
            0.5, 35, 0,false, false
        );

    @Override
    public void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        PlayerData data = PlayerManager.getPlayerData(player);
        String name = getClass().getSimpleName();
        if (data.getManaBar().getMana() >= 10 && data.cooldownOver(name)){
            data.getManaBar().subtractMana(10);
            data.setItemCooldown(name, 0.33);
            projAction.execute(new EntityTarget(player), new EntitySource(player));
        }
    }

    @Override
    public void handleOffHandClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        PlayerData data = PlayerManager.getPlayerData(player);
        String name = getClass().getSimpleName() + "OffHand";
        if (data.getManaBar().getMana() >= 10 && data.cooldownOver(name)){
            data.getManaBar().subtractMana(10);
            data.setItemCooldown(name, 0.33);
            projAction.execute(new EntityTarget(player), new EntitySource(player));
        }
    }
}
