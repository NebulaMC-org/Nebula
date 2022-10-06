package org.nebulamc.plugin.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.nebulamc.plugin.Nebula;
import org.nebulamc.plugin.features.adminevents.impl.FFAAdminEvent;

public class RunEventCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player p = (Player) sender;

        if (args.length == 0) {
            p.sendMessage(
                    Component.text("Missing argument \"type\"!")
                            .color(NamedTextColor.RED)
            );
        } else {
            switch (args[0].toLowerCase()) {
                case "ffa" -> {
                    long timerLength = args.length == 2 && p.hasPermission("events.override-timer") ? Long.parseLong(args[1]) : 5;
                    FFAAdminEvent e = Nebula.getInstance().getAdminEventManager().startEvent(p, FFAAdminEvent.class, timerLength);
                    if (e == null) {
                        p.sendMessage(
                                Component.text("Started an FFA admin event.")
                                        .color(NamedTextColor.YELLOW)
                        );
                    } else {
                        p.sendMessage(
                                Component.text("An issue occured while starting the event, please check console.")
                                        .color(NamedTextColor.RED)
                        );
                    }
                }
//                case "koth" -> {
//                    long timerLength = args.length == 2 && p.hasPermission("events.override-timer") ? Long.parseLong(args[1]) : 5;
//                    KOTHAdminEvent e = EventlyCore.getAdminEventManager().startEvent(p, KOTHAdminEvent.class, timerLength);
//                    if (e == null) {
//                        p.sendMessage(
//                                lang().getWithLegacyCodes("event_timer_start_error")
//                        );
//                    } else {
//                        p.sendMessage(
//                                lang().getWithLegacyCodes("started_koth")
//                        );
//                    }
//                }
//                case "race" -> {
//                    long timerLength = args.length == 2 && p.hasPermission("events.override-timer") ? Long.parseLong(args[1]) : 5;
//                    RaceAdminEvent e = EventlyCore.getAdminEventManager().startEvent(p, RaceAdminEvent.class, timerLength);
//                    if (e == null) {
//                        p.sendMessage(
//                                lang().getWithLegacyCodes("event_timer_start_error")
//                        );
//                    } else {
//                        p.sendMessage(
//                                lang().getWithLegacyCodes("started_race")
//                        );
//                    }
//                }
                default -> p.sendMessage(
                        Component.text("Invalid game type specified.")
                                .color(NamedTextColor.RED)
                );
            }
        }

        return true;
    }
}
