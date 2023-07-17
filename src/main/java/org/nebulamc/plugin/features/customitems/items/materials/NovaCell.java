package org.nebulamc.plugin.features.customitems.items.materials;

import org.bukkit.Material;
import org.nebulamc.plugin.features.customitems.items.CustomItem;

public class NovaCell extends CustomItem {

    @Override
    public String getName() {
        return "&eNova Cell";
    }

    @Override
    public Material getMaterial() {
        return Material.IRON_INGOT;
    }

    @Override
    public int getModelData() {
        return 1;
    }
}
