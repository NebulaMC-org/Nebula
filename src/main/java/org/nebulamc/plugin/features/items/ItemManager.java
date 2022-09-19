package org.nebulamc.plugin.features.items;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.nebulamc.plugin.Nebula;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ItemManager {

    private static final Logger log = Nebula.getInstance().getLogger();
    public static ItemStack boomStick;
    public static ItemStack titanHelmet;
    public static ItemStack titanChestplate;
    public static ItemStack titanLeggings;
    public static ItemStack titanBoots;

    public static ArrayList<ItemStack> items = new ArrayList<>();

    public static void init(){
        log.info("Initializing custom items");
        createBoomStick();
        createTitanHelmet();
        createTitanChestplate();
        createTitanLeggings();
        createTitanBoots();
    }

    private static void createBoomStick(){
        ItemStack item = new ItemStack(Material.STICK, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§fBoom Stick");

        List<String> lore = new ArrayList<>();
        lore.add("§7Type: §5Magic");
        lore.add("\n");
        lore.add("§eRight-click to summon a powerful explosion!");
        meta.setLore(lore);

        meta.addEnchant(Enchantment.LUCK, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);
        boomStick = item;
        items.add(boomStick);
    }

    private static void createTitanHelmet(){
        ItemStack item = new ItemStack(Material.LEATHER_HELMET, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§cTitan Steel Helmet");

        List<String> lore = new ArrayList<>();
        lore.add("§7Type: §cMelee");
        meta.setLore(lore);

        meta.addItemFlags(ItemFlag.HIDE_DYE);

        item.setItemMeta(meta);
        titanHelmet = item;
        items.add(titanHelmet);
    }

    private static void createTitanChestplate(){
        ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§cTitan Steel Chestplate");

        List<String> lore = new ArrayList<>();
        lore.add("§7Type: §cMelee");
        meta.setLore(lore);

        meta.addItemFlags(ItemFlag.HIDE_DYE);

        item.setItemMeta(meta);
        titanChestplate = item;
        items.add(titanChestplate);
    }

    private static void createTitanLeggings(){
        ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§cTitan Steel Leggings");

        List<String> lore = new ArrayList<>();
        lore.add("§7Type: §cMelee");
        meta.setLore(lore);

        meta.addItemFlags(ItemFlag.HIDE_DYE);

        item.setItemMeta(meta);
        titanLeggings = item;
        items.add(titanLeggings);
    }

    private static void createTitanBoots(){
        ItemStack item = new ItemStack(Material.LEATHER_BOOTS, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§cTitan Steel Chestplate");

        List<String> lore = new ArrayList<>();
        lore.add("§7Type: §cMelee");
        meta.setLore(lore);

        meta.addItemFlags(ItemFlag.HIDE_DYE);

        item.setItemMeta(meta);
        titanBoots = item;
        items.add(titanBoots);
    }
}
