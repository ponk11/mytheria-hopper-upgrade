package me.mytheria.hoppers.listeners;

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

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;

import me.mytheria.hoppers.MytheriaHoppers;

public class HopperInteractListener implements Listener {

    private final MytheriaHoppers plugin;

    public HopperInteractListener(MytheriaHoppers plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
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

        // Allow normal block breaking without blocking the vanilla break flow.
        if (event.getAction() == Action.LEFT_CLICK_BLOCK && !player.isSneaking()) {
            return;
        }

        event.setCancelled(true);

        // Shift + Left-Click = Open Upgrade GUI
        if (event.getAction() == Action.LEFT_CLICK_BLOCK && player.isSneaking()) {
            plugin.getHopperGUI().openGUI(player, block.getLocation());
        }
        // Right-Click = Open Hopper Inventory
        else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            player.openInventory(((org.bukkit.block.Hopper) block.getState()).getInventory());
        }
    }
}
