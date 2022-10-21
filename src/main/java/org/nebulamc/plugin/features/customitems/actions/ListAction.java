package org.nebulamc.plugin.features.customitems.actions;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.nebulamc.plugin.features.customitems.Action;

public class ListAction extends Action {

    Action[] actionList;

    @Override
    public void execute(Player player, Location location, Entity entity) {
        for (Action a : actionList){
            a.execute(player, location, entity);
        }
    }

    public ListAction(Action ...actions){
        actionList = actions;
    }

}
