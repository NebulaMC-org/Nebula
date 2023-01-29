package org.nebulamc.plugin.features.customitems.actions;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.nebulamc.plugin.features.customitems.source.Source;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;
import org.nebulamc.plugin.features.customitems.targeter.Target;
import org.nebulamc.plugin.features.playerdata.PlayerData;
import org.nebulamc.plugin.features.playerdata.PlayerManager;
import org.nebulamc.plugin.utils.Utils;

public class DamageAction extends Action {

    double damage;

    public DamageAction(double damage){
        this.damage = damage;
    }

    @Override
    public void execute(Target target, Source source) {
        if (Utils.canDamage(target, source)){
            if (target instanceof EntityTarget){
                LivingEntity entityTarget = ((EntityTarget) target).getTarget();
                if (source instanceof Player){
                    PlayerData data = PlayerManager.getPlayerData((Player) source.getCaster());
                    entityTarget.damage(damage + damage * data.getDamageModifier(), source.getCaster());
                } else {
                    entityTarget.damage(damage, source.getCaster());
                }
            }
        }





    }
}
