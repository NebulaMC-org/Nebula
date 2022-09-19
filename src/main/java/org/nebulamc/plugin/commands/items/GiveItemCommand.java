package org.nebulamc.plugin.commands.items;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.nebulamc.plugin.features.items.ItemManager;

public class GiveItemCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command is for players only.");
        }
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("giveitem")){
            if (Bukkit.getPlayer(args[0]) != null){
                if(ItemManager.items.contains(args[1])){
                    ItemStack item = ItemManager.items.get(ItemManager.items.indexOf(args[1]));
                    player.getInventory().addItem(item);
                } else {
                    sender.sendMessage("That is not a valid item name.");
                }
            }
            else {
                sender.sendMessage("That is not a valid player.");
            }
        }

        return true;
    }
}
