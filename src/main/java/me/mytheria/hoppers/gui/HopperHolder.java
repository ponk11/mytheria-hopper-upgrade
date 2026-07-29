package me.mytheria.hoppers.gui;

import org.bukkit.Location;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class HopperHolder implements InventoryHolder {


    private final Location location;


    public HopperHolder(Location location) {
        this.location = location;
    }


    public Location getLocation() {
        return location;
    }


    @Override
    public Inventory getInventory() {
        return null;
    }
}
