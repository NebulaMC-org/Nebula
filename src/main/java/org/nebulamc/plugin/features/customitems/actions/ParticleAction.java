package org.nebulamc.plugin.features.customitems.actions;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;
import org.nebulamc.plugin.features.customitems.source.Source;
import org.nebulamc.plugin.features.customitems.targeter.Target;

public class ParticleAction extends Action {

    Particle particle;
    int count;
    double xOffset;
    double yOffset;
    double zOffset;
    double speed;
    ItemStack item;
    Particle.DustOptions options;

    public ParticleAction(org.bukkit.Particle part, int count, double xOff, double yOff, double zOff, double speed){
        particle = part;
        this.count = count;
        this.speed = speed;
        xOffset = xOff;
        yOffset = yOff;
        zOffset = zOff;
    }

    public ParticleAction(org.bukkit.Particle part, int count, double xOff, double yOff, double zOff, double speed, ItemStack i){
        particle = part;
        this.count = count;
        this.speed = speed;
        xOffset = xOff;
        yOffset = yOff;
        zOffset = zOff;
        item = i;
    }

    public ParticleAction(org.bukkit.Particle part, int count, double xOff, double yOff, double zOff, double speed, Particle.DustOptions options){
        particle = part;
        this.count = count;
        this.speed = speed;
        xOffset = xOff;
        yOffset = yOff;
        zOffset = zOff;
        this.options = options;

    }
    @Override
    public void execute(Target target, Source source) {
        Location location = target.getLocation();
        if (item != null){
            location.getWorld().spawnParticle(particle, location, count, xOffset, yOffset, zOffset, speed, item, true);
        } else {
            location.getWorld().spawnParticle(particle, location, count, xOffset, yOffset, zOffset, speed, options);
        }

    }
}
