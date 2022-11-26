package org.nebulamc.plugin.features.customitems.actions;

import org.nebulamc.plugin.features.customitems.source.Source;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;
import org.nebulamc.plugin.features.customitems.targeter.Target;
import org.nebulamc.plugin.utils.Utils;

public class SetVelocityAction extends Action {

    org.bukkit.util.Vector velocity;

    public SetVelocityAction(org.bukkit.util.Vector velocity){
        this.velocity = velocity;
    }

    @Override
    public void execute(Target target, Source source) {
        if (Utils.canDamage(target, source)){
            ((EntityTarget) target).getTarget().setVelocity(velocity);
        }
    }
}

