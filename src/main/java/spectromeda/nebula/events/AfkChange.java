package spectromeda.nebula.events;

import net.ess3.api.events.AfkStatusChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import spectromeda.nebula.Nebula;

import java.util.UUID;


public class AfkChange implements Listener {

    static Nebula plugin;

    public AfkChange(Nebula main){
        plugin = main;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onAfk(AfkStatusChangeEvent event){
        Player player = Bukkit.getPlayer(event.getAffected().getName());
        if (event.getValue() == true){
            UUID uuid = player.getUniqueId();
            Location playerLoc = player.getLocation();
            Location afkWarp = new Location(Bukkit.getWorld("nebula"), 1013.5, 84, 1006.5,-180 ,-4);
            player.teleport(afkWarp);



        }
    }
}
