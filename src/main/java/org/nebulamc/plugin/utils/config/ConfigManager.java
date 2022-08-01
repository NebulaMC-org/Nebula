package org.nebulamc.plugin.utils.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.nebulamc.plugin.Nebula;

import java.io.File;

public class ConfigManager {

    private static Nebula main;

    private File config_file;

    private FileConfiguration config;

    public ConfigManager(Nebula main) {
        ConfigManager.main = main;
    }

    public void createDefaults() {
        createFiles();
        addDefault(this.config, "settings.afksystem.enable", Boolean.valueOf(true));
        addDefault(this.config, "settings.chatping.enable",  Boolean.valueOf(true));
        addDefault(this.config, "settings.smeltingpatch.enable",  Boolean.valueOf(true));
        addDefault(this.config, "database.MySQL.host",  "localhost");
        addDefault(this.config, "database.MySQL.port",  "3306");
        addDefault(this.config, "database.MySQL.database",  "server");
        addDefault(this.config, "database.MySQL.user",  "user123");
        addDefault(this.config, "database.MySQL.pass",  "pass123");
        saveConfig();
        reloadConfigs();
    }

    private void createFiles() {
        this.config_file = new File(main.getDataFolder(), "config.yml");
        if (!this.config_file.exists()) {
            this.config_file.getParentFile().mkdirs();
            main.saveResource("config.yml", false);
        }
        this.config = (FileConfiguration)new YamlConfiguration();
        try {
            this.config.load(this.config_file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addDefault(FileConfiguration cfg, String path, Object value) {
        cfg.addDefault(path, value);
    }

    public void reloadConfigs() {
        try {
            this.config.load(this.config_file);
            loadConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveConfig() {
        try {
            this.config.save(this.config_file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadConfig() {
        System.out.println("Loading config");
        ConfigSettings.afksystem_enable = this.config.getBoolean("settings.afksystem.enable");
        ConfigSettings.chatping_enable = this.config.getBoolean("settings.chatping.enable");
        ConfigSettings.smeltingpatch_enable = this.config.getBoolean("settings.smeltingpatch.enable");
        ConfigSettings.host = this.config.getString("database.MySQL.host");
        ConfigSettings.host = this.config.getString("database.MySQL.port");
        ConfigSettings.host = this.config.getString("database.MySQL.database");
        ConfigSettings.host = this.config.getString("database.MySQL.user");
        ConfigSettings.host = this.config.getString("database.MySQL.pass");
    }
}
