package org.nebulamc.plugin.features.customitems.items;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.nebulamc.plugin.features.customitems.actions.*;
import org.nebulamc.plugin.features.customitems.area.SphericArea;
import org.nebulamc.plugin.features.playerdata.PlayerData;
import org.nebulamc.plugin.features.playerdata.PlayerManager;
import org.nebulamc.plugin.utils.Utils;

import java.util.Arrays;
import java.util.List;

public class SolarFlare extends CustomItem {

    ListAction tickActions = new ListAction(new ParticleAction(Particle.FLAME, 2, 0.05, 0.05, 0.05, 0.1),
            new EntitiesInAreaAction(
                    new SphericArea(new Vector(), 6, false),
                    new ListAction(new DamageAction(12), new SetOnFireAction(180))));

    ListAction endActions =
            new ListAction(new ExplosionAction(25, 1.5, 220),
            new ParticleAction(Particle.FLAME, 15, 0, 0, 0, 0.4));

    @Override
    public void handleShootBow(Player player, ItemStack itemStack, EntityShootBowEvent event) {
        PlayerData data = PlayerManager.getPlayerData(player);
        if (data.getManaBar().getMana() >= 50){
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_BURN, 3f, 0f);
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1.5f, 2f);
            Utils.rayCast(player, 200, 2, true,
                    tickActions,
                    new NullAction(),
                    endActions);
            event.setCancelled(true);
            data.getManaBar().subtractMana(50);
        } else {

        }
    }

    @Override
    public String getName() {
        return "&dSolar Flare";
    }

    @Override
    public Material getMaterial() {
        return Material.BOW;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&7Mana Use: &b50", "\n", "&eRelease a scorching beam of fire", "&ewhen you have enough mana!");
    }

    @Override
    public int getModelData() {
        return 1;
    }
}
