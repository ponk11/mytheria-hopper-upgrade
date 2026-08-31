package me.mytheria.hoppers.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import me.mytheria.hoppers.MytheriaHoppers;

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
}
