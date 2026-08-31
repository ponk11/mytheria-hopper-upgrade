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
    public void onClick(
            InventoryClickEvent event
    ) {

        if (!(event.getInventory().getHolder()
                instanceof HopperHolder holder)) {
            return;
        }

        event.setCancelled(true);

        if (!(event.getWhoClicked()
                instanceof Player player)) {
            return;
        }

        if (!player.hasPermission(
                "mytheriahoppers.use"
        )) {
            player.closeInventory();

            player.sendMessage(
                    color(
                            plugin.getConfig()
                                    .getString(
                                            "messages.no-permission",
                                            "&cYou do not have permission."
                                    )
                    )
            );

            return;
        }

        HopperData data =
                plugin.getHopperManager()
                        .getData(
                                holder.getLocation()
                        );

        if (data == null) {
            player.closeInventory();
            return;
        }

        switch (event.getSlot()) {

            case 11 -> {

                int max =
                        plugin.getConfig()
                                .getInt(
                                        "settings.max-speed-level",
                                        4
                                );

                if (data.getSpeedLevel() >= max) {

                    player.sendMessage(
                            color(
                                    plugin.getConfig()
                                            .getString(
                                                    "messages.max-level",
                                                    "&cThis upgrade is already maxed!"
                                            )
                            )
                    );

                    return;
                }

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
                                    plugin.getConfig()
                                            .getString(
                                                    "messages.upgraded",
                                                    "&aUpgrade purchased!"
                                            )
                            )
                    );

                    player.closeInventory();

                } else {

                    player.sendMessage(
                            color(
                                    plugin.getConfig()
                                            .getString(
                                                    "messages.not-enough-money",
                                                    "&cYou do not have enough money."
                                            )
                            )
                    );
                }
            }

            case 15 -> {

                int max =
                        plugin.getConfig()
                                .getInt(
                                        "settings.max-range-level",
                                        4
                                );

                if (data.getRangeLevel() >= max) {

                    player.sendMessage(
                            color(
                                    plugin.getConfig()
                                            .getString(
                                                    "messages.max-level",
                                                    "&cThis upgrade is already maxed!"
                                            )
                            )
                    );

                    return;
                }

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
                                    plugin.getConfig()
                                            .getString(
                                                    "messages.upgraded",
                                                    "&aUpgrade purchased!"
                                            )
                            )
                    );

                    player.closeInventory();

                } else {

                    player.sendMessage(
                            color(
                                    plugin.getConfig()
                                            .getString(
                                                    "messages.not-enough-money",
                                                    "&cYou do not have enough money."
                                            )
                            )
                    );
                }
            }

            default -> {
            }
        }
    }

    @SuppressWarnings("deprecation")
    private String color(String text) {

        return ChatColor.translateAlternateColorCodes(
                '&',
                text
        );
    }
}
