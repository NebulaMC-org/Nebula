package org.nebulamc.plugin.features.customitems;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public abstract class Action {
    public abstract void execute(Player player, Location location, Entity target);
}
