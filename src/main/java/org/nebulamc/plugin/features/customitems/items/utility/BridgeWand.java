package org.nebulamc.plugin.features.customitems.items.utility;

import me.angeschossen.lands.api.flags.Flags;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.nebulamc.plugin.features.customitems.items.CustomItem;
import org.nebulamc.plugin.utils.Utils;

import java.util.Arrays;
import java.util.List;

public class BridgeWand extends CustomItem {
    @Override
    public String getName() {
        return "&fBridge Wand";
    }

    @Override
    public Material getMaterial() {
        return Material.STICK;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("\n",
                "&eRight-click the top of a block to",
                "&ebridge out with the item in your offhand.");
    }

    @Override
    public int getModelData() {
        return 0;
    }

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public boolean isUnbreakable() {
        return false;
    }

    @Override
    public void handlePlaceBlock(Player player, ItemStack itemStack, BlockPlaceEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        ItemStack offHand = player.getInventory().getItemInOffHand();
        Block clickedBlock = event.getClickedBlock();

        if (offHand.getType().isBlock() && clickedBlock != null && clickedBlock.getRelative(player.getFacing(), 1).getType().equals(Material.AIR)){
            Block newBlock = clickedBlock.getRelative(player.getFacing(), 1);
            if (Utils.hasFlag(player, newBlock.getLocation(), offHand, Flags.BLOCK_PLACE)){
                newBlock.setType(offHand.getType(), true);
                offHand.subtract();
            }
        }
    }
}
