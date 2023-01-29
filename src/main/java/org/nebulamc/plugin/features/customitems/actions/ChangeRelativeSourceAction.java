package org.nebulamc.plugin.features.customitems.actions;

import org.bukkit.util.Vector;
import org.nebulamc.plugin.features.customitems.source.LocationSource;
import org.nebulamc.plugin.features.customitems.source.Source;
import org.nebulamc.plugin.features.customitems.targeter.Target;

public class ChangeRelativeSourceAction extends Action{
    Action action;
    Vector relativeChange;

    public ChangeRelativeSourceAction(Action action, Vector relativeChange){
        this.action = action;
        this.relativeChange = relativeChange;
    }

    @Override
    public void execute(Target target, Source source) {
        action.execute(target, new LocationSource(source.getLocation().clone().add(relativeChange), source.getCaster()));
    }
}
