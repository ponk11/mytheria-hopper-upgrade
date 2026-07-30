package me.mytheria.hoppers.economy;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;

public class CoinsEngineEconomy implements EconomyProvider {


    private final String currency;


    public CoinsEngineEconomy(String currency) {

        this.currency = currency;

    }



    @Override
    public boolean has(Player player, double amount) {

        try {

            Class<?> api =
                    Class.forName(
                            "su.nightexpress.coinsengine.api.CoinsEngineAPI"
                    );


            Method method =
                    api.getMethod(
                            "getBalance",
                            Player.class,
                            String.class
                    );


            Object result =
                    method.invoke(
                            null,
                            player,
                            currency
                    );


            if (result instanceof Number number) {

                return number.doubleValue() >= amount;

            }


        } catch (Exception ignored) {

        }


        return false;

    }



    @Override
    public boolean withdraw(Player player, double amount) {

        try {

            Class<?> api =
                    Class.forName(
                            "su.nightexpress.coinsengine.api.CoinsEngineAPI"
                    );


            Method method =
                    api.getMethod(
                            "removeBalance",
                            Player.class,
                            String.class,
                            double.class
                    );


            method.invoke(
                    null,
                    player,
                    currency,
                    amount
            );


            return true;


        } catch (Exception ignored) {

        }


        return false;

    }



    @Override
    public String getName() {

        return "CoinsEngine";

    }

}
