package org.nebulamc.plugin.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.nebulamc.plugin.Nebula;
import org.nebulamc.plugin.utils.mineshafts.SpawnMineshafts;

import java.util.logging.Logger;


public class SpawnMineshaftsCommand implements CommandExecutor {

    private static final Nebula plugin = Nebula.getInstance();
    private static final Logger log = plugin.getLogger();

    private static final double MINESHAFT_RARITY = 0.008; //chance per chunk to spawn a mineshaft

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            SpawnMineshafts.SpawnMineshafts((Player) sender, MINESHAFT_RARITY);

        } else {
            sender.sendMessage(
                Component.text("This command cannot be used in console!")
                        .color(NamedTextColor.RED)
            );
        }

        return true;
    }
}
