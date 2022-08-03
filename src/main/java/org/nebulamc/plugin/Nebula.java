package org.nebulamc.plugin;

import lombok.Getter;
import me.angeschossen.lands.api.integration.LandsIntegration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.nebulamc.plugin.commands.SetPronounsCommand;
import org.nebulamc.plugin.commands.SpawnChestCommand;
import org.nebulamc.plugin.commands.WagerCommand;
import org.nebulamc.plugin.features.wager.WagerManager;
import org.nebulamc.plugin.listeners.ChatListener;
import org.nebulamc.plugin.listeners.PlayerListener;
import org.nebulamc.plugin.listeners.SmeltingListener;
import org.nebulamc.plugin.features.haproxy.HAProxy;
import org.nebulamc.plugin.features.loottable.LootTable;
import org.nebulamc.plugin.utils.config.ConfigManager;
import org.nebulamc.plugin.utils.config.ConfigSettings;

public final class Nebula extends JavaPlugin {

    @Getter
    private static Nebula instance;
    @Getter
    private HAProxy HAProxy;
    @Getter
    private ConfigManager configManager;
    @Getter
    private WagerManager wagerManager;
    @Getter
    private LandsIntegration landsIntegration;
    @Getter
    private static LootTable meteorLoot;

    private final PluginManager pm = this.getServer().getPluginManager();

    @Override
    public void onEnable() {

        instance = this;
        this.configManager = new ConfigManager(this);
        this.landsIntegration = new LandsIntegration(this);
        this.wagerManager = new WagerManager();

        configManager.createDefaults();
        checkDependencies();
        registerListeners();
        registerCommands();

        try {
            this.HAProxy = new HAProxy();
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

    public MiniMessage miniMessage() {
        return MiniMessage.miniMessage();
    }

    private void registerListeners() {

        pm.registerEvents(new PlayerListener(), this);

        if (ConfigSettings.afksystem_enable) {
            // temp removed
        }
        if (ConfigSettings.chatping_enable) {
            pm.registerEvents(new ChatListener(this), this);
        }
        if (ConfigSettings.smeltingpatch_enable) {
            pm.registerEvents(new SmeltingListener(this), this);
        }
    }

    private void registerCommands(){
        this.getCommand("spawnchest").setExecutor(new SpawnChestCommand());
        this.getCommand("setpronouns").setExecutor(new SetPronounsCommand());

        this.getCommand("wager").setTabCompleter(new WagerCommand());
        this.getCommand("wager").setExecutor(new WagerCommand());
    }

    private void checkDependencies() {
        // Will be re-implemented once dependencies are needed.
    }

}
