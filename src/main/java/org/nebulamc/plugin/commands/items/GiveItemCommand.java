package org.nebulamc.plugin.commands.items;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.nebulamc.plugin.Nebula;
import org.nebulamc.plugin.features.customitems.ItemManager;

import java.util.logging.Logger;

public class GiveItemCommand implements CommandExecutor {

    private static final Logger log = Nebula.getInstance().getLogger();

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
}
