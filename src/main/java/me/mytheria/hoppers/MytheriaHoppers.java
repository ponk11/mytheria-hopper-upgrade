package me.mytheria.hoppers;

import me.mytheria.hoppers.commands.HopperCommand;
import me.mytheria.hoppers.economy.EconomyManager;
import me.mytheria.hoppers.hopper.HopperManager;
import me.mytheria.hoppers.hopper.HopperTask;
import me.mytheria.hoppers.listeners.GUIListener;
import me.mytheria.hoppers.listeners.HopperInteractListener;
import me.mytheria.hoppers.storage.DataManager;
import org.bukkit.plugin.java.JavaPlugin;
import me.mytheria.hoppers.gui.UpgradeManager;

public class MytheriaHoppers extends JavaPlugin {


    private static MytheriaHoppers instance;

    private HopperManager hopperManager;

    private EconomyManager economyManager;

    private DataManager dataManager;

    private UpgradeManager upgradeManager;



    @Override
    public void onEnable() {


        instance = this;


        saveDefaultConfig();


        dataManager =
                new DataManager(this);
        
        dataManager.load();


        hopperManager =
                new HopperManager(this);


        economyManager =
                new EconomyManager(this);

        upgradeManager =
                new UpgradeManager(this);



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
                .setExecutor(
                        new HopperCommand(this)
                );



        new HopperTask(this)
                .runTaskTimer(
                        this,
                        20,
                        getConfig()
                                .getInt(
                                "settings.task-interval",
                                20)
                );


        getLogger()
                .info(
                "MytheriaHoppers enabled."
                );

    }



    @Override
    public void onDisable() {

        dataManager.save();

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


    public DataManager getDataManager() {

        return dataManager;

    }


    public UpgradeManager getUpgradeManager() {

        return upgradeManager;

}

}
