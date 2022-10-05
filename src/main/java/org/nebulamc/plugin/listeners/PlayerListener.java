package org.nebulamc.plugin.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.nebulamc.plugin.Nebula;
import org.nebulamc.plugin.features.mana.ManaManager;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Nebula.getInstance().getPlayerManager().create(e.getPlayer());
        ManaManager.createManaBar(e.getPlayer());
    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent e) {
        Nebula.getInstance().getPlayerManager().delete(e.getPlayer());
        ManaManager.deleteManaBar(e.getPlayer());
    }

}
