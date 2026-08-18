package me.mytheria.hoppers.storage;

import me.mytheria.hoppers.MytheriaHoppers;
import me.mytheria.hoppers.hopper.HopperData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class DataManager {

    private final MytheriaHoppers plugin;
    private final File file;
    private YamlConfiguration data;

    public DataManager(MytheriaHoppers plugin) {
        this.plugin = plugin;

        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        this.file =
                new File(
                        plugin.getDataFolder(),
                        "hoppers.yml"
                );

        this.data =
                YamlConfiguration.loadConfiguration(file);
    }

    public void load() {

        data =
                YamlConfiguration.loadConfiguration(file);

        ConfigurationSection section =
                data.getConfigurationSection("hoppers");

        if (section == null) {
            return;
        }

        plugin.getHopperManager()
                .getHoppers()
                .clear();

        for (String key : section.getKeys(false)) {

            String worldName =
                    section.getString(
                            key + ".world"
                    );

            if (worldName == null) {
                continue;
            }

            World world =
                    Bukkit.getWorld(worldName);

            if (world == null) {
                continue;
            }

            int x =
                    section.getInt(
                            key + ".x"
                    );

            int y =
                    section.getInt(
                            key + ".y"
                    );

            int z =
                    section.getInt(
                            key + ".z"
                    );

            Location location =
                    new Location(
                            world,
                            x,
                            y,
                            z
                    );

            HopperData hopper =
                    new HopperData();

            hopper.setSpeedLevel(
                    section.getInt(
                            key + ".speed-level",
                            0
                    )
            );

            hopper.setRangeLevel(
                    section.getInt(
                            key + ".range-level",
                            0
                    )
            );

            plugin.getHopperManager()
                    .getHoppers()
                    .put(
                            location,
                            hopper
                    );
        }
    }

    public void save() {

        data =
                new YamlConfiguration();

        int index = 0;

        for (Map.Entry<Location, HopperData> entry :
                plugin.getHopperManager()
                        .getHoppers()
                        .entrySet()) {

            Location location =
                    entry.getKey();

            HopperData hopper =
                    entry.getValue();

            String path =
                    "hoppers." + index;

            data.set(
                    path + ".world",
                    location.getWorld()
                            .getName()
            );

            data.set(
                    path + ".x",
                    location.getBlockX()
            );

            data.set(
                    path + ".y",
                    location.getBlockY()
            );

            data.set(
                    path + ".z",
                    location.getBlockZ()
            );

            data.set(
                    path + ".speed-level",
                    hopper.getSpeedLevel()
            );

            data.set(
                    path + ".range-level",
                    hopper.getRangeLevel()
            );

            index++;
        }

        try {

            data.save(file);

        } catch (IOException exception) {

            plugin.getLogger().severe(
                    "Could not save hoppers.yml!"
            );

            exception.printStackTrace();
        }
    }
}
