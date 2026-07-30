package me.mytheria.hoppers;

import me.mytheria.hoppers.commands.HopperCommand;
import me.mytheria.hoppers.economy.EconomyManager;
import me.mytheria.hoppers.gui.UpgradeManager;
import me.mytheria.hoppers.hopper.HopperManager;
import me.mytheria.hoppers.hopper.HopperTask;
import me.mytheria.hoppers.listeners.GUIListener;
import me.mytheria.hoppers.listeners.HopperBreakListener;
import me.mytheria.hoppers.listeners.HopperInteractListener;
import me.mytheria.hoppers.listeners.HopperPlaceListener;
import me.mytheria.hoppers.storage.DataManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MytheriaHoppers extends JavaPlugin {


    private static MytheriaHoppers instance;

    private HopperManager hopperManager;

    private EconomyManager economyManager;

    private UpgradeManager upgradeManager;

    private DataManager dataManager;



    @Override
    public void onEnable() {


        instance = this;


        saveDefaultConfig();



        dataManager = new DataManager(this);



        hopperManager = new HopperManager(this);



        dataManager.load();



        economyManager = new EconomyManager(this);



        upgradeManager = new UpgradeManager(this);



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


        getServer()
                .getPluginManager()
                .registerEvents(
                        new HopperPlaceListener(this),
                        this
                );


        getServer()
                .getPluginManager()
                .registerEvents(
                        new HopperBreakListener(this),
                        this
                );



        getCommand("mytheriahoppers")
                .setExecutor(
                        new HopperCommand(this)
                );



        int interval =
                getConfig()
                        .getInt(
                                "settings.task-interval",
                                20
                        );



        new HopperTask(this)
                .runTaskTimer(
                        this,
                        interval,
                        interval
                );



        getLogger()
                .info(
                        "MytheriaHoppers enabled!"
                );

    }



    @Override
    public void onDisable() {


        if (dataManager != null) {

            dataManager.save();

        }


        getLogger()
                .info(
                        "MytheriaHoppers disabled!"
                );

    }




    public static MytheriaHoppers getInstance() {

        return instance;

    }



    public HopperManager getHopperManager() {

        return hopperManager;

    }



    public EconomyManager getEconomyManager() {

        return economyManager;

    }



    public UpgradeManager getUpgradeManager() {

        return upgradeManager;

    }



    public DataManager getDataManager() {

        return dataManager;

    }

}
