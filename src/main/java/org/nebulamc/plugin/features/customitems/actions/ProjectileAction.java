package org.nebulamc.plugin.features.customitems.actions;

import org.bukkit.Location;
import org.nebulamc.plugin.features.customitems.entity.Entity;
import org.nebulamc.plugin.features.customitems.projectile.Projectile;
import org.nebulamc.plugin.features.customitems.source.EntitySource;
import org.nebulamc.plugin.features.customitems.source.Source;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;
import org.nebulamc.plugin.features.customitems.targeter.LocationTarget;
import org.nebulamc.plugin.features.customitems.targeter.Target;

public class ProjectileAction extends AbstractProjectileAction {

    private int projectileSpread;

    public ProjectileAction(double initialSpeed, double gravity, Action onEntityHitAction,
                            Action onStartAction, Action onBlockHitAction, Action onProjectileTickAction, Entity entity,
                            double hitBoxSize, int maxDistance, int projectileSpread) {
        super(initialSpeed, gravity, onStartAction, onEntityHitAction, onBlockHitAction, onProjectileTickAction, entity,
                hitBoxSize, maxDistance);
        this.projectileSpread = projectileSpread;
    }

    @Override
    public void execute(Target target, Source source) {
        Location sourceLocation = source.getLocation();

        if (sourceLocation == null) {
            return;
        }

        if (!source.getLocation().equals(target.getLocation())) {
            if (target instanceof LocationTarget) {
                sourceLocation.setDirection(target.getLocation().clone().toVector().subtract(sourceLocation.toVector()).normalize());
            } else if (target instanceof EntityTarget) {
                sourceLocation.setDirection(((EntityTarget) target).getTarget().getEyeLocation().clone().toVector().subtract(sourceLocation.
                        toVector()).normalize());
            }
        }

        if (source instanceof EntitySource) {
            EntitySource entitySource = (EntitySource) source;
            sourceLocation.add(0, entitySource.getCaster().getEyeHeight(), 0);
        }

        new Projectile(target, source, sourceLocation, gravity, initialSpeed,
                onStartAction, onEntityHitAction, onBlockHitAction, onProjectileTickAction,
                this.entity, this.hitBoxSize, maxLife,
                projectileSpread).run();
    }
}
