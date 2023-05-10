package org.nebulamc.plugin.features.playerdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerData {

    private final UUID id;
    private ManaBar manaBar;
    public HashMap<String, Double> itemCooldowns = new HashMap<>();
    public HashMap<String, Double> itemDamage = new HashMap<>();
    public List<String> activeItems = new ArrayList<>();

    private int jetpackFuel;
    private int maxJetpackFuel = 100;
    private int momentum;
    private float damageModifier;
    private long lastInHolyGround;

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

    public void setItemDamage(String name, double damage){
        itemDamage.put(name, damage);
    }

    public void addItemDamage(String name, double damage){
        if (itemDamage.containsKey(name)){
            itemDamage.put(name, itemDamage.get(name) + 1);
        } else {
            itemDamage.put(name, damage);
        }
    }

    public double getItemDamage(String name){
        return itemDamage.get(name);
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

    public void setInHolyGround(float buffer){
        lastInHolyGround = System.currentTimeMillis() + (long) (1000 * buffer);;
    }

    public boolean isInHolyGround(){
        return lastInHolyGround >= System.currentTimeMillis();
    }
}
