package me.mytheria.hoppers.listeners;

import me.mytheria.hoppers.MytheriaHoppers;
import me.mytheria.hoppers.hopper.HopperData;
import me.mytheria.hoppers.hopper.HopperItem;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class HopperBreakListener implements Listener {

    private final MytheriaHoppers plugin;

    public HopperBreakListener(MytheriaHoppers plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {

        Block block = event.getBlock();

        if (block.getType() != Material.HOPPER) {
            return;
        }

        HopperData data =
                plugin.getHopperManager()
                        .getData(block.getLocation());

        if (data == null) {
            return;
        }

        int speedLevel =
                data.getSpeedLevel();

        int rangeLevel =
                data.getRangeLevel();

        plugin.getHopperManager()
                .remove(block.getLocation());

        plugin.getDataManager().save();

        if (speedLevel <= 0 && rangeLevel <= 0) {
            return;
        }

        event.setDropItems(false);

        block.getWorld().dropItemNaturally(
                block.getLocation(),
                HopperItem.create(
                        speedLevel,
                        rangeLevel
                )
        );
    }
}
