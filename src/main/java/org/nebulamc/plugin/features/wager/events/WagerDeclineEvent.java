package org.nebulamc.plugin.features.wager.events;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.nebulamc.plugin.features.wager.Wager;

@Getter
public class WagerDeclineEvent extends WagerEvent {

    private Player player;

    public WagerDeclineEvent(Wager wager, Player p) {
        super(wager);
        player = p;
    }
}
