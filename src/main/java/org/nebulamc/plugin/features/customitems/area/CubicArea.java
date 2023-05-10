package org.nebulamc.plugin.features.customitems.area;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;
import org.nebulamc.plugin.features.customitems.source.Source;
import org.nebulamc.plugin.features.customitems.targeter.Target;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CubicArea extends Area {

    private final double range;
    private final boolean onlyBorders;

    public CubicArea() {
        this(new Vector(0, 0, 0), 3, false);
    }

    public CubicArea(Vector centerTranslation, double range, boolean onlyBorders) {
        super(centerTranslation);
        this.range = range;
        this.onlyBorders = onlyBorders;
    }

    @Override
    public List<Location> getBlocks(Location center, Target target, Source source) {
        Location finalCenter = finalLocation(center, target, source);
        List<Location> blocks = new ArrayList<>();
        double radius = range + 0.5;

        for (int x = 0; x <= radius; ++x) {
            for (int y = 0; y <= radius; ++y) {
                for (int z = 0; z <= radius; ++z) {

                    if (onlyBorders) {
                        if (x < radius - 1 && y < radius - 1 && z < radius - 1) {
                            continue;
                        }
                    }

                    blocks.add(finalCenter.clone().add(x, y, z));
                    blocks.add(finalCenter.clone().add(-x, y, z));
                    blocks.add(finalCenter.clone().add(x, -y, z));
                    blocks.add(finalCenter.clone().add(-x, -y, z));
                    blocks.add(finalCenter.clone().add(x, y, -z));
                    blocks.add(finalCenter.clone().add(-x, y, -z));
                    blocks.add(finalCenter.clone().add(x, -y, -z));
                    blocks.add(finalCenter.clone().add(-x, -y, -z));
                }
            }
        }

        return blocks;
    }

    @Override
    public Collection<LivingEntity> entitiesInside(Location center, Target target, Source source) {
        Location l = finalLocation(center, target, source);
        return l.getWorld().getNearbyEntities(getMinimumBoundingBox(l, range)).
                stream().filter(entity -> entity instanceof LivingEntity).
                map(entity -> (LivingEntity) entity).collect(Collectors.toList());
    }

    private BoundingBox getMinimumBoundingBox(Location l, double range) {
        return new BoundingBox(l.getX() - range, l.getY() - range, l.getZ() - range,
                l.getX() + range, l.getY() + range, l.getZ() + range);
    }

    @Override
    public String getName() {
        return "&6&lCubic range:&c " + range;
    }

    @Override
    public Area clone() {
        return new CubicArea(centerTranslation.clone(), range, onlyBorders);
    }
}
