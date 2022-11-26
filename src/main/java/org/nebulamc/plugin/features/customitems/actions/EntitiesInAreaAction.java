package org.nebulamc.plugin.features.customitems.actions;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.nebulamc.plugin.features.customitems.source.LocationSource;
import org.nebulamc.plugin.features.customitems.source.Source;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;
import org.nebulamc.plugin.features.customitems.targeter.Target;

import java.util.Collection;

public class EntitiesInAreaAction extends Action {

    double radius;
    Action action;
    boolean includeSource = false;

    public EntitiesInAreaAction(double radius, Action action){
        this.radius = radius;
        this.action = action;
    }

    public EntitiesInAreaAction(double radius, Action action, boolean includeSource){
        this.radius = radius;
        this.action = action;
        this.includeSource = includeSource;

    }

    @Override
    public void execute(Target target, Source source) {
        Collection<Entity> nearbyEntities = target.getLocation().getWorld().getNearbyEntities(target.getLocation(), radius, radius, radius);
        Source newSource = new LocationSource(target.getLocation(), source.getCaster());
        for (Entity e : nearbyEntities){
            if (e != null && !includeSource && (e instanceof Player) && e.equals(source.getCaster())) {
                break;
            }
            if (e instanceof LivingEntity){
                action.execute(new EntityTarget((LivingEntity) e), newSource);
            }

        }
    }
}
