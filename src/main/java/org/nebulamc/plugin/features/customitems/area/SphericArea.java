package org.nebulamc.plugin.features.customitems.area;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.nebulamc.plugin.features.customitems.source.Source;
import org.nebulamc.plugin.features.customitems.targeter.Target;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SphericArea extends Area {

    private double range;
    private boolean onlyBorders;

    public SphericArea() {
        this(new Vector(0, 0, 0), 3, false);
    }

    public SphericArea(Vector centerTranslation, double range, boolean onlyBorders) {
        super(centerTranslation);
        this.range = range;
        this.onlyBorders = onlyBorders;
    }

    /**
     * Method implementation provided by WorldEdit library
     * <p>
     * WorldEdit, a Minecraft world manipulation toolkit
     * Copyright (C) sk89q <http://www.sk89q.com>
     * Copyright (C) WorldEdit team and contributors
     */
    @Override
    public List<Location> getBlocks(Location center, Target target, Source source) {
        Location finalCenter = finalLocation(center, target, source);
        List<Location> blocks = new ArrayList<>();
        double radius = range + 0.5;

        final double invRadius = 1 / radius;

        double nextXn = 0;
        forX:
        for (int x = 0; x <= radius; ++x) {
            final double xn = nextXn;
            nextXn = (x + 1) * invRadius;
            double nextYn = 0;
            forY:
            for (int y = 0; y <= radius; ++y) {
                final double yn = nextYn;
                nextYn = (y + 1) * invRadius;
                double nextZn = 0;
                for (int z = 0; z <= radius; ++z) {
                    final double zn = nextZn;
                    nextZn = (z + 1) * invRadius;

                    double distanceSq = lengthSq(xn, yn, zn);
                    if (distanceSq > 1) {
                        if (z == 0) {
                            if (y == 0) {
                                break forX;
                            }
                            break forY;
                        }
                        break;
                    }

                    if (onlyBorders) {
                        if (lengthSq(nextXn, yn, zn) <= 1 && lengthSq(xn, nextYn, zn) <= 1 && lengthSq(xn, yn, nextZn) <= 1) {
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
        double radius = range;
        Location l = finalLocation(center, target, source);
        return l.getWorld().getNearbyEntities(getMinimumBoundingBox(radius, l)).stream().filter(entity ->
                (entity instanceof LivingEntity) && entity.getLocation().distance(l) <= radius).
                map(entity -> (LivingEntity) entity).collect(Collectors.toList());
    }

    @NotNull
    private BoundingBox getMinimumBoundingBox(double radius, Location l) {
        return new BoundingBox(l.getX() - radius, l.getY() - radius, l.getZ() - radius,
                l.getX() + radius, l.getY() + radius, l.getZ() + radius);
    }

    @Override
    public String getName() {
        return "&6&lSphere range: " + range;
    }

    @Override
    public Area clone() {
        return new SphericArea(centerTranslation.clone(), range, onlyBorders);
    }
}
