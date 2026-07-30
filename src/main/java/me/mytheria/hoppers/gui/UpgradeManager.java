package me.mytheria.hoppers.gui;

import me.mytheria.hoppers.MytheriaHoppers;
import me.mytheria.hoppers.hopper.HopperData;
import me.mytheria.hoppers.economy.EconomyProvider;
import org.bukkit.entity.Player;

public class UpgradeManager {


    private final MytheriaHoppers plugin;


    public UpgradeManager(MytheriaHoppers plugin) {
        this.plugin = plugin;
    }



    public boolean upgradeSpeed(Player player, HopperData data) {


        int next =
                data.getSpeedLevel() + 1;


        int cost =
                plugin.getConfig()
                        .getInt(
                        "speed-upgrades."
                        + next
                        + ".cost"
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



        economy.withdraw(
                player,
                cost
        );


        data.setSpeedLevel(next);


        return true;

    }





    public boolean upgradeRange(Player player, HopperData data) {


        int next =
                data.getRangeLevel() + 1;


        int cost =
                plugin.getConfig()
                        .getInt(
                        "range-upgrades."
                        + next
                        + ".cost"
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



        economy.withdraw(
                player,
                cost
        );


        data.setRangeLevel(next);


        return true;

    }

}
