package org.nebulamc.plugin.utils.mineshafts;

import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.nebulamc.plugin.Nebula;
import org.nebulamc.plugin.utils.noise.WhiteNoise;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SpawnMineshafts implements Listener {

    private static final Nebula plugin = Nebula.getInstance();
    private static final Logger log = plugin.getLogger();
    private static List<Chunk> chunks = new ArrayList<>();

    static WhiteNoise noise = new WhiteNoise(01101110);


    public static void SpawnMineshafts(Player sender, double rarity) {

        int mineshaftsSpawned = 0;
        sender.setGameMode(GameMode.SPECTATOR);

        World world = sender.getWorld();
        double border = world.getWorldBorder().getSize()/2;
        double chunkNumber = border/16;
        for (double i = -chunkNumber; i < chunkNumber; i++) {
            for (double k = -chunkNumber; k < chunkNumber; k++) {
                if (rarity >= noise.noise(i, k)){
                    Location loc = new Location(world,i*16,  90,  k*16);
                    Chunk chunk = loc.getChunk();

                    if (mineshaftsSpawned <= 100){
                        chunks.add(chunk);
                        chunk.addPluginChunkTicket(plugin);
                        mineshaftsSpawned++;
                    } else {
                       SpawnInList(sender);
                       mineshaftsSpawned = 0;
                    }
                    log.info("[" + (int) ((i + chunkNumber)/(chunkNumber*2)*100) + "%]");
                }

            }
        }
        SpawnInList(sender);
    }

    private static void SpawnInList(Player sender){
        for (Chunk c : chunks){
            plugin.getServer().dispatchCommand(sender,
                    "place structure minecraft:mineshaft " + c.getX()*16 + " " + 0 +  " " + c.getZ()*16
            );
        }
        chunks.clear();
    }
}
