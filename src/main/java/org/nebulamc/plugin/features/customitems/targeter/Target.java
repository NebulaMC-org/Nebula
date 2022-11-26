package org.nebulamc.plugin.features.customitems.targeter;

import org.bukkit.Location;

public interface Target {

    Location getLocation();

    enum Type {
        ENTITY,
        ITEM,
        LOCATION,
        PROJECTILE
    }

}
