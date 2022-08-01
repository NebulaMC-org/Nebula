package org.nebulamc.plugin.features.wager;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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

    public Wager createWager(Player p, Player t) {
        UUID u = UUID.randomUUID();
        return activeWagers.put(u, new Wager(u, p, t));
    }

    @EventHandler
    public void onInviteAccept(WagerAcceptEvent e) {
        e.getWager().getHost().sendMessage(
            Component.text(
                e.getWager().getTarget().getName() + " accepted your wager request!"
            ).color(NamedTextColor.GREEN)
        );
        e.getWager().promptForWageredItems().thenRun(() -> e.getWager().joinQueue());
    }

    @EventHandler
    public void onInviteDecline(WagerDeclineEvent e) {
        e.getWager().getHost().sendMessage(
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

        queue.element().startGameplay();
        queue.forEach(i -> {
            if (queue.indexOf(i) != 0) {
                i.audience().sendMessage(
                        Component.text("You are now in position " + Nebula.getInstance().getWagerManager().queue.indexOf(this) + ".")
                                .color(NamedTextColor.GREEN)
                );
            }
        });

        // TODO: handle the next game being started
    }

}
