package org.nebulamc.plugin.features.customitems.items;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.nebulamc.plugin.features.customitems.actions.*;
import org.nebulamc.plugin.features.customitems.entity.GenericEntity;
import org.nebulamc.plugin.features.customitems.source.EntitySource;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;
import org.nebulamc.plugin.features.playerdata.PlayerManager;

import java.util.Arrays;
import java.util.List;

public class FireballCannon extends CustomItem{
    @Override
    public String getName() {
        return "&fFireball Cannon";
    }

    @Override
    public Material getMaterial() {
        return Material.BLAZE_ROD;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&7Mana Use: &b20", "\n", "&eRight-click to launch a fireball!");
    }

    @Override
    public int getModelData() {
        return 1;
    }

    ProjectileAction fireball = new ProjectileAction(
            50, 0,
            new ListAction(
                    new DamageAction(5),
                    new SetOnFireAction(160)
            ),
            new NullAction(),
            new NullAction(),
            new NullAction(),
            new GenericEntity(EntityType.SMALL_FIREBALL), 1, 100, 1, false, false
    );

    @Override
    public void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        if (PlayerManager.takeMana(player, 20)){
            fireball.execute(new EntityTarget(player), new EntitySource(player));
        }
    }

    @Override
    public void handleOffHandClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        handleRightClick(player, itemStack, event);
    }
}
