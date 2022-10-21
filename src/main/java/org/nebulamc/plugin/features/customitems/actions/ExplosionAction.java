package org.nebulamc.plugin.features.customitems.actions;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.nebulamc.plugin.features.customitems.Action;

public class ExplosionAction extends Action {

    double damage;
    double power;
    int fireTicks = 0;

    public ExplosionAction(double damage, double power, int fireTicks){
        this.damage = damage;
        this.power = power;
        this.fireTicks = fireTicks;
    }

    @Override
    public void execute(Player player, Location location, Entity entity) {
        location.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, location, 1, 0, 0, 0, 0, null, true);
        location.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 6f, 1f);
        new EntitiesInAreaAction(4, new ListAction(new DamageAction(damage), new SetOnFireAction(fireTicks))).execute(player, location, entity);
    }
}
