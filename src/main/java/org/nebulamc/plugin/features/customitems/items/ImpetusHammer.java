package org.nebulamc.plugin.features.customitems.items;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.nebulamc.plugin.utils.Utils;

import java.util.Arrays;
import java.util.List;

public class ImpetusHammer extends CustomItem {

    @Override
    public String getName() {
        return "&dImpetus Hammer";
    }

    @Override
    public boolean isUnbreakable() {
        return true;
    }

    @Override
    public Material getMaterial() {
        return Material.NETHERITE_AXE;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("\n",
                "&eDeal more damage at higher velocities!");
    }

    @Override
    public int getModelData() {
        return 2;
    }

    @Override
    public List<EquipmentSlot> activeSlots() {
        return Arrays.asList(EquipmentSlot.HAND);
    }

    @Override
    public void handleAttackEntity(Player player, ItemStack itemStack, EntityDamageByEntityEvent event) {
        if (event.getDamage() >= 9){
            Vector direction = player.getLocation().getDirection();
            Vector velocity = player.getVelocity();
            Entity entity = event.getEntity();

            double xDamage = velocity.getX() * direction.getX() * 10;
            double yDamage = velocity.getY() * direction.getY() * 10;
            double zDamage = velocity.getZ() * direction.getZ() * 10;
            int addedDamage = (int) Math.abs(xDamage + yDamage + zDamage);

            if (addedDamage >= 2 && entity instanceof LivingEntity){
                Location location = player.getLocation();
                event.setDamage(event.getDamage() + addedDamage);
                entity.setVelocity(velocity.multiply(
                        Utils.absoluteVector(direction)).
                        multiply(5).setY(entity.getVelocity().
                        getY() + 0.2));

                if (addedDamage >= 20) {
                    player.playSound(location, Sound.BLOCK_ANVIL_PLACE, 1.5f, 0f);
                    player.playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 1.5f, 2f);
                } else if (addedDamage >= 15){
                    player.playSound(location, Sound.BLOCK_ANVIL_PLACE, 1.5f, 0f);
                } else if (addedDamage >= 10){
                    player.playSound(location, Sound.BLOCK_ANVIL_PLACE, 1.5f, 1f);
                } else {
                    player.playSound(location, Sound.BLOCK_ANVIL_PLACE, 1.5f, 2f);
                }

            }
        }
    }
}
