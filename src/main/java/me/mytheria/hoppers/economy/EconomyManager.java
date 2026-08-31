package me.mytheria.hoppers.economy;

import me.mytheria.hoppers.MytheriaHoppers;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class EconomyManager {

    private final MytheriaHoppers plugin;
    private EconomyProvider provider;

    public EconomyManager(MytheriaHoppers plugin) {
        this.plugin = plugin;
        setup();
    }

    public boolean setup() {
        if (provider != null) {
            return true;
        }

        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            plugin.getLogger().warning("Vault was not found. Economy upgrades are disabled.");
            return false;
        }

        RegisteredServiceProvider<Economy> registration =
                Bukkit.getServicesManager().getRegistration(Economy.class);

        if (registration == null) {
            plugin.getLogger().warning("No Vault economy provider was found.");
            return false;
        }

        Economy economy = registration.getProvider();

        if (economy == null) {
            plugin.getLogger().warning("Vault returned no economy provider.");
            return false;
        }

        provider = new VaultEconomy(economy);
        plugin.getLogger().info("Economy hooked through Vault: " + economy.getName());
        return true;
    }

    public boolean has(Player player, double amount) {
        if (provider == null && !setup()) {
            return false;
        }
        return provider.has(player, amount);
    }

    public boolean withdraw(Player player, double amount) {
        if (provider == null && !setup()) {
            return false;
        }
        return provider.withdraw(player, amount);
    }

    public EconomyProvider getProvider() {
        return provider;
    }
}
