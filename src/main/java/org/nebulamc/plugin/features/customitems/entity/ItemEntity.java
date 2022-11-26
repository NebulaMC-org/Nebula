package org.nebulamc.plugin.features.customitems.entity;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

//refactored from SupremeItem source code
//author: jummes

@Getter
@Setter
public class ItemEntity extends Entity {

    private final static int DEFAULT_PICKUP = 37687;
    private final static boolean DEFAULT_GRAVITY = false;

    private ItemStack item;

    private boolean gravity;

    private int pickupDelay;

    public ItemEntity() {
        this(new ItemStack(Material.STONE), DEFAULT_GRAVITY, DEFAULT_PICKUP);
    }

    public ItemEntity(ItemStack item, boolean gravity, int pickupDelay) {
        this.item = item;
        this.gravity = gravity;
        this.pickupDelay = pickupDelay;
    }

    public ItemEntity(ItemStack item, boolean gravity) {
        this.item = item;
        this.gravity = gravity;
        this.pickupDelay = DEFAULT_PICKUP;
    }

    @Override
    public org.bukkit.entity.Entity spawnEntity(Location l) {
        Item item = l.getWorld().dropItem(l, this.item);
        item.setVelocity(new Vector());
        item.setGravity(gravity);
        item.setPickupDelay(pickupDelay);
        return item;
    }

    public ItemStack getFlatItem() {
        return item.clone();
    }

    @Override
    public Entity clone() {
        return new ItemEntity(item, gravity, pickupDelay);
    }

    @Override
    public EntityType getType() {
        return EntityType.DROPPED_ITEM;
    }
}
