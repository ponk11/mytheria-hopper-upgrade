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
        Action action = event.getAction();
        
        // Listen for Right-Click or Shift + Left-Click
        if (action != Action.RIGHT_CLICK_BLOCK && action != Action.LEFT_CLICK_BLOCK) {
            return;
        }

        Block block = event.getClickedBlock();
        if (block == null || block.getType() != Material.HOPPER) {
            return;
        }

        Player player = event.getPlayer();

        // SuperiorSkyblock Protection Check
        if (Bukkit.getPluginManager().isPluginEnabled("SuperiorSkyblock2")) {
            Island island = SuperiorSkyblockAPI.getIslandAt(block.getLocation());
            if (island != null) {
                // If the player is not an island member or admin, block access
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

        // Handle opening the Hopper Upgrade GUI on Right-Click (or Shift-Left Click)
        if (action == Action.RIGHT_CLICK_BLOCK || (action == Action.LEFT_CLICK_BLOCK && player.isSneaking())) {
            event.setCancelled(true);
            new HopperGUI(plugin).openGUI(player, block.getLocation());
        }
    }
}
