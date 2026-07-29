package me.mytheria.hoppers.hopper;

import me.mytheria.hoppers.MytheriaHoppers;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

public class HopperManager {


    private final MytheriaHoppers plugin;

    private final Map<Location, HopperData> hoppers;


    public HopperManager(MytheriaHoppers plugin) {

        this.plugin = plugin;

        this.hoppers = new HashMap<>();

    }


    public HopperData getData(Location location) {

        return hoppers.computeIfAbsent(
                location,
                key -> new HopperData()
        );

    }


    public boolean hasHopper(Location location) {

        return hoppers.containsKey(location);

    }


    public Map<Location, HopperData> getHoppers() {

        return hoppers;

    }


    public void remove(Location location) {

        hoppers.remove(location);

    }

}
