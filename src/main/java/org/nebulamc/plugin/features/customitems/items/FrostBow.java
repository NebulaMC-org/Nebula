package org.nebulamc.plugin.features.customitems.items;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
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
            ),
            new SoundAction(Sound.BLOCK_GLASS_BREAK, 1f, 1)
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
}
