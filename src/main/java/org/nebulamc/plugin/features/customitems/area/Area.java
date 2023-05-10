package org.nebulamc.plugin.features.customitems.area;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;
import org.nebulamc.plugin.features.customitems.source.Source;
import org.nebulamc.plugin.features.customitems.targeter.Target;

import java.util.Collection;
import java.util.List;

public abstract class Area {

    protected Vector centerTranslation;

    public Area(Vector centerTranslation) {
        this.centerTranslation = centerTranslation;
    }

    protected Location finalLocation(Location center, Target target, Source source) {
        return center.clone().add(centerTranslation);
    }

    public abstract List<Location> getBlocks(Location center, Target target, Source source);

    public abstract Collection<LivingEntity> entitiesInside(Location center, Target target, Source source);

    public abstract String getName();

    public abstract Area clone();

    protected static double lengthSq(double x, double y, double z) {
        return (x * x) + (y * y) + (z * z);
    }

    protected static double lengthSq(double x, double z) {
        return (x * x) + (z * z);
    }
}
