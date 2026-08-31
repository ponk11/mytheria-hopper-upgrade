package me.mytheria.hoppers.hopper;

import me.mytheria.hoppers.MytheriaHoppers;
import org.bukkit.Location;
import org.bukkit.Material;
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
        int currentTick = plugin.getServer().getCurrentTick();

        for (Map.Entry<Location, HopperData> entry : plugin.getHopperManager().getHoppers().entrySet()) {
            Location location = entry.getKey();
            HopperData data = entry.getValue();

            if (!location.getChunk().isLoaded()) {
                continue;
            }

            if (location.getBlock().getType() != Material.HOPPER) {
                continue;
            }

            int speedLevel = data.getSpeedLevel();
            int transferTicks = plugin.getConfig().getInt(
                    "speed-upgrades." + speedLevel + ".transfer-ticks",
                    8
            );

            if (currentTick - data.getLastTransferTick() < transferTicks) {
                continue;
            }

            data.setLastTransferTick(currentTick);

            int rangeLevel = data.getRangeLevel();
            int range = 1;
            if (rangeLevel > 0) {
                range = plugin.getConfig().getInt(
                        "range-upgrades." + rangeLevel + ".range",
                        1
                );
            }

            collectItems(location, range);
        }
    }

    private void collectItems(Location location, int range) {
        if (location.getWorld() == null) {
            return;
        }

        Inventory inventory = ((org.bukkit.block.Hopper) location.getBlock().getState()).getInventory();

        for (Entity entity : location.getWorld().getNearbyEntities(location, range, range, range)) {
            if (!(entity instanceof Item item)) {
                continue;
            }

            if (!item.isValid() || item.isDead()) {
                continue;
            }

            ItemStack stack = item.getItemStack();
            if (stack.isEmpty()) {
                continue;
            }

            Map<Integer, ItemStack> leftover = inventory.addItem(stack);

            if (leftover.isEmpty()) {
                item.remove();
                continue;
            }

            ItemStack remaining = leftover.values().iterator().next();
            item.setItemStack(remaining);
        }
    }
}
