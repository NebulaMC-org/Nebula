package org.nebulamc.plugin.player;

import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.HashMap;

public class NPlayerManager {

    public HashMap<Player, NPlayer> players = new HashMap<>();

    public void create(Player p) {
        players.put(
                p,
                new NPlayer(p)
        );
    }

    public void delete(Player p) {
        players.remove(p);
    }

    @Nullable
    public NPlayer get(Player p) {
        return players.get(p);
    }

}
