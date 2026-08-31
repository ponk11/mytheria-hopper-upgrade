package me.mytheria.hoppers.managers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.mytheria.hoppers.MytheriaHoppers;
import me.mytheria.hoppers.hopper.HopperData;

public class HopperManager {

    private final MytheriaHoppers plugin;
    private final Map<Location, HopperData> hopperDataMap = new HashMap<>();
    private final File dataFile;
    private FileConfiguration dataConfig;

    public HopperManager(MytheriaHoppers plugin) {
        this.plugin = plugin;
        this.dataFile = new File(plugin.getDataFolder(), "data.yml");
        loadData();
    }

    public HopperData getData(Location location) {
        return hopperDataMap.computeIfAbsent(location, loc -> new HopperData());
    }

    public Map<Location, HopperData> getHoppers() {
        return hopperDataMap;
    }

    public Map<Location, HopperData> getHopperDataMap() {
        return hopperDataMap;
    }

    public void remove(Location location) {
        hopperDataMap.remove(location);
    }

    public void removeData(Location location) {
        remove(location);
    }

    public void saveData() {
        if (dataConfig == null) {
            dataConfig = new YamlConfiguration();
        }

        for (Map.Entry<Location, HopperData> entry : hopperDataMap.entrySet()) {
            Location loc = entry.getKey();
            if (loc == null || loc.getWorld() == null) continue;
            String path = "hoppers." + loc.getWorld().getName() + "," + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ();
            HopperData data = entry.getValue();
            dataConfig.set(path + ".speed", data.getSpeedLevel());
            dataConfig.set(path + ".range", data.getRangeLevel());
            dataConfig.set(path + ".filterEnabled", data.isFilterEnabled());
            dataConfig.set(path + ".filterUnlocked", data.isFilterUnlocked());
            dataConfig.set(path + ".unlockedFilterSlots", data.getUnlockedFilterSlots());
            if (data.getSelectedFilterItem() != null) {
                dataConfig.set(path + ".selectedFilterItem", data.getSelectedFilterItem().toString());
            }
        }

        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save hopper data to data.yml!");
        }
    }

    public void loadData() {
        if (!dataFile.exists()) {
            return;
        }
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
        if (!dataConfig.contains("hoppers")) {
            return;
        }

        for (String key : dataConfig.getConfigurationSection("hoppers").getKeys(false)) {
            String[] parts = key.split(",");
            if (parts.length != 4) continue;

            String worldName = parts[0];
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            int z = Integer.parseInt(parts[3]);

            if (Bukkit.getWorld(worldName) == null) continue;

            Location loc = new Location(Bukkit.getWorld(worldName), x, y, z);
            HopperData data = new HopperData();
            data.setSpeedLevel(dataConfig.getInt("hoppers." + key + ".speed", 0));
            data.setRangeLevel(dataConfig.getInt("hoppers." + key + ".range", 0));
            data.setFilterEnabled(dataConfig.getBoolean("hoppers." + key + ".filterEnabled", false));
            data.setFilterUnlocked(dataConfig.getBoolean("hoppers." + key + ".filterUnlocked", false));
            data.setUnlockedFilterSlots(dataConfig.getInt("hoppers." + key + ".unlockedFilterSlots", 0));
            
            String selectedItem = dataConfig.getString("hoppers." + key + ".selectedFilterItem");
            if (selectedItem != null && !selectedItem.isEmpty()) {
                try {
                    org.bukkit.Material material = org.bukkit.Material.valueOf(selectedItem);
                    data.setSelectedFilterItem(material);
                } catch (IllegalArgumentException e) {
                    // Invalid material, skip
                }
            }

            hopperDataMap.put(loc, data);
        }
    }
}
