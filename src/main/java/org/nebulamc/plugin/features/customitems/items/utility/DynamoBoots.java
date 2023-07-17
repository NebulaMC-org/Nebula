package org.nebulamc.plugin.features.customitems.items.utility;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.nebulamc.plugin.features.customitems.items.CustomItem;
import org.nebulamc.plugin.features.playerdata.PlayerData;
import org.nebulamc.plugin.features.playerdata.PlayerManager;

import java.util.Arrays;
import java.util.List;

public class DynamoBoots extends CustomItem {
    @Override
    public String getName() {
        return "&fDynamo Boots";
    }

    @Override
    public Material getMaterial() {
        return Material.DIAMOND_BOOTS;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("\n", "&eThe longer you run, the faster you go!");
    }

    @Override
    public List<EquipmentSlot> activeSlots() {
        return Arrays.asList(EquipmentSlot.FEET);
    }

    @Override
    public void doTimerAction(Player player) {
        PlayerData data = PlayerManager.getPlayerData(player);
        if (player.isSprinting()){
            data.setMomentum(data.getMomentum() + 4);
        } else {
            data.setMomentum(0);
        }
        if (data.getMomentum() >= 120){
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10, 2));
        } else if (data.getMomentum() >= 80){
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10, 1));
        } else if (data.getMomentum() >= 40){
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10, 0));
        }
    }

    @Override
    public boolean hasTimerAction() {
        return true;
    }

    @Override
    public int getTimerPeriod() {
        return 4;
    }
}
