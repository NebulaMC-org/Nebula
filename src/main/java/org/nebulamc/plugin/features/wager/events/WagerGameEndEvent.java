package org.nebulamc.plugin.features.wager.events;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.nebulamc.plugin.features.wager.Wager;

@Getter
public class WagerGameEndEvent extends WagerEvent {

    private Player winner;

    public WagerGameEndEvent(Wager wager, Player w) {
        super(wager);
        winner = w;
    }

}
