package org.nebulamc.plugin.features.customitems.actions;

import org.nebulamc.plugin.features.customitems.source.LocationSource;
import org.nebulamc.plugin.features.customitems.source.Source;
import org.nebulamc.plugin.features.customitems.targeter.Target;

public class ChangeSourceAction extends Action{

    Action action;
    Source newSource;

    public ChangeSourceAction(Action action, Source newSource){
        this.action = action;
        this.newSource = newSource;
    }

    public ChangeSourceAction(Action action){
        this.action = action;
    }

    @Override
    public void execute(Target target, Source source) {
        if (newSource == null){
            action.execute(target, new LocationSource(target.getLocation(), source.getCaster()));
        } else {
            action.execute(target, newSource);
        }

    }
}
