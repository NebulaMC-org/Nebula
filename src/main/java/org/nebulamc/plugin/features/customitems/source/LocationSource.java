
//refacted from SupremeItem source code
//author: jummes

package org.nebulamc.plugin.features.customitems.source;

import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

public class LocationSource implements Source {
    private final @NonNull Location source;
    private final @NonNull LivingEntity originalCaster;

    public LocationSource(Location location, LivingEntity originalCaster){
        source = location;
        this.originalCaster = originalCaster;
    }

    @Override
    public LivingEntity getCaster() {
        return null;
    }

    @Override
    public Location getLocation() {
        return source;
    }
}
