package me.mytheria.hoppers.hopper;

import me.mytheria.hoppers.MytheriaHoppers;
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
    private final Map<Location, HopperData> hoppers = new HashMap<>();
    private final File file;
    private FileConfiguration config;

    public HopperManager(MytheriaHoppers plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "hoppers.yml");
        loadHoppers();
    }

    public Map<Location, HopperData> getHoppers() {
        return hoppers;
    }

    public HopperData getHopper(Location location) {
        return hoppers.computeIfAbsent(location, loc -> new HopperData());
    }

    public HopperData getData(Location location) {
        return getHopper(location);
    }

    public void removeHopper(Location location) {
        hoppers.remove(location);
    }

    public void remove(Location location) {
        removeHopper(location);
    }

    public void loadHoppers() {
        hoppers.clear();
        if (!file.exists()) {
            return;
        }

        config = YamlConfiguration.loadConfiguration(file);
        if (!config.isConfigurationSection("hoppers")) {
            return;
        }

        for (String key : config.getConfigurationSection("hoppers").getKeys(false)) {
            String worldName = config.getString("hoppers." + key + ".world");
            if (worldName == null || Bukkit.getWorld(worldName) == null) {
                continue;
            }

            double x = config.getDouble("hoppers." + key + ".x");
            double y = config.getDouble("hoppers." + key + ".y");
            double z = config.getDouble("hoppers." + key + ".z");
            Location location = new Location(Bukkit.getWorld(worldName), x, y, z);

            int speedLevel = config.getInt("hoppers." + key + ".speed", 0);
            int rangeLevel = config.getInt("hoppers." + key + ".range", 0);

            HopperData data = new HopperData();
            data.setSpeedLevel(speedLevel);
            data.setRangeLevel(rangeLevel);

            hoppers.put(location, data);
        }
    }

    public void saveHoppers() {
        if (!file.exists()) {
            try {
                plugin.getDataFolder().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("Could not create hoppers.yml!");
                return;
            }
        }

        config = new YamlConfiguration();
        int index = 0;

        for (Map.Entry<Location, HopperData> entry : hoppers.entrySet()) {
            Location loc = entry.getKey();
            HopperData data = entry.getValue();

            if (loc.getWorld() == null) continue;

            String path = "hoppers." + index++;
            config.set(path + ".world", loc.getWorld().getName());
            config.set(path + ".x", loc.getBlockX());
            config.set(path + ".y", loc.getBlockY());
            config.set(path + ".z", loc.getBlockZ());
            config.set(path + ".speed", data.getSpeedLevel());
            config.set(path + ".range", data.getRangeLevel());
        }

        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save hoppers.yml!");
        }
    }
}
