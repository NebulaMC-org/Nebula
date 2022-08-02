package org.nebulamc.plugin.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.nebulamc.plugin.Nebula;
import org.nebulamc.plugin.features.wager.HomosexWager;
import org.nebulamc.plugin.features.wager.Wager;
import org.nebulamc.plugin.features.wager.events.WagerAcceptEvent;
import org.nebulamc.plugin.features.wager.events.WagerDeclineEvent;

import java.util.*;

public class WagerCommand implements CommandExecutor {

    private static final Component ADMIN_PREFIX = Component.text("ADMIN ⋙ ").color(NamedTextColor.DARK_RED).decorate(TextDecoration.BOLD);
    private static final Component SEPARATOR = Component.text("• • • • • • • • • • • • • • • • • • • • • • • • • • • • • • • • • • •").color(NamedTextColor.DARK_GRAY);

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
                    case "itemselect" -> {
                        if (args.length == 2) {
                            try {
                                UUID u = UUID.fromString(args[1]);
                                Wager w = Nebula.getInstance().getWagerManager().activeWagers.get(u);
                                if (w == null) {
                                    player.sendMessage(
                                            Component.text("That wager doesn't exist!").color(NamedTextColor.RED)
                                    );
                                } else {
                                    if (sender == w.getTarget()) {
                                        w.setTargetWager(((Player) sender).getInventory().getItem(0));
                                        w.confirmItemOrWait();
                                        player.sendMessage(
                                                Component.text("Deposited your item! Your opponent will need to approve it before the game can start.").color(NamedTextColor.GOLD)
                                        );
                                    } else if (sender == w.getHost()) {
                                        w.setHostWager(((Player) sender).getInventory().getItem(0));
                                        w.confirmItemOrWait();
                                        player.sendMessage(
                                                Component.text("Deposited your item! Your opponent will need to approve it before the game can start.").color(NamedTextColor.GOLD)
                                        );
                                    } else {
                                        player.sendMessage(
                                                Component.text("You're not taking part in this wager!").color(NamedTextColor.RED)
                                        );
                                    }
                                }
                            } catch (IllegalArgumentException e) {
                                player.sendMessage(
                                        Component.text("That isn't a valid wager ID!").color(NamedTextColor.RED)
                                );
                            }
                        }
                    }
                    case "acceptoffer" -> {
                        try {
                            UUID u = UUID.fromString(args[1]);
                            Wager w = Nebula.getInstance().getWagerManager().activeWagers.get(u);
                            if (w == null) {
                                player.sendMessage(
                                        Component.text("That wager doesn't exist!").color(NamedTextColor.RED)
                                );
                            } else {
                                if (sender == w.getTarget()) {
                                    w.joinQueueOrWait("target");
                                    player.sendMessage(
                                            Component.text("Confirmed.").color(NamedTextColor.GOLD)
                                    );
                                } else if (sender == w.getHost()) {
                                    w.joinQueueOrWait("host");
                                    player.sendMessage(
                                            Component.text("Confirmed.").color(NamedTextColor.GOLD)
                                    );
                                } else {
                                    player.sendMessage(
                                            Component.text("You're not taking part in this wager!").color(NamedTextColor.RED)
                                    );
                                }
                            }
                        } catch (IllegalArgumentException e) {
                            player.sendMessage(
                                    Component.text("That isn't a valid wager ID!").color(NamedTextColor.RED)
                            );
                        }
                    }
                    case "declineoffer" -> {
                        try {
                            UUID u = UUID.fromString(args[1]);
                            Wager w = Nebula.getInstance().getWagerManager().activeWagers.get(u);
                            if (w == null) {
                                player.sendMessage(
                                        Component.text("That wager doesn't exist!").color(NamedTextColor.RED)
                                );
                            } else {
                                if (sender == w.getTarget()) {
                                    player.sendMessage(
                                            Component.text("Declined.").color(NamedTextColor.GOLD)
                                    );
                                    w.setStatus(Wager.Status.ENDED);
                                    w.getHost().sendMessage(
                                            Component.text(w.getTarget().getName() + " declined your offer.").color(NamedTextColor.RED)
                                    );
                                    Nebula.getInstance().getWagerManager().activeWagers.remove(u);
                                } else if (sender == w.getHost()) {
                                    player.sendMessage(
                                            Component.text("Declined.").color(NamedTextColor.GOLD)
                                    );
                                    w.setStatus(Wager.Status.ENDED);
                                    w.getTarget().sendMessage(
                                            Component.text(w.getHost().getName() + " declined your offer.").color(NamedTextColor.RED)
                                    );
                                    Nebula.getInstance().getWagerManager().activeWagers.remove(u);
                                } else {
                                    player.sendMessage(
                                            Component.text("You're not taking part in this wager!").color(NamedTextColor.RED)
                                    );
                                }
                            }
                        } catch (IllegalArgumentException e) {
                            player.sendMessage(
                                    Component.text("That isn't a valid wager ID!").color(NamedTextColor.RED)
                            );
                        }
                    }
                    case "admin" -> {
                        if (sender.hasPermission("wagers.admin")) {
                            if (args.length >= 3) {
                                switch (args[2]) {
                                    case "panel" -> {
                                        LinkedList<Wager> queuedWagers = Nebula.getInstance().getWagerManager().queue;
                                        Collection<Wager> activeWagers = Nebula.getInstance().getWagerManager().activeWagers.values();

                                        Wager activeGame = queuedWagers.element();

                                        if (activeGame.getStatus() != Wager.Status.IN_GAME) {
                                            activeGame = null;
                                        }

                                        player.sendMessage(SEPARATOR);
                                        player.sendMessage(ADMIN_PREFIX.append(
                                            Component.text("Panel snapshot taken at " + new Date()).color(NamedTextColor.DARK_GRAY).append(Component.newline())
                                        ));
                                        player.sendMessage(ADMIN_PREFIX.append(
                                            Component.text("Active wagers (includes non-queued): ").color(NamedTextColor.RED)
                                                    .append(Component.text(activeWagers.size()).color(NamedTextColor.GOLD))
                                                    .clickEvent(ClickEvent.runCommand("/wager admin list active"))
                                                    .hoverEvent(HoverEvent.showText(Component.text("Click to view").color(NamedTextColor.GOLD)))
                                                    .append(Component.newline())
                                        ));
                                        player.sendMessage(ADMIN_PREFIX.append(
                                            Component.text("Queued wagers: ").color(NamedTextColor.RED)
                                                    .append(Component.text(queuedWagers.size()).color(NamedTextColor.GOLD))
                                                    .clickEvent(ClickEvent.runCommand("/wager admin list queued"))
                                                    .hoverEvent(HoverEvent.showText(Component.text("Click to view").color(NamedTextColor.GOLD)))
                                                    .append(Component.newline())
                                        ));

                                        Component cag = Component.text("Current active game: ").color(NamedTextColor.RED)
                                                    .append(Component.text(
                                                            activeGame == null ? "None" : activeGame.getHost().getName() + " (host) vs " + activeGame.getTarget().getName()
                                                    ).color(NamedTextColor.GOLD));
                                        if (activeGame != null)
                                            cag.clickEvent(ClickEvent.runCommand("/wager admin info " + activeGame.getUuid()))
                                               .hoverEvent(HoverEvent.showText(Component.text("Click to view").color(NamedTextColor.GOLD)));

                                        cag.append(Component.newline());

                                        player.sendMessage(ADMIN_PREFIX.append(cag));
                                        player.sendMessage(SEPARATOR);

                                    }
                                    case "info" -> {
                                        if (args.length == 4) {
                                            try {
                                                UUID uuid = UUID.fromString(args[3]);
                                                Wager w = Nebula.getInstance().getWagerManager().activeWagers.get(uuid);

                                                if (w != null) {
                                                    player.sendMessage(SEPARATOR.append(Component.newline()));

                                                    player.sendMessage(ADMIN_PREFIX.append(
                                                            Component.text("UUID: ").color(NamedTextColor.RED)
                                                                    .append(Component.text(w.getUuid().toString()).color(NamedTextColor.GOLD))
                                                                    .append(Component.newline())
                                                    ));

                                                    player.sendMessage(ADMIN_PREFIX.append(
                                                            Component.text("Host: ").color(NamedTextColor.RED)
                                                                    .append(Component.text(w.getHost().getName()).color(NamedTextColor.GOLD))
                                                                    .append(Component.newline())
                                                    ));

                                                    player.sendMessage(ADMIN_PREFIX.append(
                                                            Component.text("Target: ").color(NamedTextColor.RED)
                                                                    .append(Component.text(w.getTarget().getName()).color(NamedTextColor.GOLD))
                                                                    .append(Component.newline())
                                                    ));

                                                    player.sendMessage(ADMIN_PREFIX.append(
                                                            Component.text("Type: ").color(NamedTextColor.RED)
                                                                    .append(Component.text(
                                                                            w instanceof HomosexWager
                                                                                    ? "HOMOSEX"
                                                                                    : "DEFAULT"
                                                                    ).color(NamedTextColor.GOLD))
                                                                    .append(Component.newline())
                                                    ));

                                                    player.sendMessage(ADMIN_PREFIX.append(
                                                            Component.text("State: ").color(NamedTextColor.RED)
                                                                    .append(Component.text(w.getStatus().toString()).color(NamedTextColor.GOLD))
                                                                    .append(Component.newline())
                                                    ));

                                                    player.sendMessage(SEPARATOR.append(Component.newline()));

                                                    player.sendMessage(ADMIN_PREFIX.append(
                                                            Component.text("Host wager: ").color(NamedTextColor.RED)
                                                                    .append(Component.newline())
                                                                    .append(Component.text(w.getTargetWager().getAmount() + "x " + w.getTargetWager().getType()).color(NamedTextColor.GOLD))
                                                                    .append(Component.newline())
                                                    ));

                                                    player.sendMessage(ADMIN_PREFIX.append(
                                                            Component.text("Target wager: ").color(NamedTextColor.RED)
                                                                    .append(Component.newline())
                                                                    .append(Component.text(w.getTargetWager().getAmount() + "x " + w.getTargetWager().getType()).color(NamedTextColor.GOLD))
                                                                    .append(Component.newline())
                                                    ));

                                                    player.sendMessage(SEPARATOR.append(Component.newline()));
                                                } else {
                                                    player.sendMessage(
                                                            ADMIN_PREFIX.append(
                                                                    Component.text("Wager could not be found in registry.").color(NamedTextColor.RED)
                                                            )
                                                    );
                                                }
                                            } catch (IllegalArgumentException e) {
                                                player.sendMessage(
                                                        ADMIN_PREFIX.append(
                                                                Component.text("Argument \"uuid\" was invalid and could not be parsed.").color(NamedTextColor.RED)
                                                        )
                                                );
                                            }
                                        } else {
                                            player.sendMessage(
                                                    ADMIN_PREFIX.append(
                                                            Component.text("Missing argument \"uuid\" of type \"string\".").color(NamedTextColor.RED)
                                                    )
                                            );
                                        }

                                    }
                                    case "list" -> {
                                        if (args.length == 4) {
                                            if (args[3].equals("active")) {
                                                Collection<Wager> activeWagers = Nebula.getInstance().getWagerManager().activeWagers.values();

                                                player.sendMessage(SEPARATOR);
                                                player.sendMessage(ADMIN_PREFIX.append(
                                                        Component.text("Active wagers snapshot taken at " + new Date()).color(NamedTextColor.DARK_GRAY).append(Component.newline())
                                                ));
                                                player.sendMessage(ADMIN_PREFIX.append(
                                                        Component.text("Total queued wagers: ").color(NamedTextColor.RED)
                                                                .append(Component.text(activeWagers.size()).color(NamedTextColor.GOLD))
                                                                .clickEvent(ClickEvent.runCommand("/wager admin list active"))
                                                                .hoverEvent(HoverEvent.showText(Component.text("Click to view").color(NamedTextColor.GOLD)))
                                                                .append(Component.newline())
                                                ));

                                                for (Wager w : activeWagers) {
                                                    Component c = ADMIN_PREFIX.append(
                                                            Component.text(w.getHost().getName() + " (host) vs " + w.getTarget().getName()).color(NamedTextColor.GOLD)
                                                                    .append(Component.text( " (" + w.getStatus() + ")").color(NamedTextColor.GREEN))
                                                                    .clickEvent(ClickEvent.runCommand("/wager admin info " + w.getUuid()))
                                                    );

                                                    if (w.getStatus() == Wager.Status.IN_GAME) {
                                                        c.append(Component.text( " (active)").color(NamedTextColor.GREEN));
                                                    }

                                                    player.sendMessage(c);
                                                }

                                                player.sendMessage(SEPARATOR);
                                            } else if (args[3].equals("queued")) {
                                                LinkedList<Wager> queuedWagers = Nebula.getInstance().getWagerManager().queue;

                                                player.sendMessage(SEPARATOR);
                                                player.sendMessage(ADMIN_PREFIX.append(
                                                        Component.text("Queue snapshot taken at " + new Date()).color(NamedTextColor.DARK_GRAY).append(Component.newline())
                                                ));
                                                player.sendMessage(ADMIN_PREFIX.append(
                                                        Component.text("Total queued wagers: ").color(NamedTextColor.RED)
                                                                .append(Component.text(queuedWagers.size()).color(NamedTextColor.GOLD))
                                                                .clickEvent(ClickEvent.runCommand("/wager admin list active"))
                                                                .hoverEvent(HoverEvent.showText(Component.text("Click to view").color(NamedTextColor.GOLD)))
                                                                .append(Component.newline())
                                                ));

                                                for (Wager w : queuedWagers) {
                                                    Component c = ADMIN_PREFIX.append(
                                                            Component.text(w.getHost().getName() + " (host) vs " + w.getTarget().getName()).color(NamedTextColor.GOLD)
                                                                    .clickEvent(ClickEvent.runCommand("/wager admin info " + w.getUuid()))
                                                    );

                                                    if (w.getStatus() == Wager.Status.IN_GAME) {
                                                        c.append(Component.text( " (active)").color(NamedTextColor.GREEN));
                                                    }

                                                    player.sendMessage(c);
                                                }

                                                player.sendMessage(SEPARATOR);
                                            } else
                                                player.sendMessage(
                                                        ADMIN_PREFIX.append(
                                                                Component.text("Unknown argument \"" + args[3] +  "\", expected \"active\" or \"queued\".").color(NamedTextColor.RED)
                                                        )
                                                );
                                        } else {
                                            player.sendMessage(
                                                    ADMIN_PREFIX.append(
                                                            Component.text("Missing argument \"category\" of type \"string\".").color(NamedTextColor.RED)
                                                    )
                                            );
                                        }
                                    }
                                    case "end" -> {
                                        try {
                                            UUID uuid = UUID.fromString(args[3]);
                                            Wager w = Nebula.getInstance().getWagerManager().activeWagers.get(uuid);

                                            if (w != null) {
                                                player.sendMessage(SEPARATOR.append(Component.newline()));

                                                player.sendMessage(ADMIN_PREFIX.append(
                                                        Component.text("Force-ended wager #" + w.getUuid()).color(NamedTextColor.GOLD)
                                                ));

                                                player.sendMessage(SEPARATOR.append(Component.newline()));
                                            } else {
                                                player.sendMessage(
                                                        ADMIN_PREFIX.append(
                                                                Component.text("Wager could not be found in registry.").color(NamedTextColor.RED)
                                                        )
                                                );
                                            }
                                        } catch (IllegalArgumentException e) {
                                            player.sendMessage(
                                                    ADMIN_PREFIX.append(
                                                            Component.text("Argument \"uuid\" was invalid and could not be parsed.").color(NamedTextColor.RED)
                                                    )
                                            );
                                        }
                                    }
                                    default -> player.sendMessage(
                                            ADMIN_PREFIX.append(
                                                    Component.text("Unknown argument \"" + args[2] + "\", expected \"panel\", \"list\", \"info\" or \"end\".").color(NamedTextColor.RED)
                                            )
                                    );
                                }
                            } else {
                                player.sendMessage(
                                        ADMIN_PREFIX.append(
                                                Component.text("Unknown argument \"\", expected \"panel\", \"list\", \"info\" or \"end\".").color(NamedTextColor.RED)
                                        )
                                );
                            }
                        } else {
                            player.sendMessage(
                                    Component.text("You can't run this command!").color(NamedTextColor.RED)
                            );
                        }
                    }
                    default -> {
                        Player p = Bukkit.getPlayer(args[0]);
                        if (p == null) {
                            player.sendMessage(
                                    Component.text("That player is offline!").color(NamedTextColor.RED)
                            );
                        } else {
                            if (label.equals("homosex")) {
                                Nebula.getInstance().getWagerManager().createHomosexWager(player, p);
                            } else
                                Nebula.getInstance().getWagerManager().createWager(player, p);
                        }
                    }
                }
            } else {
                if (label.equals("homosex")) {
                    player.sendMessage(
                            Component.text("You need to specify a player to have homosex with!").color(NamedTextColor.RED)
                    );
                } else player.sendMessage(
                        Component.text("You need to specify a player to wager with!").color(NamedTextColor.RED)
                );
            }
        }

        return true;
    }

}
