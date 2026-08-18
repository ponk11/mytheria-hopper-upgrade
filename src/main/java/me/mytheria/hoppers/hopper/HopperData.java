package me.mytheria.hoppers.hopper;

public class HopperData {

    private int speedLevel;
    private int rangeLevel;

    private long lastTransferTick;

    public HopperData() {
        this.speedLevel = 0;
        this.rangeLevel = 0;
        this.lastTransferTick = 0;
    }

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

    public long getLastTransferTick() {
        return lastTransferTick;
    }

    public void setLastTransferTick(long lastTransferTick) {
        this.lastTransferTick = lastTransferTick;
    }
}
