package me.mytheria.hoppers.hopper;

import me.mytheria.hoppers.MytheriaHoppers;
import org.bukkit.Location;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HopperManager {

    private final Map<Location, HopperData> hoppers =
            new ConcurrentHashMap<>();

    public HopperManager(MytheriaHoppers plugin) {
    }

    public Map<Location, HopperData> getHoppers() {
        return hoppers;
    }

    public HopperData getData(Location location) {

        Location blockLocation =
                location.getBlock()
                        .getLocation();

        return hoppers.computeIfAbsent(
                blockLocation,
                key -> new HopperData()
        );
    }

    public void remove(Location location) {

        hoppers.remove(
                location.getBlock()
                        .getLocation()
        );
    }

    public boolean contains(Location location) {

        return hoppers.containsKey(
                location.getBlock()
                        .getLocation()
        );
    }
}
