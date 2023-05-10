
//refacted from SupremeItem source code
//author: jummes

package org.nebulamc.plugin.features.customitems.targeter;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;


@Getter
public class EntityTarget implements Target {

    private final LivingEntity target;

    public EntityTarget(LivingEntity target){
        this.target = target;
    }

    @Override
    public Location getLocation() {
        return target.getLocation();
    }
}
