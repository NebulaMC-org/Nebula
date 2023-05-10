
//refacted from SupremeItem source code
//author: jummes

package org.nebulamc.plugin.features.customitems.targeter;

import lombok.Getter;
import org.bukkit.Location;
import org.nebulamc.plugin.features.customitems.projectile.AbstractProjectile;

@Getter
public class ProjectileTarget implements Target {

    private final AbstractProjectile projectile;

    public ProjectileTarget(AbstractProjectile projectile) {
        this.projectile = projectile;
    }

    @Override
    public Location getLocation() {
        return projectile.getLocation();
    }

    public AbstractProjectile getProjectile(){
        return projectile;
    }
}
