package me.mytheria.hoppers;

import me.mytheria.hoppers.commands.HopperCommand;
import me.mytheria.hoppers.hopper.HopperManager;
import me.mytheria.hoppers.hopper.HopperTask;
import me.mytheria.hoppers.listeners.GUIListener;
import me.mytheria.hoppers.listeners.HopperInteractListener;
import org.bukkit.plugin.java.JavaPlugin;

public class MytheriaHoppers extends JavaPlugin {

    private static MytheriaHoppers instance;

    private HopperManager hopperManager;

    @Override
    public void onEnable() {

        instance = this;

        saveDefaultConfig();

        hopperManager = new HopperManager(this);

        getServer()
                .getPluginManager()
                .registerEvents(
                        new HopperInteractListener(this),
                        this
                );

        getServer()
                .getPluginManager()
                .registerEvents(
                        new GUIListener(this),
                        this
                );


        getCommand("mytheriahoppers")
                .setExecutor(new HopperCommand(this));


        int interval = getConfig()
                .getInt("settings.task-interval", 20);


        new HopperTask(this)
                .runTaskTimer(
                        this,
                        interval,
                        interval
                );


        getLogger().info("MytheriaHoppers enabled!");
    }


    @Override
    public void onDisable() {
        getLogger().info("MytheriaHoppers disabled!");
    }


    public static MytheriaHoppers getInstance() {
        return instance;
    }


    public HopperManager getHopperManager() {
        return hopperManager;
    }
}
