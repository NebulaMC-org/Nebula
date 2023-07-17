package org.nebulamc.plugin.features.customitems.items.combat;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.nebulamc.plugin.features.customitems.actions.*;
import org.nebulamc.plugin.features.customitems.entity.GenericEntity;
import org.nebulamc.plugin.features.customitems.items.CustomItem;
import org.nebulamc.plugin.features.customitems.source.EntitySource;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;
import org.nebulamc.plugin.utils.Utils;

import java.util.Arrays;
import java.util.List;

public class RepeaterCrossbow extends CustomItem {
    @Override
    public String getName() {
        return "&fRepeater Crossbow";
    }

    @Override
    public Material getMaterial() {
        return Material.CROSSBOW;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("\n", "&eShoots 10 arrows in quick succession!");
    }

    @Override
    public void handleShootBow(Player player, ItemStack itemStack, EntityShootBowEvent event) {
        ItemMeta meta = event.getBow().getItemMeta();

        double damage = Utils.calculateBowDamage(event);
        boolean piercing = false;

        ListAction tickActions = new ListAction(new ParticleAction(Particle.CRIT, 1, 0, 0, 0, 0));
        ListAction damageActions = new ListAction(new DamageAction(damage/8), new SetNoDamageTicksAction(0));

        if (meta.hasEnchant(Enchantment.PIERCING)){
            piercing = true;
        }

        ProjectileAction projAction = new ProjectileAction(60, 0.4,
                damageActions,
                new SoundAction(Sound.ITEM_CROSSBOW_SHOOT, 2.5f, 1.1f),
                new NullAction(),
                tickActions,
                new GenericEntity(event.getProjectile().getType()), 0.5, 200, 3, piercing, false);

        new TimedAction(0, 1, 10, projAction).execute(new EntityTarget(player), new EntitySource(player));
        event.setCancelled(true);
    }
}
