package spectromeda.nebula.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import spectromeda.nebula.Nebula;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (Nebula.getInstance().haProxy() != null) {
            if (
                !Nebula.getInstance().haProxy().isOnEuropeanIP(e.getPlayer())
                && Nebula.getInstance().haProxy().isIPInEurope(e.getPlayer())
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
    }

}
