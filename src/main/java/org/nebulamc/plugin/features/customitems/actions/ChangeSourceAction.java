package org.nebulamc.plugin.features.customitems.actions;

import org.nebulamc.plugin.features.customitems.source.Source;
import org.nebulamc.plugin.features.customitems.targeter.Target;

public class ChangeSourceAction extends Action{

    Action action;
    Source newSource;

    public ChangeSourceAction(Action action, Source newSource){
        this.action = action;
        this.newSource = newSource;
    }

    @Override
    public void execute(Target target, Source source) {
        action.execute(target, newSource);
    }
}
