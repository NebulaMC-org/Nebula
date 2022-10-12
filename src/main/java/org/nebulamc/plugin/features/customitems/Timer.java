package org.nebulamc.plugin.features.customitems;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.nebulamc.plugin.Nebula;

import java.util.ArrayList;
import java.util.List;

public class Timer {

    CustomItem customItem;
    List<Player> players = new ArrayList<>();
    boolean cancelled = false;


    public Timer(CustomItem item){
        customItem = item;
    }

    public void addPlayer(Player player){
        if (!(players.contains(player))){
            players.add(player);
        }
    }

    public void removePlayer(Player player){
        if (players.contains(player)){
            players.remove(player);
        }
    }

    public void setCancelled(boolean cancel){
        cancelled = cancel;
    }

    public void execute() {
        cancelled = false;
        new BukkitRunnable() {
            public void run() {
                if (cancelled){
                    Bukkit.getScheduler().cancelTask(this.getTaskId());
                }
                for (Player p : players){
                    customItem.doTimerAction(p);
                }
            }
        }.runTaskTimer(Nebula.getInstance(), 0, customItem.getTimerPeriod());
    }
}
