package org.nebulamc.plugin.features.customitems.actions;

import org.nebulamc.plugin.features.customitems.source.Source;
import org.nebulamc.plugin.features.customitems.targeter.Target;

public class ListAction extends Action {

    Action[] actionList;

    @Override
    public void execute(Target target, Source source) {
        for (Action a : actionList){
            a.execute(target, source);
        }
    }

    public ListAction(Action ...actions){
        actionList = actions;
    }

}
