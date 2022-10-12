package org.nebulamc.plugin.features.playerdata;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.UUID;

public class PlayerManager implements Listener {

    public static HashMap<UUID, PlayerData> playerData = new HashMap<>();

    public static void createPlayerData(Player p){
        UUID id = p.getUniqueId();
        if (playerData.get(id) == null) {
            playerData.put(p.getUniqueId(), new PlayerData(id));
        }
        playerData.get(p.getUniqueId()).getManaBar().tickManaBar();
    }

    public static PlayerData getPlayerData(Player p){
        return playerData.get(p.getUniqueId());
    }

}
