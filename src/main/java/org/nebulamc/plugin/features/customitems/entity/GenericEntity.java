
//refactored from SupremeItem source code
//author: jummes

package org.nebulamc.plugin.features.customitems.entity;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

@Setter
@Getter
public class GenericEntity extends Entity {

    private EntityType type;

    public GenericEntity() {
        this(EntityType.ARROW);
    }

    public GenericEntity(EntityType entity) {
        this.type = entity;
    }

    @Override
    public org.bukkit.entity.Entity spawnEntity(Location l) {
        return l.getWorld().spawn(l, type.getEntityClass());
    }

    @Override
    public Entity clone() {
        return new GenericEntity(type);
    }

}
