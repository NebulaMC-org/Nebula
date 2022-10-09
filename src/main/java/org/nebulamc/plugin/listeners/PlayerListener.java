package org.nebulamc.plugin.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.nebulamc.plugin.features.playerdata.PlayerManager;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        PlayerManager.createPlayerData(e.getPlayer());
    }

}
