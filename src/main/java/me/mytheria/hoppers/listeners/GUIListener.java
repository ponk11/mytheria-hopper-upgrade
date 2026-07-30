package me.mytheria.hoppers.listeners;

import me.mytheria.hoppers.MytheriaHoppers;
import me.mytheria.hoppers.gui.HopperHolder;
import me.mytheria.hoppers.hopper.HopperData;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;

public class GUIListener implements Listener {


    private final MytheriaHoppers plugin;


    public GUIListener(MytheriaHoppers plugin) {
        this.plugin = plugin;
    }



    @EventHandler
    public void onClick(InventoryClickEvent event) {


        if (!(event.getInventory()
                .getHolder()
                instanceof HopperHolder holder)) {

            return;
        }


        event.setCancelled(true);



        if (!(event.getWhoClicked()
                instanceof Player player)) {

            return;
        }



        HopperData data =
                plugin.getHopperManager()
                        .getData(
                                holder.getLocation()
                        );



        if (event.getSlot() == 11) {

            data.setSpeedLevel(
                    data.getSpeedLevel() + 1
            );


            player.sendMessage(
                    color("&dMytheria Hoppers &8» &aSpeed upgraded!")
            );

        }



        if (event.getSlot() == 15) {

            data.setRangeLevel(
                    data.getRangeLevel() + 1
            );


            player.sendMessage(
                    color("&dMytheria Hoppers &8» &aRange upgraded!")
            );

        }

    }



    private String color(String text) {

        return ChatColor.translateAlternateColorCodes(
                '&',
                text
        );

    }

}
