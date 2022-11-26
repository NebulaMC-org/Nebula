package org.nebulamc.plugin.features.customitems.actions;

import org.bukkit.util.Vector;
import org.nebulamc.plugin.features.customitems.source.Source;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;
import org.nebulamc.plugin.features.customitems.targeter.Target;
import org.nebulamc.plugin.utils.Utils;

public class PushAction extends Action {

    double power;
    double yPower;

    public PushAction(double power, double yPower){
        this.power = power;
        this.yPower = yPower;
    }

    @Override
    public void execute(Target target, Source source) {
        if (Utils.canDamage(target, source)){
            Vector velocity = target.getLocation().subtract(source.getLocation()).toVector().normalize();
            ((EntityTarget) target).getTarget().setVelocity(velocity.multiply(power).setY(yPower));
        }
    }
}
