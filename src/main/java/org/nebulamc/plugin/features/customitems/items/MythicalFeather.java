package org.nebulamc.plugin.features.customitems.items;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.nebulamc.plugin.features.customitems.actions.*;
import org.nebulamc.plugin.features.customitems.entity.GenericEntity;
import org.nebulamc.plugin.features.customitems.source.EntitySource;
import org.nebulamc.plugin.features.customitems.targeter.EntityTarget;
import org.nebulamc.plugin.features.playerdata.PlayerData;
import org.nebulamc.plugin.features.playerdata.PlayerManager;

import java.util.Arrays;
import java.util.List;

public class MythicalFeather extends CustomItem{
    @Override
    public String getName() {
        return "&dMythical Feather";
    }

    @Override
    public Material getMaterial() {
        return Material.STICK;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("&7Mana Use: &b65", "\n", "&eRight-click to shoot a poisonous dart!");
    }

    @Override
    public int getModelData() {
        return 1;
    }

    ProjectileAction projAction = new ProjectileAction(80, 0,
            new ListAction(
                    new DamageAction(5),
                    new PotionAction(PotionEffectType.WITHER, 400, 4),
                    new SoundAction(Sound.ITEM_TRIDENT_HIT, 1, 1f)
            ) ,
            new SoundAction(Sound.ENTITY_LLAMA_SPIT, 1, 1f),
            new NullAction(),
            new ParticleAction(Particle.REDSTONE, 1, 0, 0, 0, 0, new Particle.DustOptions(Color.YELLOW, 2f)),
            new GenericEntity(EntityType.ARROW), 1, 200, 0, false, false);

    @Override
    public void handleRightClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        PlayerData data = PlayerManager.getPlayerData(player);
        if (data.getManaBar().getMana() >= 65) {
            data.getManaBar().subtractMana(65);
            projAction.execute(new EntityTarget(player), new EntitySource(player));
        }
    }

    @Override
    public void handleOffHandClick(Player player, ItemStack itemStack, PlayerInteractEvent event) {
        handleRightClick(player, itemStack, event);
    }
}
