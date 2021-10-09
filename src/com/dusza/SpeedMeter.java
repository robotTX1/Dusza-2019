package com.dusza;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SpeedMeter {
    public static String dateFormat = "HH:MM:SS";

    private int distance;
    private List<Data> records;

    public SpeedMeter(int distance) {
        this.distance = distance;
        records = new ArrayList<>();
    }

    public int getDistance() {
        return distance;
    }

    public List<Data> getRecords() {
        return records;
    }


    public void addRecord(String input) {
        String[] data = input.split(",");
        String felsegJel = data[0];
        String jelszam = data[1];
        VehicleType type = null;

        char location = data[2].toCharArray()[0];
        switch (data[3]) {
            case "sz" -> type = VehicleType.CAR;
            case "m" -> type = VehicleType.MOTOR;
            case "b" -> type = VehicleType.BUS;
            case "t" -> type = VehicleType.HEAVY;
            case "mk" -> type = VehicleType.SPECIAL;
        }
        int speed;
        Date time;
        speed = Integer.parseInt(data[4]);
        time = formatStringToDate(data[5]);

        records.add(new Data(felsegJel, jelszam, location, type, speed, time));
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


    // fel 1

    private List<Data> getSpeeders() {
        List<Data> out = new ArrayList<>();
        for (Data v : records) {
            if (v.getSpeed() > v.getType().getSpeedLimit()) {
                out.add(v);
            }
        }
        return out;
    }

    public int Fel1_GetSpeederCount() {
        return getSpeeders().size();
    }

    public String Fel2_GetSpeeders() {
        String out = "";
        return "";
    }
}
