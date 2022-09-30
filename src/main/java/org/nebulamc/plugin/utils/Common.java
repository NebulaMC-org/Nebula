package org.nebulamc.plugin.utils;

import org.bukkit.ChatColor;
import org.bukkit.util.Vector;

public final class Common {
    private Common(){}

    public static String colorize(String string){
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static Vector absoluteVector(Vector vector){
        double absX = Math.abs(vector.getX());
        double absY = Math.abs(vector.getY());
        double absZ = Math.abs(vector.getZ());
        return new Vector(absX, absY, absZ);
    }
}
