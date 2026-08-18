package me.mytheria.hoppers.economy;

import org.bukkit.entity.Player;

public interface EconomyProvider {

    boolean has(
            Player player,
            double amount
    );

    boolean withdraw(
            Player player,
            double amount
    );

    String getName();
}
