package me.mytheria.hoppers.listeners;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;
import me.mytheria.hoppers.MytheriaHoppers;
import me.mytheria.hoppers.gui.HopperGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class HopperInteractListener implements Listener {

    private final MytheriaHoppers plugin;

    public HopperInteractListener(MytheriaHoppers plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent event) {
        // ONLY trigger on Shift + Left-Click
        if (event.getAction() != Action.LEFT_CLICK_BLOCK) {
            return;
        }

        Player player = event.getPlayer();
        if (!player.isSneaking()) {
            return;
        }

        Block block = event.getClickedBlock();
        if (block == null || block.getType() != Material.HOPPER) {
            return;
        }

        // SuperiorSkyblock Protection Check
        if (Bukkit.getPluginManager().isPluginEnabled("SuperiorSkyblock2")) {
            Island island = SuperiorSkyblockAPI.getIslandAt(block.getLocation());
            if (island != null) {
                if (!island.isMember(SuperiorSkyblockAPI.getPlayer(player)) && !player.hasPermission("mytheriahoppers.admin")) {
                    player.sendMessage(ChatColor.RED + "You cannot interact with hoppers on someone else's island!");
                    event.setCancelled(true);
                    return;
                }
            }
        }

        if (!player.hasPermission("mytheriahoppers.use")) {
            return;
        }

        event.setCancelled(true);
        new HopperGUI(plugin).openGUI(player, block.getLocation());
    }
}
