package org.nebulamc.plugin.features.wager;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.nebulamc.plugin.Nebula;
import org.nebulamc.plugin.features.wager.events.WagerGameEndEvent;
import org.nebulamc.plugin.utils.SavedInventory;

import javax.annotation.Nullable;
import java.util.UUID;

@Getter
public class Wager implements Listener {

    @Getter(AccessLevel.NONE)
    private static final Location HOST_SPAWNPOINT =
            new Location(
                    Bukkit.getWorld("admin"),
                    -377.5,
                    26,
                    -222.5
            );

    @Getter(AccessLevel.NONE)
    private static final Location TARGET_SPAWNPOINT =
            new Location(
                    Bukkit.getWorld("admin"),
                    -397.5,
                    26,
                    -222.5
            );

    private final String id;
    private final Player host;
    private final Player target;

    private Status status = Status.REQUESTED;

    @Setter
    @Nullable private ItemStack hostWager;
    @Setter
    @Nullable private ItemStack targetWager;

    public Wager(String id, Player h, Player t) {
        this.id = id;
        host = h;
        target = t;
    }

    public Audience audience() {
        return Audience.audience(host, target);
    }

    public void sendInvite() {
        host.sendMessage(
                Component.text("Building invite...")
                        .color(NamedTextColor.DARK_GRAY)
        );

        target.sendMessage(
            Component.text(host.getName() + " would like to wager with you!")
                .color(NamedTextColor.GOLD)
                .append(Component.newline())
                .append(
                    Component.text("[ACCEPT] ")
                        .color(NamedTextColor.GREEN)
                        .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/wager accept " + getId()))
                ).append(
                    Component.text("[DECLINE]")
                        .color(NamedTextColor.RED)
                        .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/wager decline " + getId()))
                )
        );

        host.sendMessage(
                Component.text("You sent a wager request to "+ target.getName() + ".")
                        .color(NamedTextColor.GOLD)
        );
    }

