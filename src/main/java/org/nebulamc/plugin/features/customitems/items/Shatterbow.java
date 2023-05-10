package org.nebulamc.plugin.features.customitems.items;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.nebulamc.plugin.features.customitems.actions.*;
import org.nebulamc.plugin.features.customitems.entity.GenericEntity;
import org.nebulamc.plugin.features.customitems.source.EntitySource;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;
import org.nebulamc.plugin.utils.Utils;

import java.util.Arrays;
import java.util.List;

public class Shatterbow extends CustomItem{
    @Override
    public String getName() {
        return "&fShatterbow";
    }

    @Override
    public Material getMaterial() {
        return Material.BOW;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("\n", "&eShoot many arrows in a cone-shaped trajectory.");
    }

    @Override
    public void handleShootBow(Player player, ItemStack itemStack, EntityShootBowEvent event) {
        double speed;
        double gravity;
        float pitch;
        ItemMeta meta = event.getBow().getItemMeta();

        ListAction tickActions = new ListAction();
        ListAction damageActions = new ListAction();

        double damage = Utils.calculateBowDamage(event);
        double force = event.getForce();
        if (force >= 1){
            speed = 60;
            gravity = 0.4;
            pitch = 1.2f;
            tickActions.addAction(new ParticleAction(Particle.CRIT, 1, 0, 0, 0, 0));
        } else if (force >= 0.2){
            speed = 30;
            gravity = 1;
            pitch = 1f;
        } else {
            speed = 10;
            gravity = 3.8;
            pitch = 0.8f;
        }

        damageActions.addAction(new DamageAction(damage/2));
        if (meta.hasEnchant(Enchantment.ARROW_FIRE)){
            tickActions.addAction(new ParticleAction(Particle.FLAME, 1, 0, 0, 0, 0));
            damageActions.addAction(new SetOnFireAction(100));
        }
        if (meta.hasEnchant(Enchantment.ARROW_KNOCKBACK)){
            damageActions.addAction(new PushAction(1 * meta.getEnchantLevel(Enchantment.ARROW_KNOCKBACK), 0.25, true));
        }

        ProjectileAction projAction = new ProjectileAction(speed, gravity,
                damageActions,
                new NullAction(),
                new NullAction(),
                tickActions,
                new GenericEntity(EntityType.ARROW), 0.5, 50, 20, false, false);


        event.setCancelled(true);
        for (int i = 0; i < 10; i++){
            projAction.execute(new EntityTarget(player), new EntitySource(player));
        }
        player.playSound(player.getLocation(), Sound.ENTITY_SKELETON_SHOOT, 2.5f, pitch);


    }
}
