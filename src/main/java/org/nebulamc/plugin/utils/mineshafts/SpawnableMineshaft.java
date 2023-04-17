package org.nebulamc.plugin.utils.mineshafts;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.nebulamc.plugin.Nebula;
import org.nebulamc.plugin.utils.Utils;

public class SpawnableMineshaft {

    Location loc;
    Player sender;
    boolean cancelled;

    private static final Nebula plugin = Nebula.getInstance();

    SpawnableMineshaft(Location loc, Player sender){
        this.loc = loc;
        this.sender = sender;
    }
    public void run(){
        Utils.forceLoadChunk(loc, 3);
        new BukkitRunnable() {
            public void run() {
                if (cancelled){
                    Bukkit.getScheduler().cancelTask(this.getTaskId());
                } else {
                    Spawn();
                    cancelled = true;
                }
            }
        }.runTaskTimer(Nebula.getInstance(), 30, 1);
    }

    private void Spawn(){
        plugin.getServer().dispatchCommand(sender,
                "place structure minecraft:mineshaft " + (int) loc.getX() + " " + (int) loc.getY() + " " + (int) loc.getZ()
        );
    }


}
