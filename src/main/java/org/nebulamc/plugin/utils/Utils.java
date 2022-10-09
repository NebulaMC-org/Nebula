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

public final class Utils {
    private Utils(){}

    public static LandsIntegration lands = Nebula.getInstance().getLandsIntegration();

    public static String colorize(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static Vector absoluteVector(Vector vector) {
        double absX = Math.abs(vector.getX());
        double absY = Math.abs(vector.getY());
        double absZ = Math.abs(vector.getZ());
        return new Vector(absX, absY, absZ);
    }

    public static boolean canPlace(Player player, Location loc, ItemStack itemStack) {

        LandWorld landWorld = lands.getLandWorld(player.getWorld());

        if (!(player.getWorld().getName().equals("nebula")) && !(player.getWorld().getName().equals("admin")))
            if (landWorld.hasFlag(player, loc, itemStack.getType(), Flags.BLOCK_PLACE, true)){
                return true;
            }
        return false;
    }

    /* temporarily disabled
    public void rayCast(Player player, int distance, Action tickAction, Action startAction, Action endAction){
        startAction.execute(player, player.getLocation());
        BlockIterator rayBlocks = new BlockIterator(player.getEyeLocation(), 1, distance);
        while (rayBlocks.hasNext()){
            Location loc = rayBlocks.next().getLocation();
            if (loc.getBlock().getType().isSolid()){
                endAction.execute(player, loc);
                break;
            } else {
                tickAction.execute(player, loc);
            }
        }

    }
  */
}
