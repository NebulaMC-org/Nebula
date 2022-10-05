package org.nebulamc.plugin.features.mana;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.nebulamc.plugin.Nebula;

import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

public class ManaManager implements Listener {

    public static HashMap<UUID, ManaBar> manaBars = new HashMap<>();

    public static void createManaBar(Player p) {
        UUID id = p.getUniqueId();
        if (manaBars.get(id) == null) {
            manaBars.put(p.getUniqueId(), new ManaBar(id));
        }
        manaBars.get(p.getUniqueId()).tickManaBar();
    }

    public static void deleteManaBar(Player p) {
        UUID id = p.getUniqueId();
        if (manaBars.get(id) != null) {
            manaBars.remove(id);
        }
    }
}
