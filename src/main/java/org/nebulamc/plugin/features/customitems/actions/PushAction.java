package org.nebulamc.plugin.features.customitems.actions;

import me.angeschossen.lands.api.flags.Flags;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;
import org.nebulamc.plugin.features.customitems.Action;
import org.nebulamc.plugin.utils.Utils;

public class PushAction extends Action {

    double power;

    public PushAction(double power){
        this.power = power;
    }

    @Override
    public void execute(Player player, Location location, Entity target) {
        if (target instanceof LivingEntity){
            if (target instanceof Player){
                if (!Utils.hasFlag(player, location, null, Flags.ATTACK_PLAYER))
                    return;
            }
            if (target instanceof Monster){
                if (!Utils.hasFlag(player, location, null, Flags.ATTACK_MONSTER))
                    return;
            }
            if (target instanceof Animals){
                if (!Utils.hasFlag(player, location, null, Flags.ATTACK_ANIMAL))
                    return;
            }
            Vector velocity = target.getLocation().subtract(location).toVector().normalize();
            target.setVelocity(velocity.multiply(power).setY(power/2));
        }
    }
}
