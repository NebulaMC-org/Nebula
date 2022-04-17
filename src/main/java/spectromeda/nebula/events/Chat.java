package spectromeda.nebula.events;

import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import spectromeda.nebula.Nebula;

import static org.bukkit.Sound.BLOCK_NOTE_BLOCK_BELL;

public class Chat implements Listener {

    static Nebula plugin;

    public Chat(Nebula main){
        plugin = main;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onChat(AsyncChatEvent event){
        if (event.message().toString().contains("@")){
            for (Player p : Bukkit.getOnlinePlayers()){
                if (event.message().toString().contains("@" + p.getName())){
                    p.playSound(p.getLocation(), BLOCK_NOTE_BLOCK_BELL, 1, 1);
                }
            }
        }
    }
}
