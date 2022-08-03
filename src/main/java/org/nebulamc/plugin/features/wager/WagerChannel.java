package org.nebulamc.plugin.features.wager;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class WagerChannel implements Listener {

    private static final Component PREFIX = Component.text("WAGERS ⋙ ").color(NamedTextColor.DARK_AQUA);

    private final List<Player> players = new ArrayList<>();
    private Audience audience = Audience.audience();

    public void toggle(Player p) {
        if (players.contains(p)) addPlayer(p);
        else removePlayer(p);
    }

    public void addPlayer(Player p) {
        players.add(p);
        p.sendMessage(
            PREFIX.append(
                Component.text("Joined wager info channel.").color(NamedTextColor.GRAY)
            )
        );
        audience = Audience.audience(players);
    }

    public void removePlayer(Player p) {
        players.remove(p);
        p.sendMessage(
            PREFIX.append(
                Component.text("Left wager info channel.").color(NamedTextColor.GRAY)
            )
        );
        audience = Audience.audience(players);
    }

    public void wagerCreate(Wager w) {
        audience.sendMessage(
            PREFIX.append(
                Component.text(w.getHost().getName()).color(NamedTextColor.AQUA)
            ).append(
                Component.text(" invited ").color(NamedTextColor.GRAY)
            ).append(
                Component.text(w.getTarget().getName()).color(NamedTextColor.AQUA)
            ).append(
                Component.text(" to a wager.").color(NamedTextColor.GRAY)
            )
        );
    }

    public void stateChange(Wager w) {
        audience.sendMessage(
            PREFIX.append(
                Component.text("Wager #").color(NamedTextColor.GRAY)
            ).append(
                Component.text(w.getId()).color(NamedTextColor.AQUA)
            ).append(
                Component.text("'s state changed to").color(NamedTextColor.GRAY)
            ).append(
                Component.text(w.getStatus().toString()).color(NamedTextColor.AQUA)
            )
        );
    }

    public void inviteAccept(Wager w) {
        audience.sendMessage(
            PREFIX.append(
                Component.text("Wager").color(NamedTextColor.AQUA)
            ).append(
                Component.text("  ").color(NamedTextColor.GRAY)
            ).append(
                Component.text(w.getTarget().getName()).color(NamedTextColor.AQUA)
            ).append(
                Component.text("'s wager.").color(NamedTextColor.GRAY)
            )
        );
    }

    public void inviteDecline(Wager w) {
        audience.sendMessage(
            PREFIX.append(
                Component.text(w.getTarget().getName()).color(NamedTextColor.AQUA)
            ).append(
                Component.text(" declined ").color(NamedTextColor.GRAY)
            ).append(
                Component.text(w.getTarget().getName()).color(NamedTextColor.AQUA)
            ).append(
                Component.text("'s wager.").color(NamedTextColor.GRAY)
            )
        );
    }

    public void offerAccept(Wager w, Player a) {
        audience.sendMessage(
            PREFIX.append(
                Component.text(a.getName()).color(NamedTextColor.AQUA)
            ).append(
                Component.text(" declined ").color(NamedTextColor.GRAY)
            ).append(
                Component.text(
                    w.getHost() == a
                        ? w.getHost().getName()
                        : w.getTarget().getName()
                ).color(NamedTextColor.AQUA)
            ).append(
                Component.text("'s wager offer.").color(NamedTextColor.GRAY)
            )
        );
    }

    public void offerDecline(Wager w, Player d) {
        audience.sendMessage(
            PREFIX.append(
                Component.text(d.getName()).color(NamedTextColor.AQUA)
            ).append(
                Component.text(" declined ").color(NamedTextColor.GRAY)
            ).append(
                Component.text(
                    w.getHost() == d
                            ? w.getHost().getName()
                            : w.getTarget().getName()
                ).color(NamedTextColor.AQUA)
            ).append(
                Component.text("'s wager offer.").color(NamedTextColor.GRAY)
            )
        );
    }

    public void gameStart(Wager w) {
        audience.sendMessage(
            PREFIX.append(
                Component.text("#" + w.getId()).color(NamedTextColor.AQUA)
            ).append(
                Component.text("'s game started.'").color(NamedTextColor.GRAY)
            )
        );
    }

    public void gameEnd(Wager w) {
        audience.sendMessage(
            PREFIX.append(
                Component.text("#" + w.getId()).color(NamedTextColor.AQUA)
            ).append(
                Component.text("'s game ended.").color(NamedTextColor.GRAY)
            )
        );
    }

}
