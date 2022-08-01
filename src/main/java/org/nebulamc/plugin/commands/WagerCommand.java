package org.nebulamc.plugin.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.nebulamc.plugin.Nebula;
import org.nebulamc.plugin.features.wager.Wager;
import org.nebulamc.plugin.features.wager.events.WagerAcceptEvent;
import org.nebulamc.plugin.features.wager.events.WagerDeclineEvent;

import java.util.UUID;

public class WagerCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player player) {
            if (args.length > 0) {
                switch (args[0]) {
                    case "accept" -> {
                        if (args.length == 2) {
                            try {
                                UUID u = UUID.fromString(args[1]);
                                Wager w = Nebula.getInstance().getWagerManager().activeWagers.get(u);
                                if (w == null) {
                                    player.sendMessage(
                                            Component.text("That wager doesn't exist!").color(NamedTextColor.RED)
                                    );
                                } else {
                                    WagerAcceptEvent ev = new WagerAcceptEvent(w, player);
                                    Bukkit.getPluginManager().callEvent(ev);
                                }
                            } catch (IllegalArgumentException e) {
                                player.sendMessage(
                                        Component.text("That isn't a valid wager ID!").color(NamedTextColor.RED)
                                );
                            }
                        }
                    }
                    case "decline" -> {
                        if (args.length == 2) {
                            try {
                                UUID u = UUID.fromString(args[1]);
                                Wager w = Nebula.getInstance().getWagerManager().activeWagers.get(u);
                                if (w == null) {
                                    player.sendMessage(
                                            Component.text("That wager doesn't exist!").color(NamedTextColor.RED)
                                    );
                                } else {
                                    WagerDeclineEvent ev = new WagerDeclineEvent(w, player);
                                    Bukkit.getPluginManager().callEvent(ev);
                                }
                            } catch (IllegalArgumentException e) {
                                player.sendMessage(
                                        Component.text("That isn't a valid wager ID!").color(NamedTextColor.RED)
                                );
                            }
                        }
                    }
                    default -> {
                        Player p = Bukkit.getPlayer(args[0]);
                        if (p == null) {
                            player.sendMessage(
                                    Component.text("That player is offline!").color(NamedTextColor.RED)
                            );
                        } else {
                            Nebula.getInstance().getWagerManager().createWager(player, p);
                        }
                    }
                }
            } else {
                player.sendMessage(
                        Component.text("You need to specify a player to wager with!").color(NamedTextColor.RED)
                );
            }
        }

        return true;
    }

}
