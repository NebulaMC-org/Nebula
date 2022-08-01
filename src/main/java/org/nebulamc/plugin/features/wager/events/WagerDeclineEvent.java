package org.nebulamc.plugin.features.wager.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.nebulamc.plugin.features.wager.Wager;

@Getter
@AllArgsConstructor
public class WagerDeclineEvent extends Event {

    private Wager wager;
    private Player player;

    @Override
    public @NotNull HandlerList getHandlers() {
        return null;
    }

}
