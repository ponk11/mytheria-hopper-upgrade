package me.mytheria.hoppers.listeners;

import me.mytheria.hoppers.MytheriaHoppers;
import me.mytheria.hoppers.gui.HopperGUI;
import me.mytheria.hoppers.hopper.HopperData;
import org.bukkit.Location;
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
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;

        String title = event.getView().getTitle();
        if (title != null && (title.contains("Hopper") || title.contains("Upgrade") || title.contains("Filter"))) {
            // Cancel event to prevent players from taking/moving items in the GUI
            event.setCancelled(true);

            if (event.getWhoClicked() instanceof Player player) {
                if (event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta()) {
                    handleGuiClick(player, event, title);
                }
            }
        }
    }

    private void handleGuiClick(Player player, InventoryClickEvent event, String title) {
        Location hopperLocation = HopperGUI.openHopperLocations.get(player.getUniqueId());
        if (hopperLocation == null) return;

        HopperData data = plugin.getHopperManager().getData(hopperLocation);
        if (data == null) return;

        int slot = event.getRawSlot();

        // Handle Hopper Upgrades GUI
        if (title.contains("Hopper Upgrades")) {
            if (slot == 11) {
                // Speed Upgrade
                if (plugin.getUpgradeManager().upgradeSpeed(player, data)) {
                    player.sendMessage("§aSpeed upgrade successful!");
                } else {
                    player.sendMessage("§cFailed to upgrade speed. Check cost or max level.");
                }
                plugin.getHopperGUI().openGUI(player, hopperLocation);
            } else if (slot == 13) {
                // Range Upgrade
                if (plugin.getUpgradeManager().upgradeRange(player, data)) {
                    player.sendMessage("§aRange upgrade successful!");
                } else {
                    player.sendMessage("§cFailed to upgrade range. Check cost or max level.");
                }
                plugin.getHopperGUI().openGUI(player, hopperLocation);
            } else if (slot == 15) {
                // Filter Settings
                plugin.getHopperGUI().openFilterGUI(player, hopperLocation);
            }
        }
        // Handle Filter GUI
        else if (title.contains("Item Filter Settings")) {
            if (slot == 26) {
                // Toggle filter
                data.setFilterEnabled(!data.isFilterEnabled());
                plugin.getHopperGUI().openFilterGUI(player, hopperLocation);
            } else if (slot < 18) {
                // Remove item from filter
                plugin.getHopperGUI().openFilterGUI(player, hopperLocation);
            }
        }
    }
}
