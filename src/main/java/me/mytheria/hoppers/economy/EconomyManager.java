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

        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            plugin.getLogger().warning(
                    "Vault was not found. Economy upgrades are disabled."
            );
            return;
        }

        RegisteredServiceProvider<Economy> registration =
                Bukkit.getServicesManager()
                        .getRegistration(Economy.class);

        if (registration == null) {
            plugin.getLogger().warning(
                    "No Vault economy provider was found."
            );
            return;
        }

        Economy economy = registration.getProvider();

        if (economy == null) {
            plugin.getLogger().warning(
                    "Vault returned no economy provider."
            );
            return;
        }

        provider = new VaultEconomy(economy);

        plugin.getLogger().info(
                "Economy hooked through Vault: "
                        + economy.getName()
        );
    }

    public EconomyProvider getProvider() {
        return provider;
    }
}
