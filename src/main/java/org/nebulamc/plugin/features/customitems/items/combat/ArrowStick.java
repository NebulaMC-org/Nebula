package org.nebulamc.plugin.features.customitems.items.combat;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.nebulamc.plugin.features.customitems.actions.DamageAction;
import org.nebulamc.plugin.features.customitems.actions.NullAction;
import org.nebulamc.plugin.features.customitems.actions.ParticleAction;
import org.nebulamc.plugin.features.customitems.actions.ProjectileAction;
import org.nebulamc.plugin.features.customitems.entity.GenericEntity;
import org.nebulamc.plugin.features.customitems.items.CustomItem;
import org.nebulamc.plugin.features.customitems.source.EntitySource;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;

public class ArrowStick extends CustomItem {
    @Override
    public String getName() {
        return "&eArrow Stick";
    }

    @Override
    public Material getMaterial() {
        return Material.STICK;
    }

    ProjectileAction projAction = new ProjectileAction(50, 2.5,
            new DamageAction(5), new NullAction(), new NullAction(), new ParticleAction(Particle.CRIT, 1, 0, 0, 0, 0),
            new GenericEntity(EntityType.ARROW), 1, 200, 25, false, false);

    @Override
    public void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        projAction.execute(new EntityTarget(player), new EntitySource(player));
    }
}
