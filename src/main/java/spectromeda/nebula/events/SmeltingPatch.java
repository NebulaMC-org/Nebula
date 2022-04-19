package spectromeda.nebula.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockCookEvent;
import org.bukkit.inventory.ItemStack;
import spectromeda.nebula.Nebula;


public class SmeltingPatch implements Listener {
    static Nebula plugin;
    public SmeltingPatch(Nebula main){
        plugin = main;
    }
    @EventHandler
    private void onSmelt(BlockCookEvent e) {
        Material smeltMat = (Material.NETHER_GOLD_ORE);
        if (e.getSource().getType().equals(smeltMat)) {
            ItemStack output = new ItemStack(Material.GOLD_NUGGET, 2);
            e.setResult(output);
        }
    }
}
