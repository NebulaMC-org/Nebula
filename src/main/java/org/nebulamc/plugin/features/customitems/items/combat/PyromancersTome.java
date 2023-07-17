package org.nebulamc.plugin.features.customitems.items.combat;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.nebulamc.plugin.features.customitems.actions.*;
import org.nebulamc.plugin.features.customitems.entity.NoEntity;
import org.nebulamc.plugin.features.customitems.items.CustomItem;
import org.nebulamc.plugin.features.customitems.source.EntitySource;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;
import org.nebulamc.plugin.features.playerdata.PlayerManager;

import java.util.Arrays;
import java.util.List;

public class PyromancersTome extends CustomItem {
    @Override
    public String getName() {
        return "&fPyromancer's Tome";
    }

    @Override
    public Material getMaterial() {
        return Material.BOOK;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&7Mana Use: &b50", "\n", "&eRight-click to summon a deadly flame!");
    }

    @Override
    public int getModelData() {
        return 1;
    }

    ProjectileAction firespell = new ProjectileAction(
            50, 0,
            new ListAction(
                    new ExplosionAction(6, 1, 100),
                    new ParticleAction(Particle.FLAME, 5, 0, 0, 0, 0.1)
            ),
            new NullAction(),
            new NullAction(),
            new NullAction(),
            new NoEntity(), 1, 100, 1, false, false
    );

    @Override
    public void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        if (PlayerManager.takeMana(player, 50)){
            firespell.execute(new EntityTarget(player), new EntitySource(player));
        }
    }

    @Override
    public void handleOffHandClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        handleRightClick(player, itemStack, event);
    }
}
