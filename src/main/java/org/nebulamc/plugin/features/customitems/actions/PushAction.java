package org.nebulamc.plugin.features.customitems.actions;

import org.bukkit.util.Vector;
import org.nebulamc.plugin.features.customitems.source.Source;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;
import org.nebulamc.plugin.features.customitems.targeter.Target;
import org.nebulamc.plugin.utils.Utils;

import java.util.Random;

public class PushAction extends Action {

    double power;
    double yPower;
    boolean powerVaries;
    Random generator = new Random();

    public PushAction(double power, double yPower, boolean powerVaries){
        this.power = power;
        this.yPower = yPower;
        this.powerVaries = powerVaries;
    }

    @Override @SuppressWarnings("IllegalArgumentException")
    public void execute(Target target, Source source) {
        if (Utils.canDamage(target, source)){
            Vector velocity = target.getLocation().subtract(source.getLocation()).toVector().normalize();

            if (powerVaries){
                power *= (generator.nextDouble()*0.4) + 0.8;
                yPower *= (generator.nextDouble()*0.4) + 0.8;
            }
            try {
                ((EntityTarget) target).getTarget().setVelocity(velocity.multiply(power).setY(yPower));
            } catch (IllegalArgumentException e){

            }
        }
    }
}
