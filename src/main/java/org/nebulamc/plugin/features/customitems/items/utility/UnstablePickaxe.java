package org.nebulamc.plugin.features.customitems.items.utility;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.nebulamc.plugin.features.customitems.actions.*;
import org.nebulamc.plugin.features.customitems.area.CubicArea;
import org.nebulamc.plugin.features.customitems.items.CustomItem;
import org.nebulamc.plugin.features.customitems.source.EntitySource;
import org.nebulamc.plugin.features.customitems.targeter.LocationTarget;
import org.nebulamc.plugin.utils.Utils;

import java.util.Arrays;
import java.util.List;

public class UnstablePickaxe extends CustomItem {
    @Override
    public String getName() {
        return "&eUnstable Pickaxe";
    }

    @Override
    public Material getMaterial() {
        return Material.DIAMOND_PICKAXE;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList(
                "&7Ammo: Gunpowder",
                "\n",
                "&eMined blocks explode.");
    }

    @Override
    public int getModelData() {
        return 0;
    }

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public boolean isUnbreakable() {
        return false;
    }

    ListAction mineEvent = new ListAction(
            new ParticleAction(Particle.EXPLOSION_LARGE, 1, 0, 0, 0,0),
            new SoundAction(Sound.ENTITY_GENERIC_EXPLODE, 3f, 1.5f),
            new BlocksInAreaAction(
                    new CubicArea(new Vector(0, 0, 0), 1, false),
                    new BreakBlockAction(8, false, 1)
            )
    );

    @Override
    public void handleBreakBlock(Player player, ItemStack itemStack, BlockBreakEvent event){
        if (Utils.removeItem(player, new ItemStack(Material.GUNPOWDER, 1))){
            mineEvent.execute(new LocationTarget(event.getBlock().getLocation()), new EntitySource(player));
        }
    }
}
