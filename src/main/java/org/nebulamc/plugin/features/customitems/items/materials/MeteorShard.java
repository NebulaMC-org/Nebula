package org.nebulamc.plugin.features.customitems.items.materials;

import org.bukkit.Material;
import org.nebulamc.plugin.features.customitems.items.CustomItem;

public class MeteorShard extends CustomItem {

    @Override
    public String getName() {
        return "&fMeteor Shard";
    }

    @Override
    public Material getMaterial() {
        return Material.NETHERITE_SCRAP;
    }

    @Override
    public int getModelData() {
        return 1;
    }
}
