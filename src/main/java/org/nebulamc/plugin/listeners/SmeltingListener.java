package org.nebulamc.plugin.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockCookEvent;
import org.bukkit.inventory.ItemStack;
import org.nebulamc.plugin.Nebula;

public class SmeltingListener implements Listener {

    static Nebula plugin;

    public SmeltingListener(Nebula main){
        plugin = main;
    }

    @EventHandler
    private void onSmelt(BlockCookEvent e) {
        if (e.getSource().getType() == Material.NETHER_GOLD_ORE) {
            ItemStack output = new ItemStack(Material.GOLD_NUGGET, 2);
            e.setResult(output);
        }
    }

}
