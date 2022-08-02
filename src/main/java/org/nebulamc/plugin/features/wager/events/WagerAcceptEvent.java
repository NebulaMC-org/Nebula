package org.nebulamc.plugin.features.wager.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.nebulamc.plugin.features.wager.Wager;

@Getter
public class WagerAcceptEvent extends WagerEvent {

    private Player player;

    public WagerAcceptEvent(Wager wager, Player p) {
        super(wager);
        player = p;
    }
}
