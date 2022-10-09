package org.nebulamc.plugin.hooks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ExcellentCratesHook implements Hook {

    @Override
    public String getPluginId() {
        return "ExcellentCrates";
    }

    public void giveAdminEventCrate(Player p) {
        Bukkit.getServer().dispatchCommand(
                Bukkit.getConsoleSender(),
                "excellentcrates give " + p.getName() + " [crate_name] 1"
        );

        // send message confirming
    }

}
