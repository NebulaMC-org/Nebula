package org.nebulamc.plugin.features.customitems.actions;

import org.bukkit.entity.LivingEntity;
import org.nebulamc.plugin.features.customitems.source.Source;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;
import org.nebulamc.plugin.features.customitems.targeter.Target;
import org.nebulamc.plugin.utils.Utils;

public class DamageAction extends Action {

    double damage;

    public DamageAction(double damage){
        this.damage = damage;
    }

    @Override
    public void execute(Target target, Source source) {
        if (Utils.canDamage(target, source)){
            LivingEntity entityTarget = ((EntityTarget) target).getTarget();
            entityTarget.damage(damage, source.getCaster());
        }





    }
}
