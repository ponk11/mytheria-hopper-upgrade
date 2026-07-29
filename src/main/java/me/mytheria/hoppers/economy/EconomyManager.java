package me.mytheria.hoppers.economy;

import me.mytheria.hoppers.MytheriaHoppers;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;

public class EconomyManager {


    private final MytheriaHoppers plugin;

    private EconomyProvider provider;


    public EconomyManager(MytheriaHoppers plugin) {

        this.plugin = plugin;

        setup();

    }


    private void setup() {


        if (Bukkit.getPluginManager()
                .getPlugin("Vault") != null) {


            Economy economy =
                    Bukkit.getServicesManager()
                            .getRegistration(Economy.class)
                            .getProvider();


            if (economy != null) {

                provider =
                        new VaultEconomy(economy);

                plugin.getLogger()
                        .info("Using Vault economy.");

                return;

            }

        }


        plugin.getLogger()
                .warning(
                        "No economy provider found!"
                );

    }



    public EconomyProvider getProvider() {

        return provider;

    }
}
