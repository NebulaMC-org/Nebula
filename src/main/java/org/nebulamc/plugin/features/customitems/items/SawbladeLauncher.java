package org.nebulamc.plugin.features.customitems.items;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
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
import org.nebulamc.plugin.features.customitems.entity.NoEntity;
import org.nebulamc.plugin.features.customitems.source.EntitySource;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SawbladeLauncher extends CustomItem{
    @Override
    public String getName() {
        return "&eSawblade Launcher";
    }

    @Override
    public Material getMaterial() {
        return Material.IRON_HORSE_ARMOR;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&7Ammo: &fIron Ingot", "\n", "&eRight-click to launch a devastating sawblade!");
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

    EntitiesInAreaAction pushAction = new EntitiesInAreaAction(
            new SphericArea(new Vector(0, 1, 0), 4, false),
            new PushAction(1, 0.8, true)
    );
    ListAction tickActions = new ListAction(
            new BlocksInAreaAction(
                    new CylindricArea(new Vector(0, 0.5, 0), 1, 0, false),
                    new ParticleAction(Particle.CLOUD, 1, 0 ,0, 0, 0.01)
            ),
            new BlocksInAreaAction(
                    new CylindricArea(new Vector(0, 1.5, 0), 1, 0.75, true),
                    new ParticleAction(Particle.CLOUD, 1, 0 ,0, 0, 0.01)
            ),
            new BlocksInAreaAction(
                    new CylindricArea(new Vector(0, 2.5, 0), 1, 1.5, true),
                    new ParticleAction(Particle.CLOUD, 1, 0 ,0, 0, 0.01)
            ),
            new BlocksInAreaAction(
                    new CylindricArea(new Vector(0, 3.5, 0), 1, 2.5, true),
                    new ParticleAction(Particle.CLOUD, 1, 0 ,0, 0, 0.01)
            )
    );



    ProjectileAction projAction = new ProjectileAction(40, 0.4,
            new NullAction(),
            new NullAction(),
            new NullAction(),
            new ListAction(
                    pushAction, tickActions
            ),
            new NoEntity(),
            0.5, 50, 0, true, true
    );

    @Override
    public void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        projAction.execute(new EntityTarget(player), new EntitySource(player));
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