    public void promptForWageredItems() {
        setStatus(Status.WAITING_ON_ITEM);

        audience().sendMessage(
                Component.text("Put the item you'd like to wager in the first slot of your hotbar then click this message to continue.")
                        .color(NamedTextColor.GOLD)
                        .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/wager itemselect " + getId().toString()))
                        .hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, Component.text("Click to deposit item").color(NamedTextColor.AQUA)))
        );
    }

    public void confirmItemOrWait() {
        if (targetWager != null && hostWager != null) {
            confirmItem();
        }
    }

    public void confirmItem() {
        setStatus(Status.CONFIRMING_ITEM);
        target.sendMessage(
                Component.text(host.getName() + " has input " + hostWager.getAmount() + "x " + hostWager.getType() + " as their wager.")
                        .color(NamedTextColor.GOLD)
                        .append(Component.newline())
                        .append(
                                Component.text("[ACCEPT OFFER] ")
                                        .color(NamedTextColor.GREEN)
                                        .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/wager acceptoffer " + getId()))
                        ).append(
                                Component.text("[DECLINE OFFER]")
                                        .color(NamedTextColor.RED)
                                        .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/wager declineoffer " + getId()))
                        )
        );
        host.sendMessage(
                Component.text(target.getName() + " has input " + hostWager.getAmount() + "x " + hostWager.getType() + " as their wager.")
                        .color(NamedTextColor.GOLD)
                        .append(Component.newline())
                        .append(
                                Component.text("[ACCEPT OFFER] ")
                                        .color(NamedTextColor.GREEN)
                                        .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/wager acceptoffer " + getId()))
                        ).append(
                                Component.text("[DECLINE OFFER]")
                                        .color(NamedTextColor.RED)
                                        .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/wager declineoffer " + getId()))
                        )
        );
    }

    private boolean hostAccepted = false;
    private boolean targetAccepted = false;

    public void joinQueueOrWait(String type) {

        if (type.equals("host")) hostAccepted = true;
        if (type.equals("target")) targetAccepted = true;

        if (targetAccepted && hostAccepted) {
            joinQueue();
        }
    }

    public void joinQueue() {
        Nebula.getInstance().getWagerManager().queue.add(this);

        if (Nebula.getInstance().getWagerManager().queue.size() == 1) {
            startGameplay();
            audience().sendMessage(
                    Component.text("Empty queue, joining game...")
                            .color(NamedTextColor.DARK_GRAY)
            );
            Nebula.getInstance().getWagerManager().getInfoChannel().gameStart(this);
        } else {
            setStatus(Status.QUEUEING);
            audience().sendMessage(
                    Component.text("Joined the queue! You are in position " + Nebula.getInstance().getWagerManager().queue.indexOf(this) + ".")
                            .color(NamedTextColor.GREEN)
            );
        }
    }

    public void startGameplay() {
        setStatus(Status.IN_GAME);
        audience().sendMessage(
                Component.text("Teleporting you into the game...")
                        .color(NamedTextColor.DARK_GRAY)
        );

        Bukkit.getPluginManager().registerEvents(this, Nebula.getInstance());

        Nebula.getInstance().getWagerManager().getLastLocationCache().put(host, host.getLocation());
        Nebula.getInstance().getWagerManager().getLastLocationCache().put(target, target.getLocation());

        host.getInventory().remove(hostWager);
        target.getInventory().remove(targetWager);

        SavedInventory.save(host);
        SavedInventory.save(target);

        host.getInventory().clear();
        target.getInventory().clear();

        host.teleport(HOST_SPAWNPOINT);
        target.teleport(TARGET_SPAWNPOINT);

        new BukkitRunnable() {
            int runTimes = 5;
            @Override
            public void run() {
                if (runTimes == -1) {
                    audience().sendMessage(
                            Component.text("FIGHT!")
                                    .color(NamedTextColor.GREEN)
                                    .decorate(TextDecoration.BOLD)
                    );
                    cancel();
                }
                else {
                    audience().sendMessage(
                            Component.text("Starting in " + runTimes + "...")
                                    .color(NamedTextColor.GOLD)
                    );

                    runTimes--;
                }
            }
        }.runTaskTimer(Nebula.getInstance(), 0, 20);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if ((e.getPlayer() == host || e.getPlayer() == target) && getStatus() == Status.IN_GAME) {
            WagerGameEndEvent ev = new WagerGameEndEvent(this, e.getPlayer());
            Bukkit.getPluginManager().callEvent(ev);
        }
    }

    public void onEnd(Player winner) {
        setStatus(Status.ENDED);
        winner.getInventory().addItem(hostWager, targetWager);


        audience().sendMessage(
            Component.text(winner.getName() + " is the winner!")
                    .color(NamedTextColor.GOLD)
        );

        SavedInventory.get(host).apply(host);
        Location hl = Nebula.getInstance().getWagerManager().getLastLocationCache().get(host);
        host.teleport(hl);

        SavedInventory.get(target).apply(target);
        Location tl = Nebula.getInstance().getWagerManager().getLastLocationCache().get(target);
        target.teleport(tl);
    }

    public void forceEnd(Player admin) {
        audience().sendMessage(
                Component.text("An admin has force-ended your wager.")
                        .color(NamedTextColor.RED)
        );

        setStatus(Status.FORCE_ENDED);
        Nebula.getInstance().getWagerManager().activeWagers.remove(getId());
        Nebula.getInstance().getWagerManager().queue.remove(this);
    }

    public void setStatus(Status s) {
        Nebula.getInstance().getWagerManager().getInfoChannel().stateChange(this);
        status = s;
    }

    public enum Status {
        REQUESTED,
        WAITING_ON_ITEM,
        CONFIRMING_ITEM,
        QUEUEING,
        IN_GAME,
        ENDED,
        FORCE_ENDED,
        DECLINED
    }

}
