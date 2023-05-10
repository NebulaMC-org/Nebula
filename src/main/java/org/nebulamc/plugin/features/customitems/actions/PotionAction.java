package org.nebulamc.plugin.features.customitems.actions;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.nebulamc.plugin.features.customitems.source.Source;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;
import org.nebulamc.plugin.features.customitems.targeter.Target;

public class PotionAction extends Action{

    PotionEffectType effect;
    int duration = 0; //ticks
    int amplifier = 0;

    public PotionAction(PotionEffectType effect, int duration, int amplifier){
        this.effect = effect;
        this.duration = duration;
        this.amplifier = amplifier;
    }

    @Override
    public void execute(Target target, Source source) {
        if (target instanceof EntityTarget){
            ((EntityTarget) target).getTarget().addPotionEffect(new PotionEffect(effect, duration, amplifier));
        }
    }
}
