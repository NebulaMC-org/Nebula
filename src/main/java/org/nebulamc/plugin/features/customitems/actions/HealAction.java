package org.nebulamc.plugin.features.customitems.actions;

import org.bukkit.entity.LivingEntity;
import org.nebulamc.plugin.features.customitems.source.Source;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;
import org.nebulamc.plugin.features.customitems.targeter.Target;

public class HealAction extends Action{

    double health;

    @Override
    public void execute(Target target, Source source) {
        if (target instanceof EntityTarget){
            LivingEntity entity = ((EntityTarget) target).getTarget();
            entity.setHealth(entity.getHealth() + health);
        }
    }

    public HealAction(double health){
        this.health = health;
    }
}
