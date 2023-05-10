package org.nebulamc.plugin.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.nebulamc.plugin.utils.Utils;

public class AdminRenameCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command is for players only.");
            return true;
        }
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("arename")) {
            if (args.length == 0) {
                sender.sendMessage(
                        Component.text("Proper usage: /arename <name>").color(NamedTextColor.RED));
                return true;
            }

            String displayName = args[0];
            for (int i = 1; i < args.length; i++){
                displayName = (displayName + " " + args[i]);
            }

            ItemStack item = player.getInventory().getItemInMainHand();
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(Utils.colorize(displayName));
            item.setItemMeta(meta);

            sender.sendMessage(
                    Component.text("Set item name to ").color(NamedTextColor.GREEN).append(Component.text(Utils.colorize(displayName))));
        }
        return true;
    }
}
