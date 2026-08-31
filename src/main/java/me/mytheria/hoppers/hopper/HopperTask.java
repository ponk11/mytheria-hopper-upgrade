package me.mytheria.hoppers.hopper;

import me.mytheria.hoppers.MytheriaHoppers;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
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

            Block block = location.getBlock();
            if (block.getType() != Material.HOPPER) {
                continue;
            }

            if (block.isBlockPowered() || block.isBlockIndirectlyPowered()) {
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

            int amountToTransfer = plugin.getConfig().getInt(
                    "speed-upgrades." + speedLevel + ".transfer-amount",
                    1
            );

            collectItems(location, data, range, amountToTransfer);
        }
    }

    private void collectItems(Location location, HopperData data, int range, int maxAmount) {
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

            if (!data.isAllowedByFilter(stack.getType())) {
                continue;
            }

            int transferCount = Math.min(stack.getAmount(), maxAmount);
            ItemStack stackToInsert = stack.clone();
            stackToInsert.setAmount(transferCount);

            Map<Integer, ItemStack> leftover = inventory.addItem(stackToInsert);

            int successfullyAdded = transferCount - (leftover.isEmpty() ? 0 : leftover.values().iterator().next().getAmount());

            if (successfullyAdded > 0) {
                if (stack.getAmount() <= successfullyAdded) {
                    item.remove();
                } else {
                    stack.setAmount(stack.getAmount() - successfullyAdded);
                    item.setItemStack(stack);
                }
                break;
            }
        }
    }
}
