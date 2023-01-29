package org.nebulamc.plugin.features.customitems.actions;

import org.bukkit.util.Vector;
import org.nebulamc.plugin.features.customitems.source.Source;
import org.nebulamc.plugin.features.customitems.targeter.LocationTarget;
import org.nebulamc.plugin.features.customitems.targeter.Target;

public class ChangeRelativeTargetAction extends Action{
    Action action;
    Vector relativeChange;

    public ChangeRelativeTargetAction(Action action, Vector relativeChange){
        this.action = action;
        this.relativeChange = relativeChange;
    }

    @Override
    public void execute(Target target, Source source) {
        action.execute(new LocationTarget(target.getLocation().clone().add(relativeChange)), source);
    }
}
