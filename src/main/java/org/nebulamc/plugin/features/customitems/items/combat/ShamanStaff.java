package org.nebulamc.plugin.features.customitems.items.combat;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.nebulamc.plugin.features.customitems.actions.*;
import org.nebulamc.plugin.features.customitems.area.CylindricArea;
import org.nebulamc.plugin.features.customitems.area.SphericArea;
import org.nebulamc.plugin.features.customitems.items.CustomItem;
import org.nebulamc.plugin.features.customitems.source.EntitySource;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;
import org.nebulamc.plugin.features.playerdata.ManaBar;
import org.nebulamc.plugin.features.playerdata.PlayerManager;
import org.nebulamc.plugin.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ShamanStaff extends CustomItem {
    @Override
    public String getName() {
        return "&eShaman Staff";
    }

    @Override
    public Material getMaterial() {
        return Material.STICK;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&7Mana Use: &b40", "\n", "&eRight-click to heal nearby players!");
    }

    @Override
    public int getModelData() {
        return 0;
    }

    @Override
    public void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        SphericArea area = new SphericArea(new Vector(0, 0.75, 0), 8, false);
        Collection<LivingEntity> nearbyEntities = area.entitiesInside(player.getLocation(), new EntityTarget(player), new EntitySource(player));
        Collection<LivingEntity> nearbyPlayers = new ArrayList<>();
        for (LivingEntity e : nearbyEntities){
            if (e instanceof Player && !(e.equals(player))){
                nearbyPlayers.add(e);
            }
        }
        player.sendMessage("Size " + nearbyPlayers.size());
        if (nearbyPlayers.size() > 0) {
            ManaBar mana = PlayerManager.getPlayerData(player).getManaBar();
            if (mana.getMana() >= 40) {
                mana.subtractMana(40);
                new ListAction(
                        new EntitiesInAreaAction(
                                area,
                                new ListAction(
                                        new HealAction(10 / nearbyPlayers.size()),
                                        new ChangeRelativeTargetAction(
                                                new ParticleAction(Particle.HEART, (10/nearbyPlayers.size()), 0.5, 0.5, 0.5, 0),
                                                new Vector(0, 0.5, 0)
                                        )
                                )
                        ),
                        new BlocksInAreaAction(
                                new CylindricArea(new Vector(0, 0.5, 0), 1, 8, true),
                                new ParticleAction(Particle.COMPOSTER, 1, 0, 0, 0, 0)
                        )

                ).execute(new EntityTarget(player), new EntitySource(player));
                player.sendMessage(Utils.colorize("&aHealed " + nearbyEntities.size() + " &aplayers for " + 10/nearbyPlayers.size() + " &ahealth each."));
            }
        }
    }

    @Override
    public void handleOffHandClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        handleRightClick(player, itemStack, event);
    }

}
