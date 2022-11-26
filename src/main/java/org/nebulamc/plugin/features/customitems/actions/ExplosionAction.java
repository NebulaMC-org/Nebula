package org.nebulamc.plugin.features.customitems.actions;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.nebulamc.plugin.features.customitems.source.LocationSource;
import org.nebulamc.plugin.features.customitems.source.Source;
import org.nebulamc.plugin.features.customitems.targeter.Target;

public class ExplosionAction extends Action {

    double damage;
    double power;
    int fireTicks;

    public ExplosionAction(double damage, double power, int fireTicks){
        this.damage = damage;
        this.power = power;
        this.fireTicks = fireTicks;
    }

    @Override
    public void execute(Target target, Source source) {
        Location location = target.getLocation();
        location.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, location, 1, 0, 0, 0, 0, null, true);
        location.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 6f, 1f);
        new EntitiesInAreaAction(6,
                new ListAction(new DamageAction(damage),
                        new SetOnFireAction(fireTicks),
                        new PushAction(power, power/2)))
                .execute(target, new LocationSource(target.getLocation(), source.getCaster()));
    }
}
