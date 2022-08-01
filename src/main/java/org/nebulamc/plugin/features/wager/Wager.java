package org.nebulamc.plugin.features.wager;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.nebulamc.plugin.Nebula;
import org.nebulamc.plugin.features.wager.events.WagerGameEndEvent;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Getter
public class Wager implements Listener {

    private static final Location HOST_SPAWNPOINT =
            new Location(
                    Bukkit.getWorld("admin"),
                    -377.5,
                    26,
                    -222.5
            );

    private static final Location TARGET_SPAWNPOINT =
            new Location(
                    Bukkit.getWorld("admin"),
                    -397.5,
                    26,
                    -222.5
            );

    private final UUID uuid;
    private final Player host;
    private final Player target;

    @Setter
    private Status status = Status.REQUESTED;

    @Nullable private ItemStack hostWager;
    @Nullable private ItemStack targetWager;

    public Wager(UUID u, Player h, Player t) {
        uuid = u;
        host = h;
        target = t;
        sendInvite();
    }

    public Audience audience() {
        return Audience.audience(host, target);
    }

    public void onEnd(Player winner) {
        setStatus(Status.ENDED);
        winner.getInventory().addItem(hostWager, targetWager);
    }

    private void sendInvite() {

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
                        .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/wager accept " + uuid))
                ).append(
                    Component.text("[DECLINE]")
                        .color(NamedTextColor.RED)
                        .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/wager decline " + uuid))
                )
        );

        host.sendMessage(
                Component.text("You sent a wager request to "+ target.getName() + ".")
                        .color(NamedTextColor.GOLD)
        );
    }

    public CompletableFuture<Void> promptForWageredItems() {
        setStatus(Status.WAITING_ON_ITEM);

        ChestGui gui = new ChestGui(1, "Input an item!");

        // Left border
        OutlinePane border = new OutlinePane(0, 0, 4, 1);
        border.addItem(
            new GuiItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE))
        );
        border.setRepeat(true);
        gui.addPane(border);

        // Right border
        border = new OutlinePane(5, 0, 4, 1);
        border.addItem(
                new GuiItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE))
        );
        border.setRepeat(true);
        gui.addPane(border);

        StaticPane center = new StaticPane(0, 4, 1, 1);
        center.setOnClick(ev -> {
            if (ev.getWhoClicked() == host) {
                hostWager = ev.getCurrentItem();
            } else targetWager = ev.getCurrentItem();
            ev.getWhoClicked().closeInventory();
        });

        audience().sendMessage(
                Component.text("Storing wagered items...")
                        .color(NamedTextColor.DARK_GRAY)
        );

        return new CompletableFuture<>();
    }

    public void startGameplay() {
        setStatus(Status.IN_GAME);
        audience().sendMessage(
                Component.text("Teleporting you into the game...")
                        .color(NamedTextColor.DARK_GRAY)
        );

        host.teleport(HOST_SPAWNPOINT);
        target.teleport(TARGET_SPAWNPOINT);

        Bukkit.getPluginManager().registerEvents(this, Nebula.getInstance());

        new BukkitRunnable() {
            int runTimes = 4;
            @Override
            public void run() {
                if (runTimes == 1) {
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
        }.runTaskTimer(Nebula.getInstance(), 20, 0);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if ((e.getPlayer() == host || e.getPlayer() == target) && getStatus() != Status.ENDED) {
            WagerGameEndEvent ev = new WagerGameEndEvent(this, e.getPlayer());
            Bukkit.getPluginManager().callEvent(ev);
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
        } else {
            setStatus(Status.QUEUEING);
            audience().sendMessage(
                    Component.text("Joined the queue! You are in position " + Nebula.getInstance().getWagerManager().queue.indexOf(this) + ".")
                            .color(NamedTextColor.GREEN)
            );
        }
    }

    public enum Status {
        REQUESTED,
        WAITING_ON_ITEM,
        QUEUEING,
        IN_GAME,
        ENDED,
        DECLINED
    }

}
