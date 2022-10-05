package org.nebulamc.plugin.utils;

import me.angeschossen.lands.api.flags.Flags;
import me.angeschossen.lands.api.integration.LandsIntegration;
import me.angeschossen.lands.api.land.LandWorld;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.nebulamc.plugin.Nebula;

public final class Common {
    private Common(){}

    private static LandsIntegration lands = Nebula.getInstance().getLandsIntegration();

    public static String colorize(String string){
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static Vector absoluteVector(Vector vector){
        double absX = Math.abs(vector.getX());
        double absY = Math.abs(vector.getY());
        double absZ = Math.abs(vector.getZ());
        return new Vector(absX, absY, absZ);
    }

    public static boolean canPlace(Player player, Location loc, ItemStack itemStack){
        LandWorld landWorld = lands.getLandWorld(player.getWorld());

        if (!(player.getWorld().getName().equals("nebula")) && !(player.getWorld().getName().equals("admin")))
            if (landWorld.hasFlag(player, loc, itemStack.getType(), Flags.BLOCK_PLACE, true)){
                return true;
            }
        return false;
    }
}
