package com.dusza;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Data {

    private String felsegJel;
    private String jelszam;
    private char location;
    private VehicleType type;
    private int speed;
    private Date time;

    public String getFelsegJel() {
        return felsegJel;
    }

    public void setFelsegJel(String felsegJel) {
        this.felsegJel = felsegJel;
    }

    public String getJelszam() {
        return jelszam;
    }

    public void setJelszam(String jelszam) {
        this.jelszam = jelszam;
    }

    public char getLocation() {
        return location;
    }

    public void setLocation(char location) {
        this.location = location;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Data(String felsegJel, String jelszam, char location, VehicleType type, int speed, Date time) {
        this.felsegJel = felsegJel;
        this.jelszam = jelszam;
        this.location = location;
        this.type = type;
        this.speed = speed;
        this.time = time;
    }

    private void fromInput(String input) {

    }

}
