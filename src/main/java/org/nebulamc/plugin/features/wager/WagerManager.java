package org.nebulamc.plugin.features.wager;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.nebulamc.plugin.Nebula;
import org.nebulamc.plugin.features.wager.events.WagerAcceptEvent;
import org.nebulamc.plugin.features.wager.events.WagerDeclineEvent;
import org.nebulamc.plugin.features.wager.events.WagerGameEndEvent;

import java.util.*;

public class WagerManager implements Listener {

    public HashMap<UUID, Wager> activeWagers = new HashMap<>();
    public LinkedList<Wager> queue = new LinkedList<>();

    @Getter
    private final HashMap<Player, Location> lastLocationCache = new HashMap<>();

    public WagerManager() {
        Bukkit.getPluginManager().registerEvents(this, Nebula.getInstance());
    }

    public void createWager(Player p, Player t) {
        UUID u = UUID.randomUUID();
        activeWagers.put(u, new Wager(u, p, t));
    }

    public void createHomosexWager(Player p, Player t) {
        UUID u = UUID.randomUUID();
        activeWagers.put(u, new HomosexWager(u, p, t));
    }

    @EventHandler
    public void onInviteAccept(WagerAcceptEvent e) {
        if (e.getWager() instanceof HomosexWager) {
            e.getWager().getHost().sendMessage(
                    Component.text(
                            e.getWager().getTarget().getName() + " would love to have homosex with you!"
                    ).color(NamedTextColor.GREEN)
            );
        } else e.getWager().getHost().sendMessage(
            Component.text(
                e.getWager().getTarget().getName() + " accepted your wager request!"
            ).color(NamedTextColor.GREEN)
        );
        e.getWager().promptForWageredItems();
    }

    @EventHandler
    public void onInviteDecline(WagerDeclineEvent e) {
        if (e.getWager() instanceof HomosexWager) {
            e.getWager().getHost().sendMessage(
                    Component.text(
                            e.getWager().getTarget().getName() + " doesn't want to have homosex with you."
                    ).color(NamedTextColor.RED)
            );
        } else e.getWager().getHost().sendMessage(
                Component.text(
                        e.getWager().getTarget().getName() + " declined your wager request."
                ).color(NamedTextColor.RED)
        );
        e.getWager().setStatus(Wager.Status.DECLINED);
        queue.remove(e.getWager());
    }


    @EventHandler
    public void onGameEnd(WagerGameEndEvent e) {
        e.getWager().onEnd(e.getWinner());
        queue.poll();
        if (queue.size() > 0) {
            queue.element().startGameplay();
        }
        queue.forEach(i -> {
            if (queue.indexOf(i) != 0) {
                i.audience().sendMessage(
                        Component.text("You are now in position " + Nebula.getInstance().getWagerManager().queue.indexOf(this) + ".")
                                .color(NamedTextColor.GREEN)
                );
            }
        });
    }

}
