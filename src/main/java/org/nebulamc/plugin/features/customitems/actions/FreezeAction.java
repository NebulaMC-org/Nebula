package org.nebulamc.plugin.features.customitems.actions;

import org.nebulamc.plugin.features.customitems.source.Source;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;
import org.nebulamc.plugin.features.customitems.targeter.Target;
import org.nebulamc.plugin.utils.Utils;

public class FreezeAction extends Action{

    int freezeTicks;

    public FreezeAction(int freezeTicks){
        this.freezeTicks = freezeTicks;
    }

    @Override
    public void execute(Target target, Source source) {
        if (target instanceof EntityTarget){
            if (Utils.canDamage(target, source)){
                ((EntityTarget) target).getTarget().setFreezeTicks(freezeTicks);
            }
        }
    }
}
