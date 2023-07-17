package org.nebulamc.plugin.features.customitems.items.combat;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.nebulamc.plugin.features.customitems.items.CustomItem;
import org.nebulamc.plugin.features.playerdata.PlayerData;
import org.nebulamc.plugin.features.playerdata.PlayerManager;

import java.util.Arrays;
import java.util.List;

public class SacrificialBlade extends CustomItem {
    @Override
    public String getName() {
        return "&eSacrificial Blade";
    }

    @Override
    public Material getMaterial() {
        return Material.NETHERITE_SWORD;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("\n",
                "&eDeal higher damage at lower health.",
                "&eRight-click to sacrifice yourself.");
    }

    @Override
    public int getModelData() {
        return 2;
    }

    @Override
    public void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        PlayerData playerData = PlayerManager.playerData.get(player.getUniqueId());
        String name = getClass().getSimpleName();

        if (player.getHealth() > 4 && playerData.cooldownOver(name)){
            player.damage(0.1);
            player.setHealth(player.getHealth() - 4);
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT_SWEET_BERRY_BUSH, 1.0f, 0f);
            playerData.setItemCooldown(name, 0.25);
        } else if (player.getHealth() > 1  && playerData.cooldownOver(name)){
            player.damage(0.1);
            player.setHealth(player.getHealth() - (player.getHealth() - 1));
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT_SWEET_BERRY_BUSH, 1.0f, 0f);
            playerData.setItemCooldown(name, 0.25);
        }
    }

    @Override
    public void handleAttackEntity(Player player, ItemStack itemStack, EntityDamageByEntityEvent event) {
        if (event.getDamage() >= 8){
            double health = player.getHealth();
            Location entityLocation = event.getEntity().getLocation().add(0, 1.2, 0);

            if (health <= 2){
                event.setDamage(event.getDamage() + 10);
                player.getWorld().spawnParticle(Particle.ITEM_CRACK, entityLocation, 10, 0.5, 0.5, 0.5, 0, new ItemStack(Material.REDSTONE_BLOCK));
                player.playSound(entityLocation, Sound.BLOCK_WOOD_BREAK, 3f, 0.5f);
            } else if (health <= 4){
                event.setDamage(event.getDamage() + 5);
                player.getWorld().spawnParticle(Particle.ITEM_CRACK, entityLocation, 5, 0.5, 0.5, 0.5, 0, new ItemStack(Material.REDSTONE_BLOCK));
                player.playSound(entityLocation, Sound.BLOCK_WOOD_BREAK, 3f, 0.7f);
            } else if (health <= 6){
                event.setDamage(event.getDamage() + 2);
                player.getWorld().spawnParticle(Particle.ITEM_CRACK, entityLocation, 2, 0.5, 0.5, 0.5, 0, new ItemStack(Material.REDSTONE_BLOCK));
                player.playSound(entityLocation, Sound.BLOCK_WOOD_BREAK, 3f, 0.9f);
            }
        }
    }

    @Override
    public List<EquipmentSlot> activeSlots() {
        return Arrays.asList(EquipmentSlot.HAND);
    }
}
