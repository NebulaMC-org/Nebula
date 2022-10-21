package org.nebulamc.plugin.features.customitems.actions;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.nebulamc.plugin.features.customitems.Action;

public class SetOnFireAction extends Action {


    int ticks;

    public SetOnFireAction(int ticks){
        this.ticks = ticks;
    }

    @Override
    public void execute(Player player, Location location, Entity target) {
        if (target instanceof LivingEntity){
            target.setFireTicks(ticks);
        }
    }
}
