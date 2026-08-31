package me.mytheria.hoppers.listeners;

import me.mytheria.hoppers.MytheriaHoppers;
import me.mytheria.hoppers.gui.HopperGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class HopperInteractListener implements Listener {

    private final MytheriaHoppers plugin;

    public HopperInteractListener(MytheriaHoppers plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        if (event.getAction() != Action.LEFT_CLICK_BLOCK) {
            return;
        }

        if (!event.getPlayer().isSneaking()) {
            return;
        }

        if (event.getClickedBlock() == null) {
            return;
        }

        if (event.getClickedBlock().getType()
                != Material.HOPPER) {
            return;
        }

        Player player =
                event.getPlayer();

        event.setCancelled(true);

        if (!player.hasPermission(
                "mytheriahoppers.use"
        )) {

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

        new HopperGUI(
                plugin,
                event.getClickedBlock()
                        .getLocation()
        ).open(player);
    }

    @SuppressWarnings("deprecation")
    private String color(String text) {

        return ChatColor.translateAlternateColorCodes(
                '&',
                text
        );
    }
}
