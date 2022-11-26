
//refacted from SupremeItem source code
//author: jummes

package org.nebulamc.plugin.features.customitems.source;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

public interface Source {

    LivingEntity getCaster();

    Location getLocation();

    enum Type {
        ENTITY,
        LOCATION
    }
}
