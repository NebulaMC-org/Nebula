package org.nebulamc.plugin.features.mana;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.nebulamc.plugin.Nebula;

import java.util.UUID;

public class ManaBar implements Listener {

    private final UUID id;
    private int maxMana;
    private int mana;
    private int regenRate; //mana regenerated per 0.25s

    public ManaBar(UUID i){
        id = i;
        maxMana = 100;
        regenRate = 1;
    }

    public int getMana() {
        return mana;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMana(int m){
        mana = m;
        if (mana < 0){
            mana = 0;
        }
    }

    public void tickManaBar(){
        Player p = Bukkit.getPlayer(id);
        new BukkitRunnable(){
            public void run(){
                if (!p.isOnline()){
                    cancel();
                }
                if (mana < maxMana){
                    mana += regenRate;
                    if (mana > maxMana){
                        mana = maxMana;
                    }
                }

                p.sendActionBar(Component.text("Mana: ").color(NamedTextColor.GRAY)
                        .append(Component.text(mana + "/" + maxMana).color(NamedTextColor.AQUA)));
            }
        }.runTaskTimer(Nebula.getInstance(), 0 ,5);
    }


}

