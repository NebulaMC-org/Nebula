package spectromeda.nebula;

import me.angeschossen.lands.api.integration.LandsIntegration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import spectromeda.nebula.commands.SetPronouns;
import spectromeda.nebula.commands.SpawnChest;
import spectromeda.nebula.listeners.AFKStatusChangeListener;
import spectromeda.nebula.listeners.ChatListener;
import spectromeda.nebula.listeners.PlayerListener;
import spectromeda.nebula.listeners.SmeltingListener;
import spectromeda.nebula.features.haproxy.HAProxy;
import spectromeda.nebula.features.loottable.LootTable;
import spectromeda.nebula.utils.config.ConfigManager;
import spectromeda.nebula.utils.config.ConfigSettings;

import java.util.logging.Logger;

public final class Nebula extends JavaPlugin {

    private static final Logger log = Bukkit.getLogger();
    private static Nebula instance;

    private HAProxy haProxy;
    private ConfigManager configManager;
    private LandsIntegration landsIntegration;
    private final PluginManager pm = this.getServer().getPluginManager();
    private static LootTable meteorLoot;

    public static Nebula getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return this.configManager;
    }

    public LandsIntegration landsIntegration() {
        return this.landsIntegration;
    }

    public HAProxy haProxy() {
        return this.haProxy;
    }

    public LootTable getMeteorLoot(){
        return meteorLoot;
    }

    private void registerListeners() {

        pm.registerEvents(new PlayerListener(), this);

        if (ConfigSettings.afksystem_enable) {
            pm.registerEvents(new AFKStatusChangeListener(this), this);
        }
        if (ConfigSettings.chatping_enable) {
            pm.registerEvents(new ChatListener(this), this);
        }
        if (ConfigSettings.smeltingpatch_enable) {
            pm.registerEvents(new SmeltingListener(this), this);
        }
    }

    private void registerCommands(){
        this.getCommand("spawnchest").setExecutor(new SpawnChest(this));
    }

    private void checkDependencies() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            pm.registerEvents(new SetPronouns(this), this);
        } else {
            log.warning("Could not find PlaceholderAPI!");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        if (Bukkit.getPluginManager().getPlugin("Essentials") != null) {
            pm.registerEvents(new SetPronouns(this), this);
        } else {
            log.warning("Could not find Essentials!");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }


    @Override
    public void onEnable() {
        instance = this;
        this.configManager = new ConfigManager(this);
        this.landsIntegration = new LandsIntegration(this);
        configManager.createDefaults();
        checkDependencies();
        registerListeners();
        registerCommands();

        try {
            this.haProxy = new HAProxy();
        } catch (ClassNotFoundException | NoSuchFieldException | NoSuchMethodException | IllegalAccessException e) {
            getLogger().info("An exception occured hooking into HAProxyDetector, so it was disabled: " + e.getMessage());
        }

        meteorLoot = new LootTable.LootTableBuilder()
                .add(new ItemStack(Material.AIR), 16)
                .add(new ItemStack(Material.GOLD_BLOCK, 2), 12)
                .add(new ItemStack(Material.DIAMOND_BLOCK, 1), 8)
                .add(new ItemStack(Material.SLIME_BALL, 18), 8)
                .add(new ItemStack(Material.GUNPOWDER, 16), 8)
                .add(new ItemStack(Material.ANCIENT_DEBRIS, 1), 8)
                .add(new ItemStack(Material.NETHERITE_PICKAXE, 1), 1)
                .add(new ItemStack(Material.NETHERITE_SWORD, 1), 1)
                .add(new ItemStack(Material.NETHERITE_AXE, 1), 1)
                .add(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 1), 3)
                .add(new ItemStack(Material.EXPERIENCE_BOTTLE, 12), 6)
                .add(new ItemStack(Material.ELYTRA, 1), 1)
                .build();

        new BukkitRunnable() {
            int times = 1;
            public void run() {
                if (times % 6 == 0) {
                    getServer().dispatchCommand(getServer().getConsoleSender(), "spawnchest");
                }
                times++;
            }
        }.runTaskTimer(this, 0, 10 * 60 * 20);

    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll();
    }

}
