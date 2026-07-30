package me.mytheria.hoppers.storage;

import me.mytheria.hoppers.MytheriaHoppers;
import me.mytheria.hoppers.hopper.HopperData;
import me.mytheria.hoppers.util.LocationUtil;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class DataManager {


    private final MytheriaHoppers plugin;

    private File file;

    private YamlConfiguration config;


    public DataManager(MytheriaHoppers plugin) {

        this.plugin = plugin;

        setup();

    }



    private void setup() {


        file = new File(
                plugin.getDataFolder(),
                "hoppers.yml"
        );


        if (!file.exists()) {

            file.getParentFile().mkdirs();

            try {

                file.createNewFile();

            } catch (IOException e) {

                e.printStackTrace();

            }

        }


        config =
                YamlConfiguration.loadConfiguration(file);

    }



    public void save() {


        for (Map.Entry<Location, HopperData> entry :
                plugin.getHopperManager()
                        .getHoppers()
                        .entrySet()) {


            String path =
                    "hoppers."
                            + LocationUtil.serialize(
                            entry.getKey()
                    );


            config.set(
                    path + ".speed",
                    entry.getValue()
                            .getSpeedLevel()
            );


            config.set(
                    path + ".range",
                    entry.getValue()
                            .getRangeLevel()
            );

        }



        try {

            config.save(file);

        } catch (IOException e) {

            e.printStackTrace();

        }

    }



    public void load() {


        if (!config.contains("hoppers")) {
            return;
        }


        for (String key :
                config.getConfigurationSection(
                        "hoppers"
                ).getKeys(false)) {


            Location location =
                    LocationUtil.deserialize(key);



            HopperData data =
                    new HopperData();


            data.setSpeedLevel(
                    config.getInt(
                            "hoppers."
                            + key
                            + ".speed"
                    )
            );


            data.setRangeLevel(
                    config.getInt(
                            "hoppers."
                            + key
                            + ".range"
                    )
            );


            plugin.getHopperManager()
                    .getHoppers()
                    .put(
                            location,
                            data
                    );

        }

    }

}
