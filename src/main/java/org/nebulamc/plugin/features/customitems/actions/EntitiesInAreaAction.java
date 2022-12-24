package org.nebulamc.plugin.features.customitems.actions;

import org.bukkit.entity.LivingEntity;
import org.nebulamc.plugin.features.customitems.area.Area;
import org.nebulamc.plugin.features.customitems.source.LocationSource;
import org.nebulamc.plugin.features.customitems.source.Source;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;
import org.nebulamc.plugin.features.customitems.targeter.Target;

import java.util.Collection;

public class EntitiesInAreaAction extends Action {

    Area area;
    Action action;
    boolean includeSource = false;

    public EntitiesInAreaAction(Area area, Action action){
        this.area = area;
        this.action = action;
    }

    public EntitiesInAreaAction(Area area, Action action, boolean includeSource){
        this.area = area;
        this.action = action;
        this.includeSource = includeSource;

    }

    @Override
    public void execute(Target target, Source source) {
        Collection<LivingEntity> nearbyEntities = area.entitiesInside(target.getLocation(), target, source);
        Source newSource = new LocationSource(target.getLocation(), source.getCaster());
        for (LivingEntity e : nearbyEntities){
            if (!includeSource && e.equals(source.getCaster())){

            } else {
                action.execute(new EntityTarget(e), newSource);
            }

        }
    }
}
