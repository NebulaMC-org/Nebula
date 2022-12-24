package org.nebulamc.plugin.features.customitems.actions;

import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.nebulamc.plugin.features.customitems.entity.Entity;

public abstract class AbstractProjectileAction extends Action {

    protected static final double INITIAL_DEFAULT = 10.0;
    protected static final double GRAVITY_DEFAULT = 0.1;
    protected static final double HIT_BOX_SIZE_DEFAULT = 0.5;
    protected static final double MAX_LIFE_DEFAULT = 100.0;

    protected double initialSpeed;
    protected double gravity;
    protected Action onStartAction;
    protected Action onEntityHitAction;
    protected Action onBlockHitAction;
    protected Action onProjectileTickAction;
    protected Entity entity;
    protected double hitBoxSize;
    protected int maxLife;
    boolean pierceEntities;
    boolean dragOnGround;

    public AbstractProjectileAction(double initialSpeed, double gravity,
                                    Action onStartAction, Action onEntityHitAction, Action onBlockHitAction,
                                    Action onProjectileTickAction, Entity entity, double hitBoxSize,
                                    int maxLife, boolean pierceEntities, boolean dragOnGround) {

        this.initialSpeed = initialSpeed;
        this.gravity = gravity;
        this.onStartAction = onStartAction;
        this.onEntityHitAction = onEntityHitAction;
        this.onBlockHitAction = onBlockHitAction;
        this.onProjectileTickAction = onProjectileTickAction;
        this.entity = entity;
        this.hitBoxSize = hitBoxSize;
        this.maxLife = maxLife;
        this.pierceEntities = pierceEntities;
        this.dragOnGround = dragOnGround;
    }

    protected Location getRightSide(Location location, double distance) {
        float angle = location.getYaw() / 60;
        return location.clone().subtract(new Vector(Math.cos(angle), 0, Math.sin(angle)).normalize().multiply(distance));
    }

    protected Location getLeftSide(Location location, double distance) {
        float angle = location.getYaw() / 60;
        return location.clone().add(new Vector(Math.cos(angle), 0, Math.sin(angle)).normalize().multiply(distance));
    }
}
