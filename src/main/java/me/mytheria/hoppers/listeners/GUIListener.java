package me.mytheria.hoppers.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import me.mytheria.hoppers.MytheriaHoppers;
import me.mytheria.hoppers.gui.HopperGUI;
import me.mytheria.hoppers.hopper.HopperData;

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
            
            if (event.getWhoClicked() instanceof Player player) {
                // Check if they clicked on the GUI inventory itself
                if (event.getClickedInventory().equals(event.getInventory())) {
                    // Cancel event to prevent players from taking/moving items in the GUI
                    event.setCancelled(true);
                    
                    if (event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta()) {
                        handleGuiClick(player, event, title);
                    }
                } else {
                    // They clicked on their own inventory - check if they're dragging to filter slots
                    if (title.contains("Item Filter Selection") && event.getCursor() != null && event.getCursor().getType() != Material.AIR) {
                        Location hopperLocation = HopperGUI.openHopperLocations.get(player.getUniqueId());
                        if (hopperLocation != null) {
                            HopperData data = plugin.getHopperManager().getData(hopperLocation);
                            if (data != null && data.isFilterUnlocked()) {
                                int slot = event.getRawSlot();
                                // Only allow placing items on filter slots (0-17, excluding 13 which is display)
                                if (slot >= 0 && slot < 18 && slot != 13) {
                                    event.setCancelled(true);
                                    data.setSelectedFilterItem(event.getCursor().getType());
                                    player.sendMessage("§aSelected item: " + event.getCursor().getType().name());
                                    plugin.getHopperGUI().openFilterGUI(player, hopperLocation);
                                }
                            }
                        }
                    }
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
        // Handle Filter Selection GUI
        else if (title.contains("Item Filter Selection")) {
            // Slot 13 is the center display slot
            if (slot == 13) {
                if (!data.isFilterUnlocked()) {
                    // Unlock filter
                    if (plugin.getUpgradeManager().unlockFilter(player, data)) {
                        player.sendMessage("§aFilter unlocked! You can now select 1 item to filter.");
                    } else {
                        player.sendMessage("§cFailed to unlock filter. You need $5,000,000.");
                    }
                } else if (data.getSelectedFilterItem() != null) {
                    // Deselect item
                    data.setSelectedFilterItem(null);
                    player.sendMessage("§aItem deselected!");
                }
                plugin.getHopperGUI().openFilterGUI(player, hopperLocation);
            }
            // Slots 18-25 are for unlocking additional slots
            else if (data.isFilterUnlocked() && slot >= 18 && slot <= 25 && data.getUnlockedFilterSlots() < 27) {
                if (plugin.getUpgradeManager().unlockFilterSlot(player, data)) {
                    player.sendMessage("§aFilter slot unlocked! You now have " + data.getUnlockedFilterSlots() + " slots.");
                } else {
                    player.sendMessage("§cFailed to unlock slot. You need $250,000 or slots are maxed out.");
                }
                plugin.getHopperGUI().openFilterGUI(player, hopperLocation);
            }
        }
    }
}
