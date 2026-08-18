package me.mytheria.hoppers.gui;

import me.mytheria.hoppers.MytheriaHoppers;
import me.mytheria.hoppers.economy.EconomyProvider;
import me.mytheria.hoppers.hopper.HopperData;
import org.bukkit.entity.Player;

public class UpgradeManager {

    private final MytheriaHoppers plugin;

    public UpgradeManager(MytheriaHoppers plugin) {
        this.plugin = plugin;
    }

    public boolean upgradeSpeed(
            Player player,
            HopperData data
    ) {

        int nextLevel =
                data.getSpeedLevel() + 1;

        int maxLevel =
                plugin.getConfig().getInt(
                        "settings.max-speed-level",
                        10
                );

        if (nextLevel > maxLevel) {
            return false;
        }

        String path =
                "speed-upgrades."
                        + nextLevel;

        if (!plugin.getConfig().contains(path)) {
            return false;
        }

        double cost =
                plugin.getConfig().getDouble(
                        path + ".cost"
                );

        EconomyProvider economy =
                plugin.getEconomyManager()
                        .getProvider();

        if (economy == null) {
            return false;
        }

        if (!economy.has(player, cost)) {
            return false;
        }

        if (!economy.withdraw(player, cost)) {
            return false;
        }

        data.setSpeedLevel(nextLevel);

        return true;
    }

    public boolean upgradeRange(
            Player player,
            HopperData data
    ) {

        int nextLevel =
                data.getRangeLevel() + 1;

        int maxLevel =
                plugin.getConfig().getInt(
                        "settings.max-range-level",
                        10
                );

        if (nextLevel > maxLevel) {
            return false;
        }

        String path =
                "range-upgrades."
                        + nextLevel;

        if (!plugin.getConfig().contains(path)) {
            return false;
        }

        double cost =
                plugin.getConfig().getDouble(
                        path + ".cost"
                );

        EconomyProvider economy =
                plugin.getEconomyManager()
                        .getProvider();

        if (economy == null) {
            return false;
        }

        if (!economy.has(player, cost)) {
            return false;
        }

        if (!economy.withdraw(player, cost)) {
            return false;
        }

        data.setRangeLevel(nextLevel);

        return true;
    }
}
