package org.nebulamc.plugin.features.customitems.actions;

import org.bukkit.util.Vector;
import org.nebulamc.plugin.features.customitems.source.Source;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;
import org.nebulamc.plugin.features.customitems.targeter.Target;
import org.nebulamc.plugin.utils.Utils;

public class PullAction extends Action {

    double power;
    boolean reverse;
    double addedPower;

    public PullAction(double power, boolean reverse){
        this.power = power;
        this.reverse = reverse;
    }

    public PullAction(boolean reverse){
        this.reverse = reverse;
    }

    public PullAction(boolean reverse, double addedPower){
        this.reverse = reverse;
        this.addedPower = addedPower;
    }

    @Override @SuppressWarnings("IllegalArgumentException")
    public void execute(Target target, Source source) {
        if (Utils.canDamage(target, source)){
            double tempPower = power;
            if (tempPower == 0){
                tempPower = target.getLocation().distance(source.getLocation())/10 + addedPower;
            }
            if (reverse){
                Vector velocity = target.getLocation().subtract(source.getLocation()).toVector().normalize();
                source.getCaster().setVelocity(velocity.multiply(tempPower));
            } else {
                Vector velocity = source.getLocation().subtract(target.getLocation()).toVector().normalize();
                ((EntityTarget) target).getTarget().setVelocity(velocity.multiply(tempPower));
            }
        }
    }
}
