package org.nebulamc.plugin.features.customitems.items.combat;

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
import org.nebulamc.plugin.features.customitems.items.CustomItem;
import org.nebulamc.plugin.features.customitems.source.EntitySource;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;
import org.nebulamc.plugin.features.playerdata.PlayerData;
import org.nebulamc.plugin.features.playerdata.PlayerManager;
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
                "&eRight-click to slam nearby enemies upwards!", "&eDeal extra damage to airborn enemies!");
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

            LivingEntity entity = ((LivingEntity) event.getEntity());

            if (!entity.isOnGround() && !entity.isUnderWater()){

                if (Utils.canDamage(player, entity)){

                    player.playSound(entity.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1.5f, 1f);

                    new PushAction(3.5, 0.8, true).execute(new EntityTarget(entity), new EntitySource(player));

                    event.setDamage(event.getDamage() + 13);
                }
            }
        }
    }

    CylindricArea damageArea = new CylindricArea(new Vector(0, -1, 0), 3, 4.5, false);
    CylindricArea particleArea = new CylindricArea(new Vector(0, 1, 0), 1, 4.5, false);

    Action slamAction = new EntitiesInAreaAction(
       damageArea, new PushAction(0, 0.7, false), false
    );

    Action particleAction = new BlocksInAreaAction(
       particleArea, new ParticleAction(Particle.CRIT, 1, 0.3, 0.3, 0.3, 0)
    );

    SoundAction soundAction = new SoundAction(Sound.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, 1, 0.4f);

    @Override
    public void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {

        PlayerData playerData = PlayerManager.playerData.get(player.getUniqueId());
        String name = getClass().getSimpleName();

        if (playerData.cooldownOver(name)){
            new ListAction(
                    slamAction, particleAction, soundAction
            ).execute(new EntityTarget(player), new EntitySource(player));

            playerData.setItemCooldown(name, 0.75);
        }

    }

    @Override
    public void handleOffHandClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        handleRightClick(player, itemStack, event);
    }
}
