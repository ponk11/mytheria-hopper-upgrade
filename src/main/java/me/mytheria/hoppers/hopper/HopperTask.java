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

        int currentTick =
                plugin.getServer().getCurrentTick();

        for (Map.Entry<Location, HopperData> entry :
                plugin.getHopperManager()
                        .getHoppers()
                        .entrySet()) {

            Location location = entry.getKey();
            HopperData data = entry.getValue();

            if (!location.getChunk().isLoaded()) {
                continue;
            }

            if (location.getBlock().getType()
                    != Material.HOPPER) {
                continue;
            }

            Hopper hopper =
                    (Hopper) location.getBlock().getState();

            int speedLevel =
                    data.getSpeedLevel();

            int rangeLevel =
                    data.getRangeLevel();

            /*
             * Speed upgrades use Paper's native hopper
             * transfer cooldown.
             */
            if (speedLevel > 0) {

                int transferTicks =
                        plugin.getConfig()
                                .getInt(
                                        "speed-upgrades."
                                                + speedLevel
                                                + ".transfer-ticks",
                                        8
                                );

                if (hopper.getTransferCooldown()
                        > transferTicks) {

                    hopper.setTransferCooldown(
                            transferTicks
                    );

                    hopper.update(
                            false,
                            false
                    );
                }
            }

            /*
             * Range 0 means vanilla hopper collection.
             * Only upgraded range hoppers need the
             * additional entity scan.
             */
            if (rangeLevel <= 0) {
                continue;
            }

            int range =
                    plugin.getConfig()
                            .getInt(
                                    "range-upgrades."
                                            + rangeLevel
                                            + ".range",
                                    1
                            );

            int transferTicks =
                    plugin.getConfig()
                            .getInt(
                                    "speed-upgrades."
                                            + speedLevel
                                            + ".transfer-ticks",
                                    8
                            );

            if (currentTick
                    - data.getLastTransferTick()
                    < transferTicks) {
                continue;
            }

            data.setLastTransferTick(
                    currentTick
            );

            collectItems(
                    location,
                    hopper.getInventory(),
                    range
            );
        }
    }

    private void collectItems(
            Location location,
            Inventory inventory,
            int range
    ) {

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

            if (!item.isValid()
                    || item.isDead()) {
                continue;
            }

            ItemStack stack =
                    item.getItemStack();

            if (stack.isEmpty()) {
                continue;
            }

            Map<Integer, ItemStack> leftover =
                    inventory.addItem(stack);

            if (leftover.isEmpty()) {

                item.remove();

                continue;
            }

            ItemStack remaining =
                    leftover.values()
                            .iterator()
                            .next();

            item.setItemStack(
                    remaining
            );
        }
    }
}
