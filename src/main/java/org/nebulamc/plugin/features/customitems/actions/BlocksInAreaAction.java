package org.nebulamc.plugin.features.customitems.actions;

import org.bukkit.Location;
import org.nebulamc.plugin.features.customitems.area.Area;
import org.nebulamc.plugin.features.customitems.source.LocationSource;
import org.nebulamc.plugin.features.customitems.source.Source;
import org.nebulamc.plugin.features.customitems.targeter.LocationTarget;
import org.nebulamc.plugin.features.customitems.targeter.Target;

import java.util.Collection;

public class BlocksInAreaAction extends Action {

    Area area;
    Action action;

    public BlocksInAreaAction(Area area, Action action) {
        this.area = area;
        this.action = action;
    }

    @Override
    public void execute(Target target, Source source) {
        Collection<Location> nearbyBlocks = area.getBlocks(target.getLocation(), target, source);
        Source newSource = new LocationSource(target.getLocation(), source.getCaster());
        for (Location l : nearbyBlocks) {
            action.execute(new LocationTarget(l), newSource);
        }
    }
}