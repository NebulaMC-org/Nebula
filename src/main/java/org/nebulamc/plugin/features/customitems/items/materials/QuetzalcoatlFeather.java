package org.nebulamc.plugin.features.customitems.items.materials;

import org.bukkit.Material;
import org.nebulamc.plugin.features.customitems.items.CustomItem;

public class QuetzalcoatlFeather extends CustomItem {

    @Override
    public String getName() {
        return "&fQueztalcoatl Feather";
    }

    @Override
    public Material getMaterial() {
        return Material.FEATHER;
    }

    @Override
    public int getModelData() {
        return 1;
    }
}
