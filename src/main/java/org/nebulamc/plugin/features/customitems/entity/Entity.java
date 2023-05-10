
//refactored from SupremeItem source code
//author: jummes

package org.nebulamc.plugin.features.customitems.entity;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import java.util.Map;

public abstract class Entity {

    public Entity() {
    }

    public Entity(Map<String, Object> map) {
    }

    public abstract org.bukkit.entity.Entity spawnEntity(Location l);

    public abstract Entity clone();

    public abstract EntityType getType();

}
