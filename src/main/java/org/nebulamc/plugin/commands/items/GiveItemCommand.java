package org.nebulamc.plugin.commands.items;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.nebulamc.plugin.features.items.ItemManager;

public class GiveItemCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command is for players only.");
        }
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("giveitem")){
            try {
                player.getInventory().addItem(ItemManager.boomStick);
            } catch (IllegalArgumentException e){
                sender.sendMessage("That is not a valid item!");
            }
        }

        return true;
    }
}
