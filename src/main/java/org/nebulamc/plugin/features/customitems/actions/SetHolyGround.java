package org.nebulamc.plugin.features.customitems.actions;

import org.bukkit.entity.Player;
import org.nebulamc.plugin.features.customitems.source.Source;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;
import org.nebulamc.plugin.features.customitems.targeter.Target;
import org.nebulamc.plugin.features.playerdata.PlayerData;
import org.nebulamc.plugin.features.playerdata.PlayerManager;


public class SetHolyGround extends Action{

    float buffer;

    public SetHolyGround(float buffer){
        this.buffer = buffer;
    }

    @Override
    public void execute(Target target, Source source) {
        if (target instanceof EntityTarget){
            if (((EntityTarget) target).getTarget() instanceof Player){
                Player player = ((Player) ((EntityTarget) target).getTarget()).getPlayer();
                PlayerData data = PlayerManager.getPlayerData(player);
                data.setInHolyGround(buffer);
            }
        }
    }
}
