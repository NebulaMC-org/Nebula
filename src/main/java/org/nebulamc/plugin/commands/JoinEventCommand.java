package org.nebulamc.plugin.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.nebulamc.plugin.Nebula;
import org.nebulamc.plugin.features.adminevents.AdminEvent;

public class JoinEventCommand implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player p = (Player) sender;

        if (Nebula.getInstance().getAdminEventManager().getCurrentEvent() == null
                || Nebula.getInstance().getAdminEventManager().getCurrentEvent().getState() != AdminEvent.State.QUEUEING) {
            p.sendMessage(
                    Component.text("There are no joinable events.").color(NamedTextColor.YELLOW)
            );
        } else if (Nebula.getInstance().getAdminEventManager().getCurrentEvent().getPlayers().contains(p)) {
            p.sendMessage(
                    Component.text("You're already in this event!").color(NamedTextColor.YELLOW)
            );
        } else {
            Nebula.getInstance().getAdminEventManager().getCurrentEvent().addPlayer(p);
            p.sendMessage(
                    Component.text("Joined the event.").color(NamedTextColor.YELLOW)
            );
        }

        return true;
    }
}
