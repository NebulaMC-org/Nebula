package org.nebulamc.plugin.features.playerdata;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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

    public float getMaxMana(){
        return maxMana;
    }

    public float getRegenRate(){
        return regenRate;
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

    public void setMaxMana(float m){
        maxMana = m;
        if (maxMana < 0){
            maxMana = 0;
        }
        if (mana > maxMana){
            mana = maxMana;
        }
    }

    public void setRegenRate(float r){
        regenRate = r;
    }

    private String getManaBarVisual(){

        Player player = Bukkit.getPlayer(id);

        String text = "";
        if (player != null && player.isOnline()) {
            if (player.isUnderWater() || player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR){
                return " ";
            }

            int tempMana = (int) (mana/2);
            int tempMaxMana = (int) (maxMana/2);


            if (tempMaxMana > 50){
                text += "\uF828\uF827".repeat((tempMaxMana - 50)/15);
            } else if (tempMaxMana < 50) {
                text += "\uF808\uF802".repeat((50 - tempMaxMana)/10);
            }
            text += "\uF82B\uF826\uF801\uE000";

            if (tempMana <= 50){
                text += "\uF801\uE002".repeat(tempMana);
            } else {
                text += "\uF801\uE002".repeat(50);
                text += "\uF801\uE300".repeat(tempMana - 50);
            }
            text += "\uF801\uE001".repeat(tempMaxMana - tempMana);
            text += "\uF801\uE000";
        }
        return text;
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

                p.sendActionBar(getManaBarVisual());
            }
        }.runTaskTimer(Nebula.getInstance(), 0 ,5);
    }

}

