package me.mytheria.hoppers.hopper;

public class HopperData {

    private int speedLevel;
    private int rangeLevel;


    public HopperData() {

        speedLevel = 0;
        rangeLevel = 0;

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

}
