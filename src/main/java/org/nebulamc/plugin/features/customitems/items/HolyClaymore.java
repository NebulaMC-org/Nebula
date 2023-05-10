package org.nebulamc.plugin.features.customitems.items;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.nebulamc.plugin.features.customitems.actions.*;
import org.nebulamc.plugin.features.customitems.area.CylindricArea;
import org.nebulamc.plugin.features.customitems.source.EntitySource;
import org.nebulamc.plugin.features.customitems.source.LocationSource;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;
import org.nebulamc.plugin.features.customitems.targeter.LocationTarget;
import org.nebulamc.plugin.features.playerdata.PlayerData;
import org.nebulamc.plugin.features.playerdata.PlayerManager;
import org.nebulamc.plugin.utils.Utils;

import java.util.Arrays;
import java.util.List;

public class HolyClaymore extends CustomItem{
    @Override
    public String getName() {
        return "&dHoly Claymore";
    }

    @Override
    public boolean isUnbreakable() {
        return true;
    }

    @Override
    public Material getMaterial() {
        return Material.NETHERITE_SWORD;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&7Mana Use: &b50", "\n",
                "&eRight-click to create holy ground.", "&eDeal bonus damage when inside the field!");
    }

    @Override
    public int getModelData() {
        return 3;
    }

    ListAction onHitAction = new ListAction(
            new ParticleAction(Particle.SCULK_SOUL, 3, 0, 1, 0, 0.1),
            new ParticleAction(Particle.SOUL_FIRE_FLAME, 3, 0, 1, 0, 0.1),
            new SoundAction(Sound.ENTITY_GENERIC_BURN, 0.6f, 1f)
    );

    @Override
    public void handleAttackEntity(Player player, ItemStack itemStack, EntityDamageByEntityEvent event){
        PlayerData data = PlayerManager.getPlayerData(player);
        if (event.getDamage() >= 7 && Utils.canDamage(player, event.getEntity())){
            if (data.isInHolyGround() && event.getEntity() instanceof LivingEntity){
                event.setDamage(event.getDamage() + 9);
                onHitAction.execute(new EntityTarget((LivingEntity) event.getEntity()), new EntitySource(player));
            }
        }

    }

    @Override
    public void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event){
        String name = getClass().getSimpleName();
        PlayerData data = PlayerManager.getPlayerData(player);
        if (data.cooldownOver(name) && data.getManaBar().getMana() >= 50){
            data.setItemCooldown(name, 22.75f);
            data.getManaBar().subtractMana(50);
            new ListAction(
                new ParticleAction(Particle.SOUL_FIRE_FLAME, 15, 0, 1, 0, 0.7),
                new SoundAction(Sound.BLOCK_BEACON_POWER_SELECT, 3, 1.5f),
                new BlocksInAreaAction(
                        new CylindricArea(new Vector(0, -3, 0), 7, 7, false),
                        new FakeBlockAction(Material.SMOOTH_QUARTZ, 465 , true, Arrays.asList(Material.AIR, Material.CAVE_AIR), true)
                ),
                new TimedAction(
                        5, 5, 90,
                        new ListAction(
                                new BlocksInAreaAction(
                                        new CylindricArea(new Vector(0, -2, 0), 6, 7, true),
                                        new ParticleAction(Particle.SOUL_FIRE_FLAME, 1, 0, 0, 0, 0)
                                ),
                                new EntitiesInAreaAction(
                                        new CylindricArea(new Vector(0, -2, 0), 8, 7, true),
                                        new SetHolyGround(0.25f), true
                                )
                        )
                ),
                new TimedAction(
                        5, 50, 8,
                        new ListAction(
                                new SoundAction(Sound.BLOCK_BEACON_AMBIENT, 3, 2)
                        )
                )
            ).execute(new LocationTarget(player.getLocation()), new LocationSource(player.getLocation(), player));

        }
    }

    @Override
    public List<EquipmentSlot> activeSlots() {
        return Arrays.asList(EquipmentSlot.HAND);
    }

}
