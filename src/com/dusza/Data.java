package com.dusza;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Data {
    public static String dateFormat = "hh:mm:ss";
    private String felsegJel;
    private String rendSzam;
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

    public String getRendSzam() {
        return rendSzam;
    }

    public void setJelszam(String jelszam) {
        this.rendSzam = jelszam;
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
        this.rendSzam = jelszam;
        this.location = location;
        this.type = type;
        this.speed = speed;
        this.time = time;
    }


    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

        return sdf.format(date);
    }

    public static Date formatStringToDate(String inp) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

        try {
            return sdf.parse(inp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getAllInformation() {
        return getType().getType() + " "
                + getFelsegJel() + " "
                + getRendSzam() + " "
                + getSpeed() + " "
                + formatDate(time);
    }

    public String getInformationWithoutTime() {
        return getType().getType() + " "
                + getFelsegJel() + " "
                + getRendSzam() + " "
                + getSpeed();
    }

}
