package org.nebulamc.plugin.features.customitems.actions;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.nebulamc.plugin.features.customitems.Action;

import java.util.Collection;

public class EntitiesInAreaAction extends Action {

    double radius;
    Action action;

    public EntitiesInAreaAction(double radius, Action action){
        this.radius = radius;
        this.action = action;
    }

    @Override
    public void execute(Player player, Location location, Entity entity) {
        Collection<Entity> nearbyEntities = location.getWorld().getNearbyEntities(location, radius, radius, radius);
        for (Entity e : nearbyEntities){
            action.execute(player, location, e);
        }
    }
}
