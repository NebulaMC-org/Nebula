package org.nebulamc.plugin.features.customitems.actions;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.nebulamc.plugin.Nebula;
import org.nebulamc.plugin.features.customitems.source.Source;
import org.nebulamc.plugin.features.customitems.targeter.Target;

public class DelayedAction extends Action{

    boolean cancelled = false;
    Action action;
    int delay;

    public void setCancelled(boolean cancelled){
        this.cancelled = cancelled;
    }

    public DelayedAction(int delay, Action action){
        this.action = action;
        this.delay = delay;
    }

    @Override
    public void execute(Target target, Source source) {
        new BukkitRunnable() {
            public void run() {
                if (cancelled){
                    Bukkit.getScheduler().cancelTask(this.getTaskId());
                } else {
                    action.execute(target, source);
                    cancelled = true;
                }
            }
        }.runTaskTimer(Nebula.getInstance(), delay, 1);
    }

}
