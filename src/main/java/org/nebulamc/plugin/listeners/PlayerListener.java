package org.nebulamc.plugin.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.nebulamc.plugin.Nebula;
import org.nebulamc.plugin.features.mana.ManaManager;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        ManaManager.createManaBar(e.getPlayer());
    }

}
