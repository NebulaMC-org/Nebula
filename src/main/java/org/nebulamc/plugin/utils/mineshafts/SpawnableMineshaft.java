package org.nebulamc.plugin.utils.mineshafts;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.nebulamc.plugin.Nebula;
import org.nebulamc.plugin.utils.Utils;

public class SpawnableMineshaft implements Runnable{

    Location loc;
    Player sender;

    private static final Nebula plugin = Nebula.getInstance();

    SpawnableMineshaft(Location loc, Player sender){
        this.loc = loc;
        this.sender = sender;
    }
    public void run(){
        Utils.loadChunksInArea(loc, 9);
        TrySpawn();
        Utils.unloadChunksInArea(loc, 9);
    }

    private boolean TrySpawn(){
        return plugin.getServer().dispatchCommand(sender,
                "place structure minecraft:mineshaft " + (int) loc.getX() + " " + (int) loc.getY() + " " + (int) loc.getZ()
        );
    }


}
