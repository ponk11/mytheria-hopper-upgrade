package me.mytheria.hoppers.managers;

import me.mytheria.hoppers.MytheriaHoppers;
import me.mytheria.hoppers.hopper.HopperData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
            dataConfig.set(path + ".speed", entry.getValue().getSpeedLevel());
            dataConfig.set(path + ".range", entry.getValue().getRangeLevel());
            dataConfig.set(path + ".filterEnabled", entry.getValue().isFilterEnabled());
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
            data.setSpeedLevel(dataConfig.getInt("hoppers." + key + ".speed", 1));
            data.setRangeLevel(dataConfig.getInt("hoppers." + key + ".range", 1));
            data.setFilterEnabled(dataConfig.getBoolean("hoppers." + key + ".filterEnabled", false));

            hopperDataMap.put(loc, data);
        }
    }
}
