package me.mytheria.hoppers.hopper;

import me.mytheria.hoppers.MytheriaHoppers;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class HopperTask extends BukkitRunnable {

    private final MytheriaHoppers plugin;


    public HopperTask(MytheriaHoppers plugin) {
        this.plugin = plugin;
    }


    @Override
    public void run() {

        HopperManager manager = plugin.getHopperManager();


        for (Location location : manager.getHoppers().keySet()) {

            if (!location.getChunk().isLoaded()) {
                continue;
            }


            HopperData data = manager.getData(location);


            int range = plugin.getConfig()
                    .getInt(
                            "range-upgrades." + data.getRangeLevel() + ".range",
                            0
                    );


            if (range <= 0) {
                continue;
            }


            for (Entity entity : location.getWorld()
                    .getNearbyEntities(
                            location,
                            range,
                            range,
                            range
                    )) {


                if (!(entity instanceof Item item)) {
                    continue;
                }


                ItemStack stack = item.getItemStack();


                location.getWorld()
                        .dropItemNaturally(
                                location,
                                stack
                        );


                item.remove();

            }
        }
    }
}
