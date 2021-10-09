package com.dusza;

import java.lang.invoke.VarHandle;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.dusza.Data.formatStringToDate;

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
        String rendszam = data[1];
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

        records.add(new Data(felsegJel, rendszam, location, type, speed, time));
    }

    public List<Data> getSpeeders(VehicleType... type) {
        List<Data> out = new ArrayList<>();
        Set<VehicleType> types = new HashSet<VehicleType>(List.of(type));

        for (Data v : records) {
            if (types.contains(v.getType()) && v.getSpeed() > v.getType().getSpeedLimit()) {
                out.add(v);
            }
        }
        return out;
    }

    public boolean isPresent(String rendszam) {
        for(Data d : records) {
            if(d.getRendSzam().equals(rendszam)) return true;
        }
        return false;
    }

    public Data getData(int index) {
        return records.get(index);
    }

    public Data getData(String rendszam) {
        for(int i=0; i<records.size(); i++) {
            if(rendszam.equals(records.get(i).getRendSzam())) return getData(i);
        }
        return null;
    }
}
