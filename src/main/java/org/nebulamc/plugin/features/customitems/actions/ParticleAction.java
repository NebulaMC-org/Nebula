package org.nebulamc.plugin.features.customitems.actions;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;
import org.nebulamc.plugin.features.customitems.Action;

public class ParticleAction extends Action {

    Particle particle;
    Location location;
    int count;
    double xOffset;
    double yOffset;
    double zOffset;
    ItemStack item;

    public ParticleAction(org.bukkit.Particle part, Location loc, int cnt, double xOff, double yOff, double zOff){
        particle = part;
        location = loc;
        count = cnt;
        xOffset = xOff;
        yOffset = yOff;
        zOffset = zOff;
    }

    public ParticleAction(org.bukkit.Particle part, Location loc, int cnt, double xOff, double yOff, double zOff, ItemStack i){
        particle = part;
        location = loc;
        count = cnt;
        xOffset = xOff;
        yOffset = yOff;
        zOffset = zOff;
        item = i;
    }
    @Override
    public void execute() {
        location.getWorld().spawnParticle(particle, location, count, xOffset, yOffset, zOffset, 0, item);
    }
}
