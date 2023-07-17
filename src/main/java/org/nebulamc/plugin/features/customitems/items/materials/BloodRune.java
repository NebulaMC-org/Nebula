package org.nebulamc.plugin.features.customitems.items.materials;

import org.bukkit.Material;
import org.nebulamc.plugin.features.customitems.items.CustomItem;

public class BloodRune extends CustomItem {

    @Override
    public String getName() {
        return "&fBlood Rune";
    }

    @Override
    public Material getMaterial() {
        return Material.GRAY_DYE;
    }

    @Override
    public int getModelData() {
        return 1;
    }
}
