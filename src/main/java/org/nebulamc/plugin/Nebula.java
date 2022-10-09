package org.nebulamc.plugin;

import lombok.AccessLevel;
import lombok.Getter;
import me.angeschossen.lands.api.integration.LandsIntegration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.nebulamc.plugin.commands.*;
import org.nebulamc.plugin.features.adminevents.AdminEventManager;
import org.nebulamc.plugin.features.customitems.CustomItemHandler;
import org.nebulamc.plugin.features.customitems.ItemManager;
import org.nebulamc.plugin.features.customitems.items.*;
import org.nebulamc.plugin.features.customitems.items.vertus.VertusCrystal;
import org.nebulamc.plugin.features.customitems.items.vertus.VertusShard;
import org.nebulamc.plugin.features.customitems.items.vertus.VertusSword;
import org.nebulamc.plugin.features.loottable.LootTable;
import org.nebulamc.plugin.features.wager.WagerManager;
import org.nebulamc.plugin.hooks.EssentialsHook;
import org.nebulamc.plugin.hooks.ExcellentCratesHook;
import org.nebulamc.plugin.hooks.Hook;
import org.nebulamc.plugin.hooks.LuckPermsHook;
import org.nebulamc.plugin.listeners.ChatListener;
import org.nebulamc.plugin.listeners.PlayerListener;
import org.nebulamc.plugin.listeners.SmeltingListener;
import org.nebulamc.plugin.player.NPlayerManager;
import org.nebulamc.plugin.utils.config.ConfigManager;
import org.nebulamc.plugin.utils.config.ConfigSettings;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
public final class Nebula extends JavaPlugin {

    @Getter(AccessLevel.PUBLIC)
    private static Nebula instance;

    private ConfigManager configManager;
    private WagerManager wagerManager;
    private NPlayerManager playerManager;
    private AdminEventManager adminEventManager;

    private LandsIntegration landsIntegration;
    public static LootTable meteorLoot;

    @Getter(AccessLevel.NONE)
    private final PluginManager pm = this.getServer().getPluginManager();

    private static final List<Hook> hooks = List.of(
            new EssentialsHook(),
            new LuckPermsHook(),
            new ExcellentCratesHook()
    );

    @Override
    public void onEnable() {

        instance = this;
        this.configManager = new ConfigManager(this);
        this.wagerManager = new WagerManager();
        this.playerManager = new NPlayerManager();
        this.adminEventManager = new AdminEventManager();

        configManager.createDefaults();
        registerListeners();
        registerCommands();
        registerHooks();
        registerCustomItems();
        buildMeteorLootTable();

        runMeteorSpawnLoop();

        getLogger().info("Successfully enabled.");
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll();
    }

    public MiniMessage miniMessage() {
        return MiniMessage.miniMessage();
    }

    private void registerHooks() {
        for (Hook h : hooks) {
            if (getServer().getPluginManager().getPlugin(h.getPluginId()) == null) {
                // The dependency needed couldn't be found! All dependencies that are hooked into are required so we need to shutdown.
                getLogger().severe("Required plugin " + h.getPluginId() + " could not be found! This is a required dependency.");
                this.setEnabled(false);
            } else {
                try {
                    // If this hook needs manual enabling, invoke its hook method using reflection
                    h.getClass().getMethod("hook").invoke(h);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {
                    // If not, it's fine to ignore any exceptions generated
                }
            }
        }
    }

    public void registerCustomItems() {
        ItemManager.registerItems(
                new SlimeOrb(),
                new GoldenCookie(),
                new TestItem(),
                new ImpetusHammer(),
                new NoFallBoots(),
                new SacrificialBlade(),
                new BridgeWand(),
                new VertusCrystal(),
                new VertusShard(),
                new VertusSword()
        );
    }

    private void buildMeteorLootTable() {
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
    }

    private void registerListeners() {

        pm.registerEvents(new PlayerListener(), this);
        pm.registerEvents(new CustomItemHandler(), this);

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

        this.getCommand("giveitem").setExecutor(new GiveItemCommand());
        this.getCommand("giveitem").setTabCompleter(new GiveItemCommand());

        this.getCommand("wager").setTabCompleter(new WagerCommand());
        this.getCommand("wager").setExecutor(new WagerCommand());

        this.getCommand("joinevent").setExecutor(new JoinEventCommand());
        this.getCommand("runevent").setExecutor(new RunEventCommand());
    }

    private void registerDependencies() {
        this.landsIntegration = new LandsIntegration(this);
    }

    private void runMeteorSpawnLoop() {
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

    @SuppressWarnings("unchecked")
    public static <T extends Hook> T getHook(String key, Class<T> clazz) {
        try {
            List<Hook> dhk = hooks.stream().filter(
                    h -> Objects.equals(h.getPluginId(), key)
            ).collect(Collectors.toList());
            if (dhk.size() == 0) return null;
            else return (T) dhk.get(0);
        } catch (ClassCastException e) {
            return null;
        }
    }

}
