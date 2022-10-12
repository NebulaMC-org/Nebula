package org.nebulamc.plugin.features.playerdata;

import java.util.HashMap;
import java.util.UUID;

public class PlayerData {

    private final UUID id;
    private ManaBar manaBar;
    public HashMap<String, Double> itemCooldowns = new HashMap<>();

    public PlayerData(UUID i){
        id = i;
        manaBar = new ManaBar(i);
    }

    public ManaBar getManaBar() {
        return manaBar;
    }

    public void setItemCooldown(String name, double seconds){
        double cooldownTime = System.currentTimeMillis() + (long) (1000 * seconds);
        itemCooldowns.put(name, cooldownTime);
    }

    public boolean cooldownOver(String name){
        if (!(itemCooldowns.containsKey(name))){
            return true;
        }
        if (System.currentTimeMillis() >= itemCooldowns.get(name)){
            return true;
        }
        return false;
    }
}
