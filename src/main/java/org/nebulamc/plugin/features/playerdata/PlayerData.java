package org.nebulamc.plugin.features.playerdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerData {

    private final UUID id;
    private ManaBar manaBar;
    public HashMap<String, Double> itemCooldowns = new HashMap<>();
    public List<String> activeItems = new ArrayList<>();

    private int jetpackFuel;
    private int maxJetpackFuel = 100;
    private int momentum;
    private float damageModifier;

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

    public int getJetpackFuel(){
        return jetpackFuel;
    }

    public int getJetpackMaxFuel(){
        return maxJetpackFuel;
    }

    public void setJetpackFuel(int fuel){
        jetpackFuel = fuel;
    }

    public int getMomentum(){
        return momentum;
    }

    public void setMomentum(int momentum){
        this.momentum = momentum;
    }

    public void addActiveItem(String item){
        if (!activeItems.contains(item)){
            activeItems.add(item);
        }
    }

    public void removeActiveItem(String item){
        if (activeItems.contains(item)){
            activeItems.remove(item);
        }
    }

    public float getDamageModifier(){
        return damageModifier;
    }

    public void setDamageModifier(float modifier){
        damageModifier = modifier;
    }
}
