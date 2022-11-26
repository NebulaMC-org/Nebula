
//refacted from SupremeItem source code
//author: jummes

package org.nebulamc.plugin.features.customitems.targeter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;

@AllArgsConstructor
@Getter
public class LocationTarget implements Target {

    private final Location target;

    @Override
    public Location getLocation() {
        return target;
    }
}
