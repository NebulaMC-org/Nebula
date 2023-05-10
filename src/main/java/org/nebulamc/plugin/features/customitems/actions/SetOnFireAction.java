package org.nebulamc.plugin.features.customitems.actions;

import org.nebulamc.plugin.features.customitems.source.Source;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;
import org.nebulamc.plugin.features.customitems.targeter.Target;
import org.nebulamc.plugin.utils.Utils;

public class SetOnFireAction extends Action {


    int ticks;

    public SetOnFireAction(int ticks){
        this.ticks = ticks;
    }

    @Override
    public void execute(Target target, Source source) {
        if (target instanceof EntityTarget && Utils.canDamage(target, source)){
            ((EntityTarget) target).getTarget().setFireTicks(ticks);
        }
    }
}
