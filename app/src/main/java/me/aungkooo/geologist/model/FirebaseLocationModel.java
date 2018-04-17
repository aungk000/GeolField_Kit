package me.aungkooo.geologist.model;

/**
 * Created by root on 11/27/17.
 */

public class FirebaseLocationModel {
    private String rockType;
    private String rockUnit;
    // public String latitude;
    // public String longtitude;
    private String gps;

    public FirebaseLocationModel(){}

    /*public FirebaseLocationModel(String latitude, String longtitude, String rock_unit, String rock_type) {
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.rock_unit = rock_unit;
        this.rock_type = rock_type;
    }*/

    public FirebaseLocationModel(String rockType, String rockUnit, String gps) {
        this.rockType = rockType;
        this.rockUnit = rockUnit;
        this.gps = gps;
    }

    public void setRockType(String rockType) {
        this.rockType = rockType;
    }

    public void setRockUnit(String rockUnit) {
        this.rockUnit = rockUnit;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public String getRockType() {
        return rockType;
    }

    public String getRockUnit() {
        return rockUnit;
    }

    public String getGps() {
        return gps;
    }
}
