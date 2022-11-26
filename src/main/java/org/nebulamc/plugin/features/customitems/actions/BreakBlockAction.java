package org.nebulamc.plugin.features.customitems.actions;

import me.angeschossen.lands.api.flags.Flags;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.nebulamc.plugin.features.customitems.source.Source;
import org.nebulamc.plugin.features.customitems.targeter.Target;
import org.nebulamc.plugin.utils.Utils;

import java.util.Collection;

public class BreakBlockAction extends Action{

    double strength;
    boolean toInventory;
    float pickupPitch;

    public BreakBlockAction(double strength, boolean toInventory, float pickupPitch){
       this.strength = strength;
       this.toInventory = toInventory;
       this.pickupPitch = pickupPitch;
    }

    @Override
    public void execute(Target target, Source source) {
        Location loc = target.getLocation();
        Block block = loc.getBlock();
        if (source.getCaster() instanceof Player && block.getType().getHardness() > 0){
            Player player = (Player) source.getCaster();
            if (Utils.hasFlag(player, loc, new ItemStack(Material.AIR), Flags.BLOCK_BREAK)){

                if (block.getType().getHardness() <= strength){
                    Collection<ItemStack> blockDrops = block.getDrops();

                    if (block.getState() instanceof Container){
                        for (ItemStack i : ((Container) block.getState()).getInventory().getContents()){
                            if (i != null){
                                blockDrops.add(i);
                            }
                        }
                    }

                    if (toInventory){
                        for (ItemStack i : blockDrops){
                            if (player.getInventory().firstEmpty() == -1){
                                loc.getWorld().dropItem(player.getLocation(), i);
                            } else {
                                player.getInventory().addItem(i);
                            }

                        }
                        player.playSound(loc, Sound.ENTITY_ITEM_PICKUP, 0.5f, pickupPitch);

                    } else {
                        for (ItemStack i : blockDrops){
                            loc.getWorld().dropItem(target.getLocation(), i);
                        }
                    }

                    loc.getWorld().playSound(loc, block.getSoundGroup().getBreakSound(), 1, 1);
                    block.setType(Material.AIR);

                } else {
                    loc.getWorld().playSound(loc, block.getSoundGroup().getHitSound(), 1, 1);
                }
            }
        } else {
            loc.getWorld().playSound(loc, block.getSoundGroup().getHitSound(), 1, 1);
        }
    }
}
