package me.mytheria.hoppers.listeners;

import me.mytheria.hoppers.MytheriaHoppers;
import me.mytheria.hoppers.gui.HopperHolder;
import me.mytheria.hoppers.hopper.HopperData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
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


        if (!(event.getInventory().getHolder()
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


            boolean upgraded =
                    plugin.getUpgradeManager()
                            .upgradeSpeed(
                                    player,
                                    data
                            );


            if (upgraded) {

                plugin.getDataManager()
                        .save();


                player.sendMessage(
                        color(
                        "&dMytheria &5Hoppers &8» &aSpeed upgrade purchased!"
                        )
                );


            } else {


                player.sendMessage(
                        color(
                        "&dMytheria &5Hoppers &8» &cYou cannot afford this upgrade!"
                        )
                );

            }


            player.closeInventory();

        }




        if (event.getSlot() == 15) {


            boolean upgraded =
                    plugin.getUpgradeManager()
                            .upgradeRange(
                                    player,
                                    data
                            );


            if (upgraded) {


                plugin.getDataManager()
                        .save();


                player.sendMessage(
                        color(
                        "&dMytheria &5Hoppers &8» &aRange upgrade purchased!"
                        )
                );


            } else {


                player.sendMessage(
                        color(
                        "&dMytheria &5Hoppers &8» &cYou cannot afford this upgrade!"
                        )
                );

            }


            player.closeInventory();

        }

    }



    private String color(String message) {

        return ChatColor.translateAlternateColorCodes(
                '&',
                message
        );

    }

}
