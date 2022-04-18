package spectromeda.nebula.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import spectromeda.nebula.Nebula;

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
        spectromeda.nebula.utils.ConfigSettings.afksystem_enable = this.config.getBoolean("settings.afksystem.enable");
        spectromeda.nebula.utils.ConfigSettings.chatping_enable = this.config.getBoolean("settings.chatping.enable");
        spectromeda.nebula.utils.ConfigSettings.smeltingpatch_enable = this.config.getBoolean("settings.smeltingpatch.enable");
    }
}
