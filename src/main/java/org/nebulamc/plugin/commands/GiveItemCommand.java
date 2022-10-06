package org.nebulamc.plugin.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.nebulamc.plugin.Nebula;
import org.nebulamc.plugin.features.customitems.ItemManager;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GiveItemCommand implements CommandExecutor, TabCompleter {

    private static List<String> arguments = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command is for players only.");
            return true;
        }
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("giveitem")){
            if (args.length == 0){
                sender.sendMessage(
                        Component.text("Proper usage: /giveitem <player> <item>").color(NamedTextColor.RED));
                return true;
            }
            if (args.length >= 0 && Bukkit.getPlayer(args[0]) != null){
                if(ItemManager.items.containsKey(args[1])){
                    ItemStack item = ItemManager.items.get(args[1]).getItem();
                    player.getInventory().addItem(item);
                } else {
                    sender.sendMessage(
                            Component.text("That is not a valid item name!").color(NamedTextColor.RED));
                }
            }
            else {
                sender.sendMessage(
                        Component.text("That is not a valid player!").color(NamedTextColor.RED));
            }
        }

        return true;
    }

    private static void createArguments(){
        for (String s : ItemManager.items.keySet()) {
            arguments.add(s);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (arguments.isEmpty()){
            createArguments();
        }

        List<String> result = new ArrayList<>();

        if (args.length == 2){
            for (String a : arguments) {
                if (a.toLowerCase().startsWith(args[1].toLowerCase())){
                    result.add(a);
                }
            }
            return result;
        }
        return null;
    }

}
