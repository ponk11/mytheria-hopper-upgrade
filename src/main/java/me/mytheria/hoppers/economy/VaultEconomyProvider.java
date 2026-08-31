package me.mytheria.hoppers.economy;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class VaultEconomyProvider implements EconomyProvider {

    private final Economy economy;

    public VaultEconomyProvider(Economy economy) {
        this.economy = economy;
    }

    @Override
    public String getName() {
        return "Vault";
    }

    public Economy getEconomy() {
        return economy;
    }

    public boolean has(Player player, double amount) {
        return has((OfflinePlayer) player, amount);
    }

    public boolean has(OfflinePlayer player, double amount) {
        return economy != null && economy.has(player, amount);
    }

    public boolean withdraw(Player player, double amount) {
        return withdraw((OfflinePlayer) player, amount);
    }

    public boolean withdraw(OfflinePlayer player, double amount) {
        if (economy == null) return false;
        return economy.withdrawPlayer(player, amount).transactionSuccess();
    }

    public boolean deposit(Player player, double amount) {
        return deposit((OfflinePlayer) player, amount);
    }

    public boolean deposit(OfflinePlayer player, double amount) {
        if (economy == null) return false;
        return economy.depositPlayer(player, amount).transactionSuccess();
    }

    public double getBalance(Player player) {
        return getBalance((OfflinePlayer) player);
    }

    public double getBalance(OfflinePlayer player) {
        if (economy == null) return 0.0;
        return economy.getBalance(player);
    }
}
