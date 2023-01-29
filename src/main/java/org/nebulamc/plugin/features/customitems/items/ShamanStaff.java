package org.nebulamc.plugin.features.customitems.items;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
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
import org.bukkit.util.Vector;
import org.nebulamc.plugin.features.customitems.actions.*;
import org.nebulamc.plugin.features.customitems.area.CylindricArea;
import org.nebulamc.plugin.features.customitems.area.SphericArea;
import org.nebulamc.plugin.features.customitems.source.EntitySource;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;
import org.nebulamc.plugin.features.playerdata.ManaBar;
import org.nebulamc.plugin.features.playerdata.PlayerManager;
import org.nebulamc.plugin.utils.Utils;

import java.util.*;

public class ShamanStaff extends CustomItem{
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

    @Override
    public void handleShootBow(Player player, ItemStack itemStack, EntityShootBowEvent event) {

    }

    @Override
    public void handleEquip(Player player, ItemStack itemStack) {

    }

    @Override
    public void handleUnequip(Player player, ItemStack itemStack) {

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
}
