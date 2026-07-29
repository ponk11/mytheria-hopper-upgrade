package me.mytheria.hoppers.listeners;

import me.mytheria.hoppers.MytheriaHoppers;
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
    public void click(InventoryClickEvent event) {


        if (!event.getView()
                .getTitle()
                .contains("Mytheria")) {

            return;
        }


        event.setCancelled(true);


        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }



        HopperData data =
                plugin.getHopperManager()
                        .getData(
                                player.getLocation()
                        );



        if (event.getSlot() == 11) {


            int level =
                    data.getSpeedLevel() + 1;


            if (level >
                    plugin.getConfig()
                            .getInt(
                                    "settings.max-speed-level"
                            )) {


                player.sendMessage(
                        color("&cMaximum level reached.")
                );

                return;

            }


            data.setSpeedLevel(level);


            player.sendMessage(
                    color("&dMytheria Hoppers &8» &aSpeed upgraded!")
            );


        }



        if (event.getSlot() == 15) {


            int level =
                    data.getRangeLevel() + 1;


            if (level >
                    plugin.getConfig()
                            .getInt(
                                    "settings.max-range-level"
                            )) {


                player.sendMessage(
                        color("&cMaximum level reached.")
                );

                return;

            }


            data.setRangeLevel(level);


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
