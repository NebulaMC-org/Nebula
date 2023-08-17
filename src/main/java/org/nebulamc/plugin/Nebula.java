package org.nebulamc.plugin;

import lombok.Getter;
import me.angeschossen.lands.api.integration.LandsIntegration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Material;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.nebulamc.plugin.commands.*;
import org.nebulamc.plugin.features.customitems.CustomItemHandler;
import org.nebulamc.plugin.features.customitems.ItemManager;
import org.nebulamc.plugin.features.customitems.items.*;
import org.nebulamc.plugin.features.customitems.items.combat.*;
import org.nebulamc.plugin.features.customitems.items.consumables.Ambrosia;
import org.nebulamc.plugin.features.customitems.items.consumables.GoldenCookie;
import org.nebulamc.plugin.features.customitems.items.consumables.SpicyApple;
import org.nebulamc.plugin.features.customitems.items.materials.*;
import org.nebulamc.plugin.features.customitems.items.sets.catalyst.CatalystBoots;
import org.nebulamc.plugin.features.customitems.items.sets.catalyst.CatalystChestplate;
import org.nebulamc.plugin.features.customitems.items.sets.catalyst.CatalystHelmet;
import org.nebulamc.plugin.features.customitems.items.sets.catalyst.CatalystLeggings;
import org.nebulamc.plugin.features.customitems.items.sets.spirit.SpiritBoots;
import org.nebulamc.plugin.features.customitems.items.sets.spirit.SpiritChestplate;
import org.nebulamc.plugin.features.customitems.items.sets.spirit.SpiritHelmet;
import org.nebulamc.plugin.features.customitems.items.sets.spirit.SpiritLeggings;
import org.nebulamc.plugin.features.customitems.items.sets.titansteel.TitanSteelBoots;
import org.nebulamc.plugin.features.customitems.items.sets.titansteel.TitanSteelChestplate;
import org.nebulamc.plugin.features.customitems.items.sets.titansteel.TitanSteelHelmet;
import org.nebulamc.plugin.features.customitems.items.sets.titansteel.TitanSteelLeggings;
import org.nebulamc.plugin.features.customitems.items.sets.vertus.*;
import org.nebulamc.plugin.features.customitems.items.summoning.RoyalOffering;
import org.nebulamc.plugin.features.customitems.items.summoning.SunSigil;
import org.nebulamc.plugin.features.customitems.items.utility.*;
import org.nebulamc.plugin.features.loottable.LootTable;
import org.nebulamc.plugin.features.playerdata.PlayerManager;
import org.nebulamc.plugin.listeners.ChatListener;
import org.nebulamc.plugin.listeners.DeathListener;
import org.nebulamc.plugin.listeners.SmeltingListener;
import org.nebulamc.plugin.utils.config.ConfigManager;
import org.nebulamc.plugin.utils.config.ConfigSettings;


public final class Nebula extends JavaPlugin {

    @Getter
    private static Nebula instance;
    @Getter
    private ConfigManager configManager;
    @Getter
    private LandsIntegration landsIntegration;
    @Getter
    private static LootTable meteorLoot;

    private final PluginManager pm = this.getServer().getPluginManager();
    private static Economy econ;

    @Override
    public void onEnable() {

        instance = this;
        this.configManager = new ConfigManager(this);
        this.landsIntegration = new LandsIntegration(this);

        configManager.createDefaults();
        setupEconomy();
        checkDependencies();
        registerListeners();
        registerCommands();
        registerCustomItems();
        buildMeteorLootTable();
        // runMeteorSpawnLoop();


        getLogger().info("Successfully enabled Nebula plugin.");

    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll();
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            getLogger().info("Couldn't find vault plugin.");
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            getLogger().info("RSP is null.");
            return false;
        }

        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }

    public MiniMessage miniMessage() {
        return MiniMessage.miniMessage();
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
                new VertusSword(),
                new ShadowBoots(),
                new Jetpack(),
                new SolarFlare(),
                new VertusAxe(),
                new VertusPickaxe(),
                new VertusShovel(),
                new VertusHoe(),
                new VertusChestplate(),
                new VertusLeggings(),
                new VertusHelmet(),
                new VertusBoots(),
                new ArrowStick(),
                new LaserDrill(),
                new Beezooka(),
                new Shatterbow(),
                new Flamethrower(),
                new GrapplingHook(),
                new WhirlwindBlade(),
                new ThrowingKnife(),
                new FrostBow(),
                new MeteorStaff(),
                new DynamoBoots(),
                new FlintlockPistol(),
                new SunSigil(),
                new SpiritChestplate(),
                new SpiritBoots(),
                new SpiritHelmet(),
                new SpiritLeggings(),
                new PoseidonsCrown(),
                new TitanSteelChestplate(),
                new TitanSteelHelmet(),
                new TitanSteelLeggings(),
                new TitanSteelBoots(),
                new CatalystBoots(),
                new CatalystChestplate(),
                new CatalystLeggings(),
                new CatalystHelmet(),
                new RepeaterCrossbow(),
                new VulcansAxe(),
                new ShamanStaff(),
                new MythicalFeather(),
                new FireballCannon(),
                new HolyClaymore(),
                new SculkTome(),
                new UnstablePickaxe(),
                new Ambrosia(),
                new SpicyApple(),
                new PyromancersTome(),
                new MechanicalLegs(),
                new BloodRune(),
                new MeteorShard(),
                new NovaCell(),
                new QuetzalcoatlFeather(),
                new SculkSoul(),
                new SolarEssence(),
                new SpiritOrb(),
                new RelicShard(),
                new ToxicVial(),
                new ReinforcedCopper(),
                new BottledLightning(),
                new RoyalOffering()
        );
        ItemManager.registerTimers();
    }

    private void buildMeteorLootTable() {
        meteorLoot = new LootTable.LootTableBuilder()
                .add(new ItemStack(Material.AIR), 16)
                .add(new ItemStack(Material.GOLD_BLOCK, 2), 12)
                .add(new ItemStack(Material.DIAMOND_BLOCK, 1), 8)
                .add(new ItemStack(Material.SLIME_BALL, 12), 8)
                .add(new ItemStack(Material.GUNPOWDER, 16), 8)
                .add(new ItemStack(Material.ANCIENT_DEBRIS, 2), 10)
                .add(new ItemStack(Material.NETHERITE_PICKAXE, 1), 1)
                .add(new ItemStack(Material.NETHERITE_SWORD, 1), 1)
                .add(new ItemStack(Material.NETHERITE_AXE, 1), 1)
                .add(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 1), 3)
                .add(new ItemStack(Material.EXPERIENCE_BOTTLE, 12), 6)
                .add(new ItemStack(Material.ELYTRA, 1), 1)
                .add(new MeteorShard().getItem(), 10)
                .build();
    }

    private void registerListeners() {

        pm.registerEvents(new PlayerManager(), this);
        pm.registerEvents(new CustomItemHandler(), this);
        pm.registerEvents(new DeathListener(this), this);

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
        this.getCommand("arename").setExecutor(new AdminRenameCommand());
        this.getCommand("spawnmineshafts").setExecutor(new SpawnMineshaftsCommand());

        this.getCommand("giveitem").setExecutor(new GiveItemCommand());
        this.getCommand("giveitem").setTabCompleter(new GiveItemCommand());
    }

    private void checkDependencies() {
        // Will be re-implemented once dependencies are needed.
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

}
