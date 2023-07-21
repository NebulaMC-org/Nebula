package org.nebulamc.plugin.features.customitems.items.materials;

import org.bukkit.Material;
import org.nebulamc.plugin.features.customitems.items.CustomItem;

public class ReinforcedCopper extends CustomItem {

    @Override
    public String getName() {
        return "&fReinforced Copper";
    }

    @Override
    public Material getMaterial() {
        return Material.COPPER_INGOT;
    }

    @Override
    public int getModelData() {
        return 1;
    }
}
