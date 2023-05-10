
//refactored from SupremeItem source code
//author: jummes

package org.nebulamc.plugin.features.customitems.entity;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import java.util.Map;

public class NoEntity extends Entity {

    public NoEntity() {

    }

    public NoEntity(Map<String, Object> map) {
        super();
    }

    public static NoEntity deserialize(Map<String, Object> map) {
        return new NoEntity();
    }

    @Override
    public org.bukkit.entity.Entity spawnEntity(Location l) {
        return null;
    }

    @Override
    public Entity clone() {
        return new NoEntity();
    }

    @Override
    public EntityType getType() {
        return null;
    }
}
