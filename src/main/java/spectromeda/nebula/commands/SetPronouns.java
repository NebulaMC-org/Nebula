package spectromeda.nebula.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import spectromeda.nebula.Nebula;

import java.util.logging.Logger;

public class SetPronouns implements Listener {
    private static final Logger log = Bukkit.getLogger();

    static Nebula plugin;

    public SetPronouns(Nebula main){
        plugin = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {

            Player player = (Player) sender;

            if (args.length > 0){
                if (args[0].length() <= 12){

                } else {
                    player.sendMessage(ChatColor.RED + "Please keep your input under 12 characters.");
                }
            } else {
                player.sendMessage(ChatColor.RED + "Incorrect usage! /setpronouns <text>");
            }
        }
        return true;
    }
}
