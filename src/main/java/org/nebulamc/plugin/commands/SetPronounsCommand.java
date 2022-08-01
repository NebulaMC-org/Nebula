package org.nebulamc.plugin.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.nebulamc.plugin.Nebula;

public class SetPronounsCommand implements CommandExecutor {

    private static Nebula plugin;
    public SetPronounsCommand(Nebula main){
        plugin = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player player) {
            player.sendMessage(
                    Component.text("This command is coming soon!")
                            .color(NamedTextColor.RED)
            );
        }
        return true;
    }
}
