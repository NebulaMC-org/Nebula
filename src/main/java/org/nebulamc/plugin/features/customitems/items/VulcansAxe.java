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
import org.nebulamc.plugin.features.customitems.area.SphericArea;
import org.nebulamc.plugin.features.customitems.entity.NoEntity;
import org.nebulamc.plugin.features.customitems.source.EntitySource;
import org.nebulamc.plugin.features.customitems.source.LocationSource;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;
import org.nebulamc.plugin.features.playerdata.ManaBar;
import org.nebulamc.plugin.features.playerdata.PlayerManager;

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
        return Arrays.asList("&7Mana Use: &b50", "\n", "&eRight-click to rupture the earth!", "&eDeal extra damage to ignited enemies.");
    }

    @Override
    public int getModelData() {
        return 3;
    }

    @Override
    public List<EquipmentSlot> activeSlots() {
        return Arrays.asList(EquipmentSlot.HAND, EquipmentSlot.OFF_HAND);
    }

    ListAction flameHitAction = new ListAction(
            new ParticleAction(Particle.EXPLOSION_LARGE, 1, 0, 0, 0, 0),
            new SoundAction(Sound.ENTITY_GENERIC_EXPLODE, 1, 1.2f),
            new ListAction(
                    new DamageAction(16),
                    new SetOnFireAction(300),
                    new PushAction(0.5, 1.2, true)
            )
    );

    @Override
    public void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        ManaBar manaBar = PlayerManager.getPlayerData(player).getManaBar();
        if (manaBar.getMana() >= 50) {
            manaBar.subtractMana(50);
            new ProjectileAction(
                    70, 10,
                    flameHitAction,
                    new SoundAction(Sound.ENTITY_BLAZE_SHOOT, 3, 0.5f),
                    new NullAction(),
                    new ListAction(
                            new BlocksInAreaAction(
                                    new SphericArea(new Vector(0, 0, 0), 0.8, false),
                                    new ListAction(
                                            new FakeBlockAction(Material.MAGMA_BLOCK, 80, true, Arrays.asList(Material.AIR, Material.CAVE_AIR))
                                    )
                            ),
                            new BlocksInAreaAction(
                                    new CylindricArea(new Vector(0, 0, 0), 3, 0, false),
                                    new ParticleAction(Particle.LAVA, 1, 0, 0, 0, 0)
                            ),
                            new SoundAction(Sound.ENTITY_BLAZE_BURN, 0.5f, 0f)
                    ),
                    new NoEntity(), 3, 20, 0, true, true
            ).execute(new EntityTarget(player), new LocationSource(player.getLocation(), player));
        }
    }

    @Override
    public void handleAttackEntity(Player player, ItemStack itemStack, EntityDamageByEntityEvent event){
        if (event.getEntity().getFireTicks() > 0 && event.getEntityType().isAlive()){
            event.setDamage(event.getDamage() * 1.4);
            new ParticleAction(Particle.FLAME, 3, 0, 0, 0, 0.3)
                    .execute(new EntityTarget((LivingEntity) event.getEntity()), new EntitySource(player));
        }
    }
}
