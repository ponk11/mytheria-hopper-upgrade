package me.mytheria.hoppers.economy;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;

public class VaultEconomy implements EconomyProvider {

    private final Economy economy;

    public VaultEconomy(Economy economy) {
        this.economy = economy;
    }

    @Override
    public boolean has(Player player, double amount) {
        return economy.has(
                player,
                amount
        );
    }

    @Override
    public boolean withdraw(
            Player player,
            double amount
    ) {

        return economy.withdrawPlayer(
                player,
                amount
        ).transactionSuccess();
    }

    @Override
    public String getName() {
        return economy.getName();
    }
}
