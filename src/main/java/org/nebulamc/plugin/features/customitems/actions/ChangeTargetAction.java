package org.nebulamc.plugin.features.customitems.actions;

import org.nebulamc.plugin.features.customitems.source.Source;
import org.nebulamc.plugin.features.customitems.targeter.Target;

public class ChangeTargetAction extends Action {
    Action action;
    Target newTarget;

    public ChangeTargetAction(Action action, Target newTarget){
        this.action = action;
        this.newTarget = newTarget;
    }

    @Override
    public void execute(Target target, Source source) {
        action.execute(newTarget, source);
    }

}
