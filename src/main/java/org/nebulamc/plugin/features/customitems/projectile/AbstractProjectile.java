
//refactored from SupremeItem source code
//author: jummes

package org.nebulamc.plugin.features.customitems.projectile;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.nebulamc.plugin.Nebula;
import org.nebulamc.plugin.features.customitems.actions.Action;
import org.nebulamc.plugin.features.customitems.source.Source;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;
import org.nebulamc.plugin.features.customitems.targeter.ProjectileTarget;
import org.nebulamc.plugin.features.customitems.targeter.Target;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
public abstract class AbstractProjectile {

    protected AbstractProjectile projectile;
    protected Target target;
    protected Source source;
    protected Location location;
    protected double gravity;
    protected double speed;
    protected Action onStartAction;
    protected Action onEntityHitAction;
    protected Action onBlockHitAction;
    protected Action onProjectileTickAction;
    protected Entity entity;
    protected double hitBoxSize;
    protected int maxLife;
    boolean pierceEntities;
    protected int projectileLife;
    private Location oldLocation;
    private org.nebulamc.plugin.features.customitems.entity.Entity baseEntity;

    public AbstractProjectile(Target target, Source source, Location location, double gravity, double speed,
                              Action onStartAction, Action onEntityHitAction, Action onBlockHitAction,
                              Action onProjectileTickAction, org.nebulamc.plugin.features.customitems.entity.Entity baseEntity,
                              double hitBoxSize, int maxLife, boolean pierceEntities) {
        this.target = target;
        this.source = source;
        this.location = location;
        this.gravity = gravity;
        this.speed = speed;
        this.onStartAction = onStartAction;
        this.onEntityHitAction = onEntityHitAction;
        this.onBlockHitAction = onBlockHitAction;
        this.onProjectileTickAction = onProjectileTickAction;
        this.hitBoxSize = hitBoxSize;
        this.maxLife = maxLife;
        this.pierceEntities = pierceEntities;
        this.baseEntity = baseEntity;

        this.projectile = this;
    }

    public static boolean isProjectile(Entity entity) {
        return entity.getMetadata("projectile").stream().anyMatch(value ->
                Objects.equals(value.getOwningPlugin(), Nebula.getInstance()));
    }

    protected abstract Vector getProjectileDirection();

    protected abstract void updateLocationAndDirection();

    public void run() {
        projectileLife = 0;
        location.setDirection(getProjectileDirection());
        oldLocation = location.clone();

        onStartAction.execute(new ProjectileTarget(projectile), source);

        this.entity = getEntity(baseEntity);
        new BukkitRunnable() {
            @Override
            public void run() {
                Map<String, Object> map = new HashMap<>();
                if (projectileHitBlock()) {
                    onBlockHitAction.execute(new ProjectileTarget(projectile), source);
                    if (!(boolean) map.getOrDefault("cancelled", false))
                        remove();
                    source.getCaster().removeMetadata("hitFace", Nebula.getInstance());
                }

                List<LivingEntity> hitEntities = getHitEntities();
                if (!hitEntities.isEmpty()) {
                    hitEntities.forEach(livingEntity -> onEntityHitAction.execute(new EntityTarget(livingEntity), source));
                    if (!pierceEntities){
                        remove();
                    }

                }

                onProjectileTickAction.execute(new ProjectileTarget(projectile), source);

                if (projectileLife++ > maxLife) {
                    remove();
                }

                if (gravity != 0) {
                    location.setDirection(location.getDirection().subtract(new Vector(0, gravity * .05, 0)));
                }

                oldLocation = location.clone();

                updateLocationAndDirection();

                if (entity != null && location.getDirection().isNormalized())
                    entity.setVelocity(location.getDirection().multiply(speed).multiply(.05));
            }

            private void remove() {
                if (entity != null) {
                    entity.remove();
                }
                this.cancel();
            }
        }.runTaskTimer(Nebula.getInstance(), 0, 1).getTaskId();
    }

    public Entity getEntity(org.nebulamc.plugin.features.customitems.entity.Entity entity) {
        Entity projectile = entity.spawnEntity(location);
        if (projectile != null) {
            projectile.setMetadata("projectile", new FixedMetadataValue(Nebula.getInstance(),
                    true));
            projectile.setGravity(false);
        }
        return projectile;
    }

    private boolean projectileHitBlock() {
        if (!location.getDirection().isNormalized()) {
            return location.getBlock().getType().isCollidable();
        }
        RayTraceResult result = oldLocation.getWorld().rayTraceBlocks(oldLocation, location.getDirection(),
                location.distance(oldLocation));
        if (result != null) {
            Vector v = result.getHitPosition();
            location.set(v.getX(), v.getY(), v.getZ()).add(location.getDirection().multiply(.00001));
            if (result.getHitBlockFace() != null) {
                source.getCaster().setMetadata("hitFace", new FixedMetadataValue(Nebula.getInstance(),
                        result.getHitBlockFace().name()));
            }
            return true;
        }
        return false;
    }

    private List<LivingEntity> getHitEntities() {
        if (hitBoxSize == 0) {
            return Lists.newArrayList();
        }
        try {
            return location.getWorld().getNearbyEntities(location, hitBoxSize, hitBoxSize, hitBoxSize,
                    entity -> entity instanceof LivingEntity && !entity.equals(source.getCaster())
                            && !isProjectile(entity)).stream().map(entity -> (LivingEntity) entity).
                    collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            return Lists.newArrayList();
        }
    }

    @NotNull
    protected Vector getSpreadedInitialDirection(Location clone, int projectileSpread) {
        Vector initialDirection = clone.getDirection();
        double spread = Math.toRadians(projectileSpread);
        if (spread != 0) {
            initialDirection.rotateAroundX((Math.random() - 0.5) * spread);
            initialDirection.rotateAroundY((Math.random() - 0.5) * spread);
            initialDirection.rotateAroundZ((Math.random() - 0.5) * spread);
        }
        return initialDirection;
    }
}
