package org.nebulamc.plugin.features.playerdata;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.nebulamc.plugin.Nebula;

import java.time.Instant;
import java.util.UUID;

public class ManaBar implements Listener {

    private final UUID id;
    private float maxMana;
    private float mana;
    private float regenRate; //mana regenerated per 0.25s
    private long lastManaUse = Instant.now().getEpochSecond();

    public ManaBar(UUID i){
        id = i;
        maxMana = 100;
        regenRate = 1;
    }

    public float getMana() {
        return mana;
    }

    public void setMana(float m){
        mana = m;
        if (mana < 0){
            mana = 0;
        }
        lastManaUse = Instant.now().getEpochSecond();
    }

    public void subtractMana(float m){
        setMana(mana - m);
    }

    public void addMana(float m){
        setMana(mana + m);
    }

    public void setMaxMana(int m){
        maxMana = m;
        if (maxMana < 0){
            maxMana = 0;
        }
    }

    public void setRegenRate(float r){
        regenRate = r;
    }

    public void tickManaBar(){
        Player p = Bukkit.getPlayer(id);
        new BukkitRunnable(){
            public void run(){
                if (!p.isOnline()){
                    cancel();
                }
                if (mana < maxMana && Instant.now().getEpochSecond() - lastManaUse >= 2){
                    mana += regenRate;
                    if (mana > maxMana){
                        mana = maxMana;
                    }
                }

                p.sendActionBar(Component.text("Mana: ").color(NamedTextColor.GRAY)
                        .append(Component.text((int) mana + "/" + (int) maxMana).color(NamedTextColor.AQUA)));
            }
        }.runTaskTimer(Nebula.getInstance(), 0 ,5);
    }

}

