package me.mytheria.hoppers.listeners;

import me.mytheria.hoppers.MytheriaHoppers;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIListener implements Listener {


    private final MytheriaHoppers plugin;


    public GUIListener(MytheriaHoppers plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onClick(InventoryClickEvent event) {


        if (event.getView()
                .getTitle()
                .contains("Mytheria Hoppers")) {


            event.setCancelled(true);

        }

    }
}
