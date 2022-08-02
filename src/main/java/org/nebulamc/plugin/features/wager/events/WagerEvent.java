package org.nebulamc.plugin.features.wager.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.nebulamc.plugin.features.wager.Wager;

@Getter
@AllArgsConstructor
public class WagerEvent extends Event {

    private Wager wager;

    private static final HandlerList handlers = new HandlerList();

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
