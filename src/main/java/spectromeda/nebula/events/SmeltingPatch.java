package spectromeda.nebula.events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import spectromeda.nebula.Nebula;


public class SmeltingPatch implements Listener {
    static Nebula plugin;
    public SmeltingPatch(Nebula main){
        plugin = main;
    }
    @EventHandler
    private void onSmelt(FurnaceSmeltEvent e) {
        if (e.getBlock().equals(Material.NETHER_GOLD_ORE)) {
            ItemStack output = new ItemStack(Material.GOLD_NUGGET);
            e.setResult(output);
        }
    }
}
