package org.nebulamc.plugin.features.customitems.area;

import lombok.Getter;
import lombok.Setter;
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

@Getter
@Setter
public class CylindricArea extends Area {

    private int height;
    private double range;
    private boolean onlyBorders;

    public CylindricArea() {
        this(new Vector(0, 0, 0), 3, 3, false);
    }

    public CylindricArea(Vector centerTranslation, int height, double range, boolean onlyBorders) {
        super(centerTranslation);
        this.height = height;
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
        Location finalLocation = finalLocation(center, target, source);
        List<Location> blocks = new ArrayList<>();
        double radius = range;
        int height = this.height;

        radius += 0.5;


        if (height == 0) {
            return blocks;
        } else if (height < 0) {
            height = -height;
            finalLocation.subtract(0, height, 0);
        }

        if (finalLocation.getBlockY() < 0) {
            finalLocation.setY(0);
        } else if (finalLocation.getBlockY() + height - 1 > 255) {
            height = 255 - finalLocation.getBlockY() + 1;
        }

        final double invRadius = 1 / radius;

        final int ceilRadius = (int) Math.ceil(radius);

        double nextXn = 0;
        forX:
        for (int x = 0; x <= ceilRadius; ++x) {
            final double xn = nextXn;
            nextXn = (x + 1) * invRadius;
            double nextZn = 0;
            for (int z = 0; z <= ceilRadius; ++z) {
                final double zn = nextZn;
                nextZn = (z + 1) * invRadius;

                double distanceSq = lengthSq(xn, zn);
                if (distanceSq > 1) {
                    if (z == 0) {
                        break forX;
                    }
                    break;
                }

                if (onlyBorders) {
                    if (lengthSq(nextXn, zn) <= 1 && lengthSq(xn, nextZn) <= 1) {
                        continue;
                    }
                }

                for (int y = 0; y < height; ++y) {
                    blocks.add(finalLocation.clone().add(x, y, z));
                    blocks.add(finalLocation.clone().add(-x, y, z));
                    blocks.add(finalLocation.clone().add(x, y, -z));
                    blocks.add(finalLocation.clone().add(-x, y, -z));
                }
            }
        }
        return blocks;
    }

    @Override
    public Collection<LivingEntity> entitiesInside(Location center, Target target, Source source) {
        double height = this.height;
        double range = this.range;
        Location l = finalLocation(center, target, source);
        return l.getWorld().getNearbyEntities(getMinimumBoundingBox(height, range, l)).stream().filter(entity -> {
            Vector diff = entity.getLocation().clone().subtract(l).toVector();
            diff.setY(0);
            return (entity instanceof LivingEntity) && diff.length() < range;
        }).map(entity -> (LivingEntity) entity).collect(Collectors.toList());
    }

    @NotNull
    private BoundingBox getMinimumBoundingBox(double height, double range, Location l) {
        BoundingBox box = new BoundingBox(l.getX() - range, l.getY() - height, l.getZ() - range,
                l.getX() + range, l.getY() + height, l.getZ() + range);
        return box;
    }

    @Override
    public String getName() {
        return "&6&lCylinder range: &c" + range + "&6&l, height: &c" + height;
    }

    @Override
    public Area clone() {
        return new CylindricArea(centerTranslation.clone(), height, range, onlyBorders);
    }
}
