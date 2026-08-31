package me.mytheria.hoppers.gui;

import me.mytheria.hoppers.MytheriaHoppers;
import me.mytheria.hoppers.hopper.HopperData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class HopperGUI {

    private final MytheriaHoppers plugin;

    public HopperGUI(MytheriaHoppers plugin) {
        this.plugin = plugin;
    }

    public void openGUI(Player player, Location location) {
        Inventory gui = Bukkit.createInventory(null, 27, ChatColor.DARK_GRAY + "Hopper Upgrades");
        HopperData data = plugin.getHopperManager().getData(location);

        // Speed Upgrade Item (Slot 11)
        ItemStack speedItem = createGuiItem(
                Material.SUGAR,
                ChatColor.GREEN + "Speed Upgrade",
                ChatColor.GRAY + "Current Level: " + ChatColor.YELLOW + data.getSpeedLevel()
        );
        gui.setItem(11, speedItem);

        // Range Upgrade Item (Slot 13)
        ItemStack rangeItem = createGuiItem(
                Material.COMPASS,
                ChatColor.AQUA + "Range Upgrade",
                ChatColor.GRAY + "Current Level: " + ChatColor.YELLOW + data.getRangeLevel()
        );
        gui.setItem(13, rangeItem);

        // Filter Settings Item (Slot 15)
        String filterStatus = data.isFilterEnabled() ? ChatColor.GREEN + "Enabled" : ChatColor.RED + "Disabled";
        ItemStack filterItem = createGuiItem(
                Material.HOPPER,
                ChatColor.GOLD + "Item Filter Settings",
                ChatColor.GRAY + "Filter Status: " + filterStatus,
                ChatColor.YELLOW + "Click to manage item filter"
        );
        gui.setItem(15, filterItem);

        player.openInventory(gui);
    }

    public void openFilterGUI(Player player, Location location) {
        Inventory gui = Bukkit.createInventory(null, 27, ChatColor.DARK_GRAY + "Item Filter Settings");
        HopperData data = plugin.getHopperManager().getData(location);

        int slot = 0;
        for (Material material : data.getFilteredMaterials()) {
            if (slot >= 18) break;
            ItemStack item = createGuiItem(
                    material,
                    ChatColor.YELLOW + material.name(),
                    ChatColor.RED + "Click to remove from filter"
            );
            gui.setItem(slot++, item);
        }

        // Toggle Status Button (Slot 26)
        String statusText = data.isFilterEnabled() ? ChatColor.GREEN + "Filter: ENABLED" : ChatColor.RED + "Filter: DISABLED";
        ItemStack toggleItem = createGuiItem(
                data.isFilterEnabled() ? Material.LIME_DYE : Material.GRAY_DYE,
                statusText,
                ChatColor.YELLOW + "Click to toggle filter on/off"
        );
        gui.setItem(26, toggleItem);

        player.openInventory(gui);
    }

    private ItemStack createGuiItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            List<String> loreList = new ArrayList<>();
            for (String line : lore) {
                loreList.add(line);
            }
            meta.setLore(loreList);
            item.setItemMeta(meta);
        }
        return item;
    }
}
