package me.mytheria.hoppers.listeners;

import me.mytheria.hoppers.MytheriaHoppers;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class HopperBlockListener implements Listener {

    private final MytheriaHoppers plugin;

    public HopperBlockListener(MytheriaHoppers plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType() == Material.HOPPER) {
            plugin.getHopperManager().getData(event.getBlock().getLocation());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() == Material.HOPPER) {
            plugin.getHopperManager().removeData(event.getBlock().getLocation());
        }
    }
}
