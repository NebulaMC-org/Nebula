package org.nebulamc.plugin.features.adminevents;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.List;

public interface AdminEvent {

    void start();
    void onEnd(Player w);


    default void addPlayer(Player p) {
        getPlayers().add(p);
        getHost().sendMessage(
                Component.text(p.getName() + "joined the event")
                        .color(NamedTextColor.YELLOW)
        );
    }

    default void removePlayer(Player p) {
        getPlayers().remove(p);
        getHost().sendMessage(
                Component.text(p.getName() + "left the event")
                        .color(NamedTextColor.YELLOW)
        );
    }

    Date getStart();
    String getEventName();
    Player getHost();
    List<Player> getPlayers();

    State getState();
    void setState(State s);

    enum State {
        WAITING, QUEUEING, ACTIVE, ENDED
    }

}
