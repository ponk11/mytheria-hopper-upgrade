package me.mytheria.hoppers.listeners;

import me.mytheria.hoppers.MytheriaHoppers;
import me.mytheria.hoppers.gui.HopperGUI;
import me.mytheria.hoppers.hopper.HopperData;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GUIListener implements Listener {

    private final MytheriaHoppers plugin;

    public GUIListener(MytheriaHoppers plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        String title = event.getView().getTitle();

        // Check if interacting with the main Hopper GUI
        if (title.equals(ChatColor.DARK_GRAY + "Hopper Upgrades")) {
            event.setCancelled(true);

            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null || clickedItem.getType() == Material.AIR) {
                return;
            }

            int slot = event.getSlot();

            switch (slot) {
                case 11 -> { // Speed Upgrade
                    if (!player.hasPermission("mytheriahoppers.upgrade.speed")) {
                        player.sendMessage(ChatColor.RED + "You do not have permission to upgrade hopper speed!");
                        return;
                    }
                    // Speed upgrade purchase logic
                }
                case 13 -> { // Range Upgrade
                    if (!player.hasPermission("mytheriahoppers.upgrade.range")) {
                        player.sendMessage(ChatColor.RED + "You do not have permission to upgrade hopper range!");
                        return;
                    }
                    // Range upgrade purchase logic
                }
                case 15 -> { // Filter Settings
                    if (!player.hasPermission("mytheriahoppers.filter")) {
                        player.sendMessage(ChatColor.RED + "You do not have permission to use the hopper filter!");
                        return;
                    }
                    // Open the dedicated Filter Management Menu
                    new HopperGUI(plugin).openFilterGUI(player, player.getLocation());
                }
            }
            return;
        }

        // Check if interacting with the Item Selection Filter GUI
        if (title.equals(ChatColor.DARK_GRAY + "Item Filter Settings")) {
            event.setCancelled(true);

            Location loc = player.getLocation(); // Dynamic location tracking
            HopperData data = plugin.getHopperManager().getData(loc);

            int slot = event.getSlot();

            // Toggle overall filter status button (Slot 26)
            if (slot == 26) {
                data.setFilterEnabled(!data.isFilterEnabled());
                player.sendMessage(ChatColor.GREEN + "Filter status set to: " + 
                        (data.isFilterEnabled() ? "Enabled" : "Disabled"));
                new HopperGUI(plugin).openFilterGUI(player, loc);
                return;
            }

            // Target top inventory (Filter Menu slots 0-17)
            if (event.getRawSlot() < 18) {
                ItemStack current = event.getCurrentItem();

                // UNSELECT ITEM: If a filter item exists in the slot, remove it
                if (current != null && current.getType() != Material.AIR && current.getType() != Material.GRAY_STAINED_GLASS_PANE) {
                    data.removeFilterMaterial(current.getType());
                    player.sendMessage(ChatColor.RED + "Removed " + current.getType().name() + " from filter.");
                    new HopperGUI(plugin).openFilterGUI(player, loc);
                    return;
                }

                // SELECT ITEM: If cursor holds an item, select it for filtering
                ItemStack cursor = event.getCursor();
                if (cursor != null && cursor.getType() != Material.AIR) {
                    data.addFilterMaterial(cursor.getType());
                    player.sendMessage(ChatColor.GREEN + "Added " + cursor.getType().name() + " to filter.");
                    new HopperGUI(plugin).openFilterGUI(player, loc);
                }
            }
        }
    }
}
