package org.nebulamc.plugin.features.customitems.actions;

import org.nebulamc.plugin.features.customitems.source.Source;
import org.nebulamc.plugin.features.customitems.targeter.Target;

import static org.bukkit.Bukkit.getServer;

public class RunCommandAction extends Action{

    String command;

    public RunCommandAction(String command){
        this.command = command;
    }

    @Override
    public void execute(Target target, Source source) {
        getServer().dispatchCommand(getServer().getConsoleSender(), command);
    }

}
