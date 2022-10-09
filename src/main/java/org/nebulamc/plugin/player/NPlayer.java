package org.nebulamc.plugin.player;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

@Getter @Setter
public class NPlayer {

    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    public Player player;
    @Nullable
    public Location lastLocation;

    public NPlayer(Player p) {
        player = p;
        lastLocation = null;
    }

}
