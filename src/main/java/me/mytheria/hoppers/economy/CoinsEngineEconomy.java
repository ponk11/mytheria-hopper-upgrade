package me.mytheria.hoppers.economy;

import org.bukkit.entity.Player;

public class CoinsEngineEconomy implements EconomyProvider {

    @Override
    public boolean has(Player player, double amount) {
        return false;
    }

    @Override
    public boolean withdraw(Player player, double amount) {
        return false;
    }

    @Override
    public String getName() {
        return "CoinsEngine";
    }
}
