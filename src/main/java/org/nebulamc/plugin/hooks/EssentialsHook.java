package org.nebulamc.plugin.hooks;

import com.earth2me.essentials.IEssentials;
import org.bukkit.Bukkit;

public class EssentialsHook implements Hook {

    public IEssentials esxInstance;

    @Override
    public String getPluginId() {
        return "Essentials";
    }

    public IEssentials hook() {
        esxInstance = (IEssentials) Bukkit.getPluginManager().getPlugin("Essentials");
        return esxInstance;
    }

}
