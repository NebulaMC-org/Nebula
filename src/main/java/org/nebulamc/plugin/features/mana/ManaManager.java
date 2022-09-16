package org.nebulamc.plugin.features.mana;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;

public class ManaManager implements Listener {

    public HashMap<Player, ManaBar> manaBars = new HashMap<>();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if (manaBars.get(p) == null) {
            new ManaBar(p);
        }
        manaBars.get(p).tickManaBar();
    }
}
