package org.nebulamc.plugin.features.customitems.actions;

import org.bukkit.Location;
import org.nebulamc.plugin.features.customitems.source.Source;
import org.nebulamc.plugin.features.customitems.targeter.Target;

public class LightningAction extends Action{

    boolean damage;

    @Override
    public void execute(Target target, Source source) {
        Location loc = target.getLocation();
        if (damage){
            loc.getWorld().strikeLightning(loc);
        } else {
            loc.getWorld().strikeLightningEffect(loc);
        }
    }

    public LightningAction(boolean damage){
        this.damage = damage;
    }
}
