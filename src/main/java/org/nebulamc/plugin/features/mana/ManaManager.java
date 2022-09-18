package org.nebulamc.plugin.features.mana;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.nebulamc.plugin.Nebula;

import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

public class ManaManager implements Listener {

    private static final Logger log = Nebula.getInstance().getLogger();

    public static HashMap<UUID, ManaBar> manaBars = new HashMap<>();

    public static void createManaBar(Player p){
        UUID id = p.getUniqueId();
        if (manaBars.get(id) == null) {
            manaBars.put(p.getUniqueId(), new ManaBar(id));
        }
        manaBars.get(p.getUniqueId()).tickManaBar();
    }
}
