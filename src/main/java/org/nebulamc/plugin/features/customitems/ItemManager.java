package org.nebulamc.plugin.features.customitems;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.nebulamc.plugin.Nebula;

import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Logger;

public class ItemManager {

    private static final Logger log = Nebula.getInstance().getLogger();

    public static NamespacedKey customItemKey = new NamespacedKey(Nebula.getInstance(), "customItemKey");

    public static HashMap<String, CustomItem> items = new HashMap<>();
    public static HashMap<String, Timer> timers = new HashMap<>();

    public static void registerItems(CustomItem... customItems){
        Arrays.asList(customItems).forEach(ci-> items.put(ci.getId(), ci));
    }

    public static void registerTimers(){
        timers.clear();
        for (CustomItem i : items.values()){
            if (i.hasTimerAction()){
                timers.put(i.getId(), new Timer(i));
                timers.get(i.getId()).execute();
            }
        }
    }

    public static void addPlayerToTimer(Player player, CustomItem item){
        timers.get(item.getId()).addPlayer(player);
    }

    public static void removePlayerFromTimer(Player player, CustomItem item){
        timers.get(item.getId()).removePlayer(player);
    }

    public static void removeFromAllTimers(Player player){
        for (Timer timer : timers.values()){
            timer.removePlayer(player);
        }
    }
}
