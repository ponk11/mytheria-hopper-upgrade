package me.mytheria.hoppers.economy;

import me.mytheria.hoppers.MytheriaHoppers;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class EconomyManager {


    private final MytheriaHoppers plugin;

    private EconomyProvider provider;



    public EconomyManager(MytheriaHoppers plugin) {

        this.plugin = plugin;

        setup();

    }



    private void setup() {


        String type =
                plugin.getConfig()
                        .getString(
                                "economy.provider",
                                "AUTO"
                        );



        if (
                (type.equalsIgnoreCase("AUTO")
                ||
                type.equalsIgnoreCase("COINSENGINE"))
                &&
                Bukkit.getPluginManager()
                        .getPlugin("CoinsEngine") != null
        ) {


            provider =
                    new CoinsEngineEconomy(
                            plugin.getConfig()
                                    .getString(
                                            "coinsengine.currency-id",
                                            "coins"
                                    )
                    );


            plugin.getLogger()
                    .info(
                            "Using CoinsEngine economy."
                    );


            return;

        }



        if (
                (type.equalsIgnoreCase("AUTO")
                ||
                type.equalsIgnoreCase("VAULT"))
                &&
                Bukkit.getPluginManager()
                        .getPlugin("Vault") != null
        ) {


            RegisteredServiceProvider<Economy> rsp =
                    Bukkit.getServicesManager()
                            .getRegistration(
                                    Economy.class
                            );


            if (rsp != null) {


                provider =
                        new VaultEconomy(
                                rsp.getProvider()
                        );


                plugin.getLogger()
                        .info(
                                "Using Vault economy."
                        );


                return;

            }

        }



        plugin.getLogger()
                .warning(
                        "No economy provider found. Upgrades disabled."
                );

    }



    public EconomyProvider getProvider() {

        return provider;

    }

}
