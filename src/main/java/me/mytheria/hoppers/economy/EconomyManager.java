package me.mytheria.hoppers.economy;

public class EconomyManager {

    private final EconomyProvider provider;

    public EconomyManager(EconomyProvider provider) {
        this.provider = provider;
    }

    public EconomyProvider getProvider() {
        return provider;
    }
}
