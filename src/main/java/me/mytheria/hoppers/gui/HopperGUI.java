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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class HopperGUI {

    private final MytheriaHoppers plugin;
    public static final Map<UUID, Location> openHopperLocations = new HashMap<>();

    public HopperGUI(MytheriaHoppers plugin) {
        this.plugin = plugin;
    }

    public void openGUI(Player player, Location location) {
        openHopperLocations.put(player.getUniqueId(), location);

        Inventory gui = Bukkit.createInventory(null, 27, ChatColor.DARK_GRAY + "Hopper Upgrades");
        HopperData data = plugin.getHopperManager().getData(location);

        int nextSpeedLevel = data.getSpeedLevel() + 1;
        double speedCost = plugin.getConfig().getDouble("speed-upgrades." + nextSpeedLevel + ".cost", -1);
        String speedCostText = speedCost > 0 ? "$" + speedCost : "MAX";

        ItemStack speedItem = createGuiItem(
                Material.SUGAR,
                ChatColor.GREEN + "Speed Upgrade",
                ChatColor.GRAY + "Current Level: " + ChatColor.YELLOW + data.getSpeedLevel(),
                ChatColor.GRAY + "Next Level Cost: " + ChatColor.GOLD + speedCostText,
                ChatColor.YELLOW + "Click to upgrade!"
        );
        gui.setItem(11, speedItem);

        int nextRangeLevel = data.getRangeLevel() + 1;
        double rangeCost = plugin.getConfig().getDouble("range-upgrades." + nextRangeLevel + ".cost", -1);
        String rangeCostText = rangeCost > 0 ? "$" + rangeCost : "MAX";

        ItemStack rangeItem = createGuiItem(
                Material.COMPASS,
                ChatColor.AQUA + "Range Upgrade",
                ChatColor.GRAY + "Current Level: " + ChatColor.YELLOW + data.getRangeLevel(),
                ChatColor.GRAY + "Next Level Cost: " + ChatColor.GOLD + rangeCostText,
                ChatColor.YELLOW + "Click to upgrade!"
        );
        gui.setItem(13, rangeItem);

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
        openHopperLocations.put(player.getUniqueId(), location);

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

        double unlockCost = plugin.getConfig().getDouble("filter-unlock-cost", 1000.0);
        String statusText = data.isFilterEnabled() ? ChatColor.GREEN + "Filter: ENABLED" : ChatColor.RED + "Filter: DISABLED";
        String toggleLore = data.isFilterEnabled() ? ChatColor.YELLOW + "Click to disable filter" : ChatColor.YELLOW + "Click to unlock ($" + unlockCost + ")";

        ItemStack toggleItem = createGuiItem(
                data.isFilterEnabled() ? Material.LIME_DYE : Material.GRAY_DYE,
                statusText,
                toggleLore
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
