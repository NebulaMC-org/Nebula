package org.nebulamc.plugin.features.wager;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.nebulamc.plugin.Nebula;
import org.nebulamc.plugin.utils.SavedInventory;

import java.util.UUID;

public class HomosexWager extends Wager {

    public HomosexWager(UUID u, Player h, Player t) {
        super(u, h, t);
    }

    private void sendInvite() {
        getHost().sendMessage(
                Component.text("Asking for homosex...")
                        .color(NamedTextColor.DARK_GRAY)
        );

        getTarget().sendMessage(
                Component.text(getHost().getName() + " would like to have homosex with you!")
                        .color(NamedTextColor.GOLD)
                        .append(Component.newline())
                        .append(
                                Component.text("[ACCEPT] ")
                                        .color(NamedTextColor.GREEN)
                                        .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/wager accept " + getUuid()))
                        ).append(
                                Component.text("[DECLINE]")
                                        .color(NamedTextColor.RED)
                                        .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/wager decline " + getUuid()))
                        )
        );

        getHost().sendMessage(
                Component.text("You asked " + getTarget().getName() + " for homosex.")
                        .color(NamedTextColor.GOLD)
        );
    }

    public void promptForWageredItems() {
        setStatus(Status.WAITING_ON_ITEM);

        audience().sendMessage(
                Component.text("Put the item you'd like to have homosex for in the first slot of your hotbar then click this message to continue.")
                        .color(NamedTextColor.GOLD)
                        .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/wager itemselect " + getUuid().toString()))
        );
    }


    public void onEnd(Player winner) {
        setStatus(Status.ENDED);
        winner.getInventory().addItem(getHostWager(), getTargetWager());

        audience().sendMessage(
                Component.text(winner.getName() + " is the sexiest!")
                        .color(NamedTextColor.GOLD)
        );

        SavedInventory.get(getHost()).apply(getHost());
        Location hl = Nebula.getInstance().getWagerManager().getLastLocationCache().get(getHost());
        getHost().teleport(hl);

        SavedInventory.get(getTarget()).apply(getTarget());
        Location tl = Nebula.getInstance().getWagerManager().getLastLocationCache().get(getTarget());
        getTarget().teleport(tl);
    }
}
