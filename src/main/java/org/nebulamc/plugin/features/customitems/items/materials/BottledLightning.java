package org.nebulamc.plugin.features.customitems.items.materials;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.nebulamc.plugin.features.customitems.actions.LightningAction;
import org.nebulamc.plugin.features.customitems.actions.NullAction;
import org.nebulamc.plugin.features.customitems.actions.ProjectileAction;
import org.nebulamc.plugin.features.customitems.entity.GenericEntity;
import org.nebulamc.plugin.features.customitems.items.CustomItem;
import org.nebulamc.plugin.features.customitems.source.EntitySource;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;
import org.nebulamc.plugin.utils.Utils;

public class BottledLightning extends CustomItem {

    @Override
    public String getName() {
        return "&fBottled Lightning";
    }

    @Override
    public Material getMaterial() {
        return Material.EXPERIENCE_BOTTLE;
    }

    @Override
    public int getModelData() {
        return 1;
    }

    ProjectileAction projAction = new ProjectileAction(30, 1,
            new LightningAction(true),
            new NullAction(),
            new LightningAction(true),
            new NullAction(),
            new GenericEntity(EntityType.THROWN_EXP_BOTTLE), 0.5, 50, 0, false, false);

    @Override
    public void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        event.setCancelled(true);
        itemStack.setAmount(1);
        if (Utils.removeItem(player, itemStack)){
            projAction.execute(new EntityTarget(player), new EntitySource(player));
        }
    }
}
