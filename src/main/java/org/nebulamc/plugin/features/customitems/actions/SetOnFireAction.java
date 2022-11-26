package org.nebulamc.plugin.features.customitems.actions;

import org.nebulamc.plugin.features.customitems.source.Source;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;
import org.nebulamc.plugin.features.customitems.targeter.Target;

public class SetOnFireAction extends Action {


    int ticks;

    public SetOnFireAction(int ticks){
        this.ticks = ticks;
    }

    @Override
    public void execute(Target target, Source source) {
        if (target instanceof EntityTarget){
            ((EntityTarget) target).getTarget().setFireTicks(ticks);
        }
    }
}
