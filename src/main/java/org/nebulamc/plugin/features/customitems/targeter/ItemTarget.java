
//refacted from SupremeItem source code
//author: jummes

package org.nebulamc.plugin.features.customitems.targeter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
@Getter
public class ItemTarget implements Target {

    private final ItemStack target;
    private final LivingEntity owner;

    @Override
    public Location getLocation() {
        return owner.getLocation();
    }
}
