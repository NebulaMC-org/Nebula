package org.nebulamc.plugin.features.customitems.actions;

import org.nebulamc.plugin.features.customitems.source.Source;
import org.nebulamc.plugin.features.customitems.targeter.Target;

public abstract class Action {
    public abstract void execute(Target target, Source source);
}
