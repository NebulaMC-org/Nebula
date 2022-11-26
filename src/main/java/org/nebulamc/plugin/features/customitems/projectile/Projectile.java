
//refactored from SupremeItem source code
//author: jummes

package org.nebulamc.plugin.features.customitems.projectile;

import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.nebulamc.plugin.features.customitems.actions.Action;
import org.nebulamc.plugin.features.customitems.entity.Entity;
import org.nebulamc.plugin.features.customitems.source.Source;
import org.nebulamc.plugin.features.customitems.targeter.Target;

public class Projectile extends AbstractProjectile {

    protected int projectileSpread;

    public Projectile(Target target, Source source, Location location, double gravity, double initialSpeed,
                      Action onStartAction, Action onEntityHitAction, Action onBlockHitAction,
                      Action onProjectileTickAction, Entity entity, double hitBoxSize, int maxDistance,
                      int projectileSpread) {
        super(target, source, location, gravity, initialSpeed, onStartAction, onEntityHitAction, onBlockHitAction,
                onProjectileTickAction, entity, hitBoxSize, maxDistance);
        this.projectileSpread = projectileSpread;
    }

    @Override
    protected Vector getProjectileDirection() {
        return getSpreadedInitialDirection(location.clone(), projectileSpread);
    }

    @Override
    protected void updateLocationAndDirection() {
        if (location.getDirection().length() > 0)
            location.add(location.clone().getDirection().multiply(speed).multiply(.05));
    }

}
