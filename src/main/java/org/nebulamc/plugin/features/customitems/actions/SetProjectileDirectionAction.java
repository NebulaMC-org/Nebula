package org.nebulamc.plugin.features.customitems.actions;

import org.bukkit.util.Vector;
import org.nebulamc.plugin.features.customitems.source.Source;
import org.nebulamc.plugin.features.customitems.targeter.ProjectileTarget;
import org.nebulamc.plugin.features.customitems.targeter.Target;

public class SetProjectileDirectionAction extends Action {

    private Vector direction;

    public SetProjectileDirectionAction(Vector direction){
        this.direction = direction;
    }

    @Override
    public void execute(Target target, Source source){
        if (target instanceof ProjectileTarget){
            ((ProjectileTarget) target).getProjectile().setDirection(direction);
        }
    }
}
