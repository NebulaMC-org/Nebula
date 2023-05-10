package org.nebulamc.plugin.features.customitems.actions;

import org.bukkit.Sound;
import org.nebulamc.plugin.features.customitems.source.Source;
import org.nebulamc.plugin.features.customitems.targeter.Target;

public class SoundAction extends Action{

    Sound sound;
    float volume;
    float pitch;

    public SoundAction(Sound sound, float volume, float pitch){
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    @Override
    public void execute(Target target, Source source) {
        target.getLocation().getWorld().playSound(target.getLocation(), sound, volume, pitch);
    }
}
