package me.mytheria.hoppers.listeners;

import me.mytheria.hoppers.MytheriaHoppers;
import me.mytheria.hoppers.gui.HopperGUI;
import org.bukkit.Material;
import org.bukkit.block.Hopper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class HopperInteractListener implements Listener {


    private final MytheriaHoppers plugin;


    public HopperInteractListener(MytheriaHoppers plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onClick(PlayerInteractEvent event) {


        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }


        if (!event.getPlayer().isSneaking()) {
            return;
        }


        if (event.getClickedBlock() == null) {
            return;
        }


        if (event.getClickedBlock().getType() != Material.HOPPER) {
            return;
        }


        Player player = event.getPlayer();


        if (!player.hasPermission("mytheriahoppers.use")) {
            return;
        }


        Hopper hopper = (Hopper) event
                .getClickedBlock()
                .getState();


        new HopperGUI(
                plugin,
                hopper.getLocation()
        ).open(player);


        event.setCancelled(true);
    }
}
