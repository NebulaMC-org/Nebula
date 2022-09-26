package org.nebulamc.plugin.commands.items;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.nebulamc.plugin.features.customitems.ItemManager;

import java.util.ArrayList;
import java.util.List;

public class GiveItemTabCompleter implements TabCompleter {

    public static List<String> arguments = new ArrayList<>();

    public static void createArguments(){
        ItemManager.items.keySet().addAll(arguments);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args){
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
