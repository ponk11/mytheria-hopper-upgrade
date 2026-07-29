package me.mytheria.hoppers.storage;

import me.mytheria.hoppers.MytheriaHoppers;
import me.mytheria.hoppers.hopper.HopperData;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

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

        try {

            config.save(file);

        } catch (IOException e) {

            e.printStackTrace();

        }

    }
}
