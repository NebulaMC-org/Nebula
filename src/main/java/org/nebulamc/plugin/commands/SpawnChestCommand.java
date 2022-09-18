package org.nebulamc.plugin.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.nebulamc.plugin.Nebula;

import java.util.logging.Logger;

import static org.bukkit.Sound.AMBIENT_BASALT_DELTAS_MOOD;

public class SpawnChestCommand implements CommandExecutor {

    private static final Nebula plugin = Nebula.getInstance();
    private static final Logger log = plugin.getLogger();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            World w = Bukkit.getWorld("world");
            double border = w.getWorldBorder().getSize();
            int loc_x;
            int loc_z;
            int maxTries = 10;

            while (maxTries > 0) {
                loc_x = (int) (Math.random() * border - border / 2);
                loc_z = (int) (Math.random() * border - border / 2);
                if (plugin.getLandsIntegration().getLand(w, loc_x / 16, loc_z / 16) == null) {
                    int loc_y = w.getHighestBlockYAt(loc_x, loc_z) + 1;
                    Location loc = new Location(w, loc_x, loc_y, loc_z);

                    Block block = loc.getBlock();
                    block.setType(Material.CHEST);
                    loc.add(0, -1, 0).getBlock().setType(Material.GILDED_BLACKSTONE);

                    Chest chest = (Chest) block.getState();
                    Inventory chestInv = chest.getBlockInventory();

                    if (!(loc).getChunk().isLoaded()) {
                        loc.getChunk().load();
                    }

                    for (int i = 0; i < chestInv.getSize(); i++) {
                        chestInv.setItem(i, plugin.getMeteorLoot().getRandom());
                    }

                    log.info("Spawned a chest at " + loc);
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendMessage("");
                        player.sendMessage(ChatColor.GOLD + "A loot chest has fallen at " + loc_x + ", " + loc_y + ", " + loc_z + "!");
                        player.sendMessage("");
                        player.playSound(loc, AMBIENT_BASALT_DELTAS_MOOD, 10000, 1);
                    }

                    return true;
                }
                maxTries--;
            }
            log.info("Could not find a suitable chest spawn location.");
        } else {
            sender.sendMessage(
                Component.text("This command is for internal use only!")
                        .color(NamedTextColor.RED)
            );
        }

        return true;
    }
}
