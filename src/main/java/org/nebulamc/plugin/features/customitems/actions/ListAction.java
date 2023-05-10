package org.nebulamc.plugin.features.customitems.actions;

import org.nebulamc.plugin.features.customitems.source.Source;
import org.nebulamc.plugin.features.customitems.targeter.Target;

import java.util.ArrayList;
import java.util.List;

public class ListAction extends Action {

    List<Action> actionList = new ArrayList<>();

    @Override
    public void execute(Target target, Source source) {
        for (Action a : actionList){
            a.execute(target, source);
        }
    }

    public ListAction(Action ...actions){
        for (Action a : actions){
            actionList.add(a);
        }
    }

    public void addAction(Action action){
        actionList.add(action);
    }

}
