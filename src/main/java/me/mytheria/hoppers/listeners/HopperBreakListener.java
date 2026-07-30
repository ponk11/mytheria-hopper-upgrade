package me.mytheria.hoppers.listeners;

import me.mytheria.hoppers.MytheriaHoppers;
import org.bukkit.Material;
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


        if (event.getBlock().getType()
                != Material.HOPPER) {

            return;
        }


        plugin.getHopperManager()
                .remove(
                        event.getBlock()
                                .getLocation()
                );


        plugin.getDataManager()
                .save();

    }

}
