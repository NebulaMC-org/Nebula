package org.nebulamc.plugin.features.customitems.items;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.nebulamc.plugin.features.customitems.actions.*;
import org.nebulamc.plugin.features.customitems.area.SphericArea;
import org.nebulamc.plugin.features.customitems.entity.NoEntity;
import org.nebulamc.plugin.features.customitems.source.LocationSource;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;

import java.util.Arrays;
import java.util.List;

public class VulcansAxe extends CustomItem{
    @Override
    public String getName() {
        return "&dVulcan's Axe";
    }

    @Override
    public Material getMaterial() {
        return Material.NETHERITE_AXE;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&7Mana Use: &b60", "\n", "&eRight-click to rupture the earth!", "&eDeal extra damage to ignited enemies.");
    }

    @Override
    public int getModelData() {
        return 0;
    }

    @Override
    public List<EquipmentSlot> activeSlots() {
        return Arrays.asList(EquipmentSlot.HAND, EquipmentSlot.OFF_HAND);
    }

    ListAction flameHitAction = new ListAction(
            new ParticleAction(Particle.EXPLOSION_LARGE, 1, 0, 0, 0, 0),
            new SoundAction(Sound.ENTITY_GENERIC_EXPLODE, 1, 1.2f),
            new EntitiesInAreaAction(
                    new SphericArea(new Vector(0, 0, 0), 1, false),
                    new ListAction(
                            new DamageAction(12),
                            new SetOnFireAction(200)
                    )
            )
    );

    ProjectileAction flameBurstProj = new ProjectileAction(
            50, 2,
            flameHitAction,
            new NullAction(),
            flameHitAction,
            new ParticleAction(Particle.FLAME, 1, 0, 0, 0, 0.075),
            new NoEntity(), 0.5, 150, 0, false, false
    );


    @Override
    public void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        new ChangeRelativeSourceAction(
                new ProjectileAction(
                        70, 10,
                        new NullAction(),
                        new NullAction(),
                        new NullAction(),
                        new ListAction(
                                new BlocksInAreaAction(
                                        new SphericArea(new Vector(0, 0, 0), 0.8, false),
                                        new ListAction(
                                                new FakeBlockAction(Material.MAGMA_BLOCK, 80, true, Arrays.asList(Material.AIR, Material.CAVE_AIR))
                                        )
                                ),
                                new ParticleAction(Particle.FLAME, 1, 0, 0, 0, 0),
                                new ChangeSourceAction(
                                        new ChangeRelativeTargetAction(
                                                flameBurstProj,
                                                new Vector(0, 5, 0)
                                        )
                                )
                        ),
                        new NoEntity(), 1, 20, 0, true, true
                ),
                player.getEyeLocation().getDirection().multiply(2)
        ).execute(new EntityTarget(player), new LocationSource(player.getLocation(), player));
    }
}
