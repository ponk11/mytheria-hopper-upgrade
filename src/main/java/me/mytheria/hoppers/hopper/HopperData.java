package me.mytheria.hoppers.hopper;

import org.bukkit.Material;
import java.util.HashSet;
import java.util.Set;

public class HopperData {

    private int speedLevel = 0;
    private int rangeLevel = 0;
    private int lastTransferTick = 0;
    private boolean filterEnabled = false;
    private final Set<Material> filteredMaterials = new HashSet<>();

    public int getSpeedLevel() {
        return speedLevel;
    }

    public void setSpeedLevel(int speedLevel) {
        this.speedLevel = speedLevel;
    }

    public int getRangeLevel() {
        return rangeLevel;
    }

    public void setRangeLevel(int rangeLevel) {
        this.rangeLevel = rangeLevel;
    }

    public int getLastTransferTick() {
        return lastTransferTick;
    }

    public void setLastTransferTick(int lastTransferTick) {
        this.lastTransferTick = lastTransferTick;
    }

    public boolean isFilterEnabled() {
        return filterEnabled;
    }

    public void setFilterEnabled(boolean filterEnabled) {
        this.filterEnabled = filterEnabled;
    }

    public Set<Material> getFilteredMaterials() {
        return filteredMaterials;
    }

    public boolean isAllowedByFilter(Material material) {
        if (!filterEnabled) {
            return true;
        }
        return filteredMaterials.contains(material);
    }

    public void addFilterMaterial(Material material) {
        filteredMaterials.add(material);
    }

    public void removeFilterMaterial(Material material) {
        filteredMaterials.remove(material);
    }
}
