package spectromeda.nebula;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import spectromeda.nebula.events.AfkChange;
import spectromeda.nebula.events.Chat;
import spectromeda.nebula.utils.ConfigManager;
import spectromeda.nebula.utils.ConfigSettings;

import java.util.logging.Logger;

public final class Nebula extends JavaPlugin {
    private static final Logger log = Bukkit.getLogger();

    private ConfigManager configManager;

    public ConfigManager getConfigManager() {
        return this.configManager;
    }

    PluginManager pm = this.getServer().getPluginManager();

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.configManager = new ConfigManager(this);
        configManager.createDefaults();

        if (ConfigSettings.afksystem_enable == true) {
            pm.registerEvents(new AfkChange(this), this);
        }
        if (ConfigSettings.chatping_enable == true) {
            pm.registerEvents(new Chat(this), this);
        }

    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll();
    }
    //test

}
