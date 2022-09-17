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
        if (Nebula.getInstance().getHAProxy() != null) {
            if (
                !Nebula.getInstance().getHAProxy().isOnEuropeanIP(e.getPlayer())
                && Nebula.getInstance().getHAProxy().isIPInEurope(e.getPlayer())
                && e.getPlayer().getPing() > 150
            ) {
                e.getPlayer().sendMessage(
                        Component.text("Hey, it looks like you're playing in Europe and have high ping. Connecting via ")
                                .color(NamedTextColor.AQUA)
                                .append(Component.text("eu.fridge.gay").color(NamedTextColor.GOLD))
                                .append(Component.text(" might help!").color(NamedTextColor.AQUA))
                );
            }
        }
        ManaManager.createManaBar(e.getPlayer());
    }

}
