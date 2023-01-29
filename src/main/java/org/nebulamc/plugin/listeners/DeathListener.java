package org.nebulamc.plugin.listeners;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.nebulamc.plugin.Nebula;

public class DeathListener implements Listener {

    static Nebula plugin;

    public DeathListener(Nebula main){
        plugin = main;
    }

    Economy econ = plugin.getEconomy();

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        double balanceTaken = econ.getBalance(player)/10;
        if (balanceTaken > 100){
            balanceTaken = 100;
        }
        econ.withdrawPlayer(player, balanceTaken);
        player.sendMessage("");
        player.sendMessage(ChatColor.DARK_RED + "☠ You died and lost " +  ChatColor.BOLD + balanceTaken + " gold");
        player.sendMessage("");
    }
}
