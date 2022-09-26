package org.nebulamc.plugin.features.customitems;

import org.bukkit.NamespacedKey;
import org.nebulamc.plugin.Nebula;

import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Logger;

public class ItemManager {

    private static final Logger log = Nebula.getInstance().getLogger();

    public static NamespacedKey customItemKey = new NamespacedKey(Nebula.getInstance(), "customItemKey");

    public static HashMap<String, CustomItem> items = new HashMap<>();

    public static void registerItems(CustomItem... customItems){
        Arrays.asList(customItems).forEach(ci-> items.put(ci.getId(), ci));
    }
}
