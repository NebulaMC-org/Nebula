package spectromeda.nebula;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;
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
import spectromeda.nebula.events.AfkChange;
import spectromeda.nebula.events.Chat;
import spectromeda.nebula.events.SmeltingPatch;
import spectromeda.nebula.loottable.LootTable;
import spectromeda.nebula.utils.ConfigManager;
import spectromeda.nebula.utils.ConfigSettings;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Logger;

public final class Nebula extends JavaPlugin {

    private static final Logger log = Bukkit.getLogger();

    private ConfigManager configManager;

    private LandsIntegration landsIntegration;

    private static LootTable meteorLoot;

    public LootTable getMeteorLoot(){
        return this.meteorLoot;
    }

    public ConfigManager getConfigManager() {
        return this.configManager;
    }

    public LandsIntegration landsIntegration() {
        return this.landsIntegration;
    }

    PluginManager pm = this.getServer().getPluginManager();

    private void testDataSource(DataSource dataSource) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            if (!conn.isValid(1)) {
                throw new SQLException("Could not establish database connection.");
            }
        }
    }

    private DataSource initMySQLDataSource() throws SQLException {
        MysqlDataSource dataSource = new MysqlConnectionPoolDataSource();
        // set credentials

        // Test connection
        testDataSource(dataSource);
        return dataSource;
    }

    private void registerEvents(){
        if (ConfigSettings.afksystem_enable == true) {
            pm.registerEvents(new AfkChange(this), this);
        }
        if (ConfigSettings.chatping_enable == true) {
            pm.registerEvents(new Chat(this), this);
        }
        if (ConfigSettings.smeltingpatch_enable == true) {
            pm.registerEvents(new SmeltingPatch(this), this);
        }
    }

    private void registerCommands(){
        this.getCommand("spawnchest").setExecutor(new SpawnChest(this));
    }

    private void checkDependencies(){
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
        // Plugin startup logic
        this.configManager = new ConfigManager(this);
        this.landsIntegration = new LandsIntegration(this);
        configManager.createDefaults();
        checkDependencies();
        registerEvents();
        registerCommands();

        try {
            initMySQLDataSource();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Random rand = new Random();
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
            int times = 1; //initialize
            public void run() {
                if (times % 6 == 0) { //ONLY if "times" is a multiple of six -> Therefor every hour
                    getServer().dispatchCommand(getServer().getConsoleSender(), "spawnchest");
                }
                times++;
            }
        }.runTaskTimer(this, 0, 10 * 60 * 20); //run the runnable in 0 ticks from onEnable and EVERY 12000 ticks

    }

    @Override
    public void onDisable() {
        //unregister
        HandlerList.unregisterAll();
    }

}
