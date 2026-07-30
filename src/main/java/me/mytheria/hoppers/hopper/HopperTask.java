package me.mytheria.hoppers.hopper;

import me.mytheria.hoppers.MytheriaHoppers;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Hopper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

public class HopperTask extends BukkitRunnable {


    private final MytheriaHoppers plugin;


    public HopperTask(MytheriaHoppers plugin) {
        this.plugin = plugin;
    }



    @Override
    public void run() {


        HopperManager manager =
                plugin.getHopperManager();



        for (Map.Entry<Location, HopperData> entry :
                manager.getHoppers().entrySet()) {


            Location location =
                    entry.getKey();


            if (!location.getChunk().isLoaded()) {
                continue;
            }


            if (location.getBlock()
                    .getType()
                    != Material.HOPPER) {

                continue;
            }


            Hopper hopper =
                    (Hopper) location.getBlock()
                            .getState();



            HopperData data =
                    entry.getValue();



            int range =
                    plugin.getConfig()
                            .getInt(
                            "range-upgrades."
                            + data.getRangeLevel()
                            + ".range",
                            0
                    );



            if (range <= 0) {
                continue;
            }



            Inventory inventory =
                    hopper.getInventory();



            for (Entity entity :
                    location.getWorld()
                            .getNearbyEntities(
                                    location,
                                    range,
                                    range,
                                    range
                            )) {



                if (!(entity instanceof Item item)) {
                    continue;
                }



                ItemStack stack =
                        item.getItemStack();



                HashMapResult result =
                        addItem(
                                inventory,
                                stack
                        );



                if (result.leftOver == null) {

                    item.remove();

                } else {

                    item.setItemStack(
                            result.leftOver
                    );

                }

            }

        }

    }



    private HashMapResult addItem(
            Inventory inventory,
            ItemStack stack
    ) {


        Map<Integer, ItemStack> leftover =
                inventory.addItem(stack);



        if (leftover.isEmpty()) {

            return new HashMapResult(null);

        }



        return new HashMapResult(
                leftover.values()
                        .iterator()
                        .next()
        );

    }



    private static class HashMapResult {

        private final ItemStack leftOver;


        private HashMapResult(ItemStack leftOver) {

            this.leftOver = leftOver;

        }

    }

}
