package org.nebulamc.plugin.utils;

import me.angeschossen.lands.api.flags.Flags;
import me.angeschossen.lands.api.flags.types.RoleFlag;
import me.angeschossen.lands.api.integration.LandsIntegration;
import me.angeschossen.lands.api.land.LandWorld;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;
import org.nebulamc.plugin.Nebula;
import org.nebulamc.plugin.features.customitems.actions.Action;
import org.nebulamc.plugin.features.customitems.source.EntitySource;
import org.nebulamc.plugin.features.customitems.source.Source;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;
import org.nebulamc.plugin.features.customitems.targeter.LocationTarget;
import org.nebulamc.plugin.features.customitems.targeter.Target;

import java.util.Random;

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

    public static boolean hasFlag(Player player, Location loc, ItemStack itemStack, RoleFlag flag){
        LandWorld landWorld = lands.getLandWorld(player.getWorld());

        if (itemStack == null){
            itemStack = new ItemStack(Material.AIR);
        }

        if (!(player.getWorld().getName().equals("nebula")) && !(player.getWorld().getName().equals("admin")))
            if (landWorld.hasFlag(player, loc, itemStack.getType(), flag, true)){
                return true;
            }
        return false;
    }

    public static boolean canDamage(Player player, Entity target){
        Location location = target.getLocation();
        if (target instanceof LivingEntity) {
            if (target instanceof Player) {
                if (Utils.hasFlag(player, location, null, Flags.ATTACK_PLAYER))
                    return true;
            }
            if (target instanceof Monster || target instanceof Golem) {
                if (Utils.hasFlag(player, location, null, Flags.ATTACK_MONSTER))
                    return true;
            }
            if (target instanceof Animals || target instanceof Ambient || target instanceof WaterMob || target instanceof NPC) {
                if (Utils.hasFlag(player, location, null, Flags.ATTACK_ANIMAL))
                    return true;
            }
        }
        return false;
    }

    public static boolean canDamage(Target target, Source source){
        if (target instanceof EntityTarget){
            if (source.getCaster() instanceof Player){
                Player playerSource = ((Player) source.getCaster()).getPlayer();
                LivingEntity entityTarget = ((EntityTarget) target).getTarget();
                if (Utils.canDamage(playerSource, entityTarget)){
                    return true;
                }
            }
        }
        return false;
    }

    public static void rayCast(Player player, int distance, int forwardOffset, boolean alwaysDoEndAction, Action tickAction, Action startAction, Action endAction){
        EntitySource playerSource = new EntitySource(player);
        startAction.execute(new LocationTarget(player.getLocation()),playerSource);
        BlockIterator rayBlocks = new BlockIterator(player.getEyeLocation().add(player.getLocation().getDirection().multiply(forwardOffset)), 0, distance);
        while (rayBlocks.hasNext()){
            Location loc = rayBlocks.next().getLocation();
            if (loc.getBlock().getType().isSolid()){
                endAction.execute(new LocationTarget(loc), playerSource);
                return;
            } else {
                tickAction.execute(new LocationTarget(loc), playerSource);
            }
        }
        if (alwaysDoEndAction){
            endAction.execute(new LocationTarget(player.getLocation().add(player.getLocation().getDirection().multiply(distance))), playerSource);
        }
    }

    public static void straightRayCast(Player player, int distance, int forwardOffset, double stepSize, boolean alwaysDoEndAction, Action tickAction, Action startAction, Action endAction){
        EntitySource playerSource = new EntitySource(player);
        startAction.execute(new LocationTarget(player.getLocation()),playerSource);

        Location loc = player.getEyeLocation().add(player.getLocation().getDirection().multiply(forwardOffset));
        Vector direction = player.getEyeLocation().getDirection().multiply(stepSize);
        int steps = 0;
        distance /= stepSize;

        while (steps < distance){
            if (loc.getBlock().getType().isSolid()){
                endAction.execute(new LocationTarget(loc), playerSource);
                return;
            } else {
                tickAction.execute(new LocationTarget(loc), playerSource);
            }
            loc.add(direction);
            steps++;
        }
        if (alwaysDoEndAction){
            endAction.execute(new LocationTarget(player.getLocation().add(player.getLocation().getDirection().multiply(distance))), playerSource);
        }

    }

    public static double calculateBowDamage(EntityShootBowEvent event){
        ItemMeta meta = event.getBow().getItemMeta();
        Random generator = new Random();
        double damage;

        double force = event.getForce();
        if (force >= 1){
            damage = (8 + generator.nextInt(0, 3));
        } else if (force >= 0.2){
            damage = 5;
        } else {
            damage = 1;
        }

        if (meta.getEnchants().containsKey(Enchantment.ARROW_DAMAGE)){
            damage *= 1 + (0.25 * (meta.getEnchantLevel(Enchantment.ARROW_DAMAGE) + 1));
        }

        return damage;
    }
}
