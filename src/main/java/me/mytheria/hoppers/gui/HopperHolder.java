package me.mytheria.hoppers.gui;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class HopperHolder implements InventoryHolder {


    private final Location location;

    private Inventory inventory;


    public HopperHolder(Location location) {

        this.location = location;

    }



    public Location getLocation() {

        return location;

    }



    public void setInventory(Inventory inventory) {

        this.inventory = inventory;

    }



    @Override
    public Inventory getInventory() {

        if (inventory == null) {

            inventory = Bukkit.createInventory(
                    this,
                    27
            );

        }

        return inventory;

    }

}
