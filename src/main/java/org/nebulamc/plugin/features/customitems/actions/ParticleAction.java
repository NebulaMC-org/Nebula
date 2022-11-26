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
    @Override
    public void execute(Target target, Source source) {
        Location location = target.getLocation();
        location.getWorld().spawnParticle(particle, location, count, xOffset, yOffset, zOffset, speed, item, true);
    }
}
