package org.nebulamc.plugin.features.customitems.actions;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.nebulamc.plugin.Nebula;
import org.nebulamc.plugin.features.customitems.source.Source;
import org.nebulamc.plugin.features.customitems.targeter.Target;

public class TimedAction extends Action{

    boolean cancelled = false;
    Action action;
    int delay;
    int period;
    int repetitions;
    int repetitionCounter = 0;

    public void setCancelled(boolean cancelled){
        this.cancelled = cancelled;
    }

    public TimedAction(int delay, int period, int repetitions, Action action){
        this.action = action;
        this.delay = delay;
        this.period = period;
        this.repetitions = repetitions;
    }

    @Override
    public void execute(Target target, Source source) {
        new BukkitRunnable() {
            public void run() {
                if (cancelled){
                    Bukkit.getScheduler().cancelTask(this.getTaskId());
                } else {
                    action.execute(target, source);
                    repetitionCounter++;
                    if (repetitionCounter > repetitions){
                        cancelled = true;
                    }
                }
            }
        }.runTaskTimer(Nebula.getInstance(), delay, period);
    }
}
