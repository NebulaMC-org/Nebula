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
        log.info("ID: " + id);
        log.info("Manabars.get(p) = " + manaBars.get(id));
        if (manaBars.get(id) == null) {
            log.info("Created new mana bar for " + p.getName());
            manaBars.put(p.getUniqueId(), new ManaBar(id));
        }
        manaBars.get(p.getUniqueId()).tickManaBar();
    }
}
