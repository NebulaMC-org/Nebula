package org.nebulamc.plugin.features.mana;

import lombok.Getter;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.nebulamc.plugin.Nebula;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
public class ManaBar implements Listener {

    private final Player player;
    private int maxMana;
    private int mana;
    private int regenRate; //mana regenerated per second

    public ManaBar(Player p){
        player = p;
        maxMana = 100;
        regenRate = 3;
    }
    public void tickManaBar(){
        new BukkitRunnable(){
            public void run(){
                if (mana < maxMana){
                    mana += regenRate;
                    if (mana > maxMana){
                        mana = maxMana;
                    }
                }
                player.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("Mana: " + mana + "/" + maxMana));
            }
        }.runTaskTimer(Nebula.getInstance(), 0 ,20);
    }


}

