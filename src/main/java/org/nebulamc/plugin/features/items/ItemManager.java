package org.nebulamc.plugin.features.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.nebulamc.plugin.Nebula;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class ItemManager {

    private static final Logger log = Nebula.getInstance().getLogger();
    public static HashMap<String, ItemStack> items = new HashMap<>();

    public static ItemStack boomStick;

    public static void init(){
        log.info("Initializing custom items");
        createBoomStick();
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
        items.put("boomStick", boomStick);
    }
}
