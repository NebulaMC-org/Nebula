package org.nebulamc.plugin.features.customitems.actions;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.nebulamc.plugin.features.customitems.Action;

public class DamageAction extends Action {

    double damage;

    public DamageAction(double damage){
        this.damage = damage;
    }

    @Override
    public void execute(Player player, Location location, Entity target) {
        if (target instanceof LivingEntity){
            ((LivingEntity) target).damage(damage, player);
        }
    }
}
