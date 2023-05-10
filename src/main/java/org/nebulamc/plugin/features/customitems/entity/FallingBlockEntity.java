
//refactored from SupremeItem source code
//author: jummes

package org.nebulamc.plugin.features.customitems.entity;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;

public class FallingBlockEntity extends Entity {

    private Material material;


    public FallingBlockEntity() {
        this.material = Material.RED_SAND;
    }

    public FallingBlockEntity(Material material) {
        this.material = material;
    }

    @Override
    public org.bukkit.entity.Entity spawnEntity(Location l) {
        FallingBlock entity = l.getWorld().spawnFallingBlock(l, Bukkit.createBlockData(material));
        entity.setDropItem(false);
        return entity;
    }

    @Override
    public Entity clone() {
        return new FallingBlockEntity(material);
    }

    @Override
    public EntityType getType() {
        return EntityType.FALLING_BLOCK;
    }
}
