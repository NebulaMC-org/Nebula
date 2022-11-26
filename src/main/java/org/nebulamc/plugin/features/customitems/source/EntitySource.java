
//refacted from SupremeItem source code
//author: jummes

package org.nebulamc.plugin.features.customitems.source;

import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class EntitySource implements Source {
    private final @NonNull LivingEntity source;

    public EntitySource(LivingEntity source) {
        this.source = source;
    }

    public EntitySource(Player source) {
        this.source = (LivingEntity) source;
    }

    @Override
    public LivingEntity getCaster() {
        return source;
    }

    @Override
    public Location getLocation() {
        return source.getLocation();
    }
}
