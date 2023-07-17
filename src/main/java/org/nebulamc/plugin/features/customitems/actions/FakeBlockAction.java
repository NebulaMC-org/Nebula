package org.nebulamc.plugin.features.customitems.actions;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.nebulamc.plugin.Nebula;
import org.nebulamc.plugin.features.customitems.source.Source;
import org.nebulamc.plugin.features.customitems.targeter.Target;

import java.util.ArrayList;
import java.util.List;

public class FakeBlockAction extends Action{

    BlockData blockData;
    int ticks;
    boolean blacklist;
    List<Material> materialList;
    boolean solidsOnly;

    public FakeBlockAction(Material blockMaterial, int ticks, boolean blacklist, List<Material> materialList){
        blockData = blockMaterial.createBlockData();
        this.ticks = ticks;
        this.blacklist = blacklist;
        this.materialList = materialList;
    }

    public FakeBlockAction(Material blockMaterial, int ticks, boolean blacklist, List<Material> materialList, boolean solidsOnly){
        blockData = blockMaterial.createBlockData();
        this.ticks = ticks;
        this.blacklist = blacklist;
        this.materialList = materialList;
        this.solidsOnly = solidsOnly;
    }

    @Override
    public void execute(Target target, Source source) {
        Material type = target.getLocation().getBlock().getType();
        if (solidsOnly && !type.isSolid()){
            return;
        }
        if (blacklist){
            if (materialList.contains(type)){
                return;
            }
        } else {
            if (!materialList.contains(type)){
                return;
            }
        }

        List<Player> players = new ArrayList<>();
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            if (p.getWorld() == source.getCaster().getWorld() && p.getLocation().distanceSquared(source.getLocation()) <= 5000) {
                players.add(p);
                p.sendBlockChange(target.getLocation(), blockData);
            }
        }
        Bukkit.getScheduler().runTaskLater(Nebula.getInstance(), () -> players.forEach(
                player -> player.sendBlockChange(target.getLocation(),
                        Bukkit.createBlockData(type))
        ), ticks);
    }
}
