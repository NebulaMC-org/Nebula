package org.nebulamc.plugin.features.customitems.actions;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.nebulamc.plugin.features.customitems.Action;

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
    public void execute(Player player, Location location, Entity entity) {
        location.getWorld().spawnParticle(particle, location, count, xOffset, yOffset, zOffset, speed, item, true);
    }
}
