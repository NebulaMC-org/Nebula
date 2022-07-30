package spectromeda.nebula.features.loottable;

import org.bukkit.inventory.ItemStack;

public class Entry {

    private int weight;
    private ItemStack item;
    private double chance;

    public Entry(ItemStack item, int weight){
        this.item = item;
        this.weight = weight;
    }

    public int getWeight(){
        return weight;
    }

    public void setChance(double chance){
        this.chance = chance;
    }

    public double getChance(){
        return chance;
    }

    public ItemStack getItem(){
        return item.clone();
    }
}
