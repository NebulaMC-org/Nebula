package org.nebulamc.plugin.features.customitems.items;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;
import org.nebulamc.plugin.features.customitems.actions.*;
import org.nebulamc.plugin.features.customitems.area.SphericArea;
import org.nebulamc.plugin.features.customitems.entity.GenericEntity;
import org.nebulamc.plugin.features.customitems.source.EntitySource;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;
import org.nebulamc.plugin.features.playerdata.PlayerData;
import org.nebulamc.plugin.features.playerdata.PlayerManager;
import org.nebulamc.plugin.utils.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FrostBow extends CustomItem{
    @Override
    public String getName() {
        return "&eFrost Bow";
    }

    @Override
    public Material getMaterial() {
        return Material.BOW;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&7Mana Use: &b20", "\n", "&eArrows freeze nearby enemies!");
    }

    @Override
    public Map<Enchantment, Integer> getEnchants() {
        return null;
    }

    @Override
    public List<ItemFlag> getFlags() {
        return null;
    }

    @Override
    public Map<Attribute, AttributeModifier> getAttributes() {
        return null;
    }

    @Override
    public int getModelData() {
        return 0;
    }

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public boolean isUnbreakable() {
        return false;
    }

    @Override
    public List<EquipmentSlot> activeSlots() {
        return null;
    }

    @Override
    public void handleLeftClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {

    }

    @Override
    public void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {

    }

    @Override
    public void handleOffHandClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {

    }

    @Override
    public void handleConsumption(Player player, ItemStack itemStack, PlayerItemConsumeEvent event) {

    }

    @Override
    public void handleDamagedByEntity(Player player, ItemStack itemStack, EntityDamageByEntityEvent event) {

    }

    @Override
    public void handleAttackEntity(Player player, ItemStack itemStack, EntityDamageByEntityEvent event) {

    }

    @Override
    public void handleDamaged(Player player, ItemStack itemStack, EntityDamageEvent event) {

    }

    @Override
    public void handlePlaceBlock(Player player, ItemStack itemStack, BlockPlaceEvent event) {

    }

    ListAction frostOrb = new ListAction(
            new BlocksInAreaAction(
                    new SphericArea(new Vector(0, 0, 0), 6, true),
                    new ParticleAction(Particle.REDSTONE, 1, 0, 0, 0, 0, new Particle.DustOptions(Color.fromRGB(183, 242, 245), 1f))
            ),
            new EntitiesInAreaAction(
                    new SphericArea(new Vector(0, 0, 0), 6, false),
                    new ListAction(
                            new FreezeAction(320),
                            new DamageAction(1)
                    ), true
            )
    );

    @Override
    public void handleShootBow(Player player, ItemStack itemStack, EntityShootBowEvent event) {
        PlayerData data = PlayerManager.getPlayerData(player);
        if (data.getManaBar().getMana() >= 20) {
            data.getManaBar().subtractMana(20);
            double speed;
            double gravity;
            float pitch;
            ItemMeta meta = event.getBow().getItemMeta();

            ListAction tickActions = new ListAction();
            tickActions.addAction(new ParticleAction(Particle.REDSTONE, 1, 0, 0, 0, 0, new Particle.DustOptions(Color.fromRGB(183, 242, 245), 1f)));
            ListAction damageActions = new ListAction();

            double damage = Utils.calculateBowDamage(event);
            double force = event.getForce();
            if (force >= 1) {
                speed = 60;
                gravity = 0.4;
                pitch = 1.2f;
            } else if (force >= 0.2) {
                speed = 30;
                gravity = 1;
                pitch = 1f;
            } else {
                speed = 10;
                gravity = 3.8;
                pitch = 0.8f;
            }

            damageActions.addAction(new DamageAction(damage));
            if (meta.hasEnchant(Enchantment.ARROW_FIRE)) {
                tickActions.addAction(new ParticleAction(Particle.FLAME, 1, 0, 0, 0, 0));
                damageActions.addAction(new SetOnFireAction(100));
            }
            if (meta.hasEnchant(Enchantment.ARROW_KNOCKBACK)) {
                damageActions.addAction(new PushAction(1 * meta.getEnchantLevel(Enchantment.ARROW_KNOCKBACK), 0.25, true));
            }
            damageActions.addAction(frostOrb);

            ProjectileAction projAction = new ProjectileAction(speed, gravity,
                    damageActions,
                    new NullAction(),
                    frostOrb,
                    tickActions,
                    new GenericEntity(EntityType.ARROW), 0.5, 50, 0, false, false);


            event.setCancelled(true);
            player.playSound(player.getLocation(), Sound.ENTITY_SKELETON_SHOOT, 2.5f, pitch);
            projAction.execute(new EntityTarget(player), new EntitySource(player));
        }
    }

    @Override
    public void doTimerAction(Player player) {

    }

    @Override
    public boolean hasTimerAction() {
        return false;
    }

    @Override
    public int getTimerPeriod() {
        return 0;
    }

    @Override
    public int getTimerDelay() {
        return 0;
    }
}
