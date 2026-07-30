package me.mytheria.hoppers.listeners;

import me.mytheria.hoppers.MytheriaHoppers;
import me.mytheria.hoppers.hopper.HopperData;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class HopperPlaceListener implements Listener {


    private final MytheriaHoppers plugin;


    public HopperPlaceListener(MytheriaHoppers plugin) {
        this.plugin = plugin;
    }



    @EventHandler
    public void onPlace(BlockPlaceEvent event) {


        if (event.getBlock().getType()
                != Material.HOPPER) {

            return;
        }


        plugin.getHopperManager()
                .getHoppers()
                .put(
                        event.getBlock()
                                .getLocation(),
                        new HopperData()
                );


        plugin.getDataManager()
                .save();

    }

}
