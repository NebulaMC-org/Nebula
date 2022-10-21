package org.nebulamc.plugin.utils;

import me.angeschossen.lands.api.flags.Flags;
import me.angeschossen.lands.api.integration.LandsIntegration;
import me.angeschossen.lands.api.land.LandWorld;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;
import org.nebulamc.plugin.Nebula;
import org.nebulamc.plugin.features.customitems.Action;

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

    public static boolean canBreak(Player player, Location loc, ItemStack itemStack) {
        LandWorld landWorld = lands.getLandWorld(player.getWorld());

        if (!(player.getWorld().getName().equals("nebula")) && !(player.getWorld().getName().equals("admin")))
            if (landWorld.hasFlag(player, loc, itemStack.getType(), Flags.BLOCK_BREAK, true)){
                return true;
            }
        return false;
    }
/*
    public static List<ItemStack> getEntityItems(LivingEntity e) {
        EntityEquipment equipment = e.getEquipment();

        if (equipment == null) {
            return Lists.newArrayList();
        }

        List<ItemStack> list = Lists.newArrayList();
        list.add(equipment.getItemInMainHand());
        list.add(equipment.getItemInOffHand());
        list.add(equipment.getBoots());
        list.add(equipment.getLeggings());
        list.add(equipment.getChestplate());
        list.add(equipment.getHelmet());

        Skill.additionalSlots.forEach(slot -> {
            if (e instanceof Player) {
                Player p = (Player) e;
                list.add(p.getInventory().getItem(slot));
            } else {
                list.add(null);
            }
        });

        return list;
    }
*/
    public static void rayCast(Player player, int distance, Action tickAction, Action startAction, Action endAction){
        startAction.execute(player, player.getLocation(), null);
        BlockIterator rayBlocks = new BlockIterator(player.getEyeLocation(), 0, distance);
        while (rayBlocks.hasNext()){
            Location loc = rayBlocks.next().getLocation();
            if (loc.getBlock().getType().isSolid()){
                endAction.execute(player, loc, null);
                return;
            } else {
                tickAction.execute(player, loc, null);
            }
        }
        endAction.execute(player, player.getLocation().add(player.getLocation().getDirection().multiply(distance)), null);

    }
}
