package org.nebulamc.plugin.features.customitems.items;

import me.angeschossen.lands.api.flags.Flags;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
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
import org.nebulamc.plugin.utils.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BridgeWand extends CustomItem {
    @Override
    public String getName() {
        return "&fBridge Wand";
    }

    @Override
    public Material getMaterial() {
        return Material.STICK;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("\n",
                "&eRight-click the top of a block to",
                "&ebridge out with the item in your offhand.");
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
        return 1;
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
    public void handleLeftClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {

    }

    @Override
    public void handlePlaceBlock(Player player, ItemStack itemStack, BlockPlaceEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void handleShootBow(Player player, ItemStack itemStack, EntityShootBowEvent event) {

    }

    @Override
    public void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        ItemStack offHand = player.getInventory().getItemInOffHand();
        Block clickedBlock = event.getClickedBlock();

        if (offHand.getType().isBlock() && clickedBlock != null && clickedBlock.getRelative(player.getFacing(), 1).getType().equals(Material.AIR)){
            Block newBlock = clickedBlock.getRelative(player.getFacing(), 1);
            if (Utils.hasFlag(player, newBlock.getLocation(), offHand, Flags.BLOCK_PLACE)){
                newBlock.setType(offHand.getType(), true);
                offHand.subtract();
            }
        }
    }

    @Override
    public List<EquipmentSlot> activeSlots() {
        return null;
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
}
