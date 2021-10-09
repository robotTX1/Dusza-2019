package com.dusza;

import java.lang.invoke.VarHandle;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SpeedMeter {
    public static String dateFormat = "HH:MM:SS";
    private final int distance;
    private final List<Data> records;

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
        VehicleType type = VehicleType.CAR;

        char location = data[2].toCharArray()[0];
        switch (data[3]) {
            case "sz" -> {
            }
            case "m" -> type = VehicleType.MOTOR;
            case "b" -> type = VehicleType.BUS;
            case "t" -> type = VehicleType.HEAVY;
            case "mk" -> type = VehicleType.SPECIAL;
        }
        int speed;
        Date time;
        speed = Integer.parseInt(data[4]);
        time = formatStringtoDate(data[5]);

        records.add(new Data(felsegJel, jelszam, location, type, speed, time));
    }



    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

        return sdf.format(date);
    }

    public static Date formatStringtoDate(String inp) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

        try {
            return sdf.parse(inp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    // fel 1

    public List<Data> getSpeeders(VehicleType... type) {
        List<Data> out = new ArrayList<>();
        Set<VehicleType> types = new HashSet<>(List.of(type));

        for (Data v : records) {
            if (types.contains(v.getType()) && v.getSpeed() > v.getType().getSpeedLimit()) {
                out.add(v);
            }
        }
        return out;
    }

    public int Fel1_GetSpeederCount() {
        return getSpeeders(VehicleType.MOTOR).size();
    }

    public String Fel2_GetSpeeders() {
        String out = "";
        List<Data> speeders = getSpeeders();

        Set<String> types = new HashSet<>();
        types.add("sz");
        types.add("b");
        types.add("t");


        for (Data v : speeders) {
            if (types.contains(v.getType().getType())) {
                out += v.getInformationWithoutTime() + " "
                        + (v.getSpeed()-v.getType().getSpeedLimit())
                        + "\n";
            }
        }

        return out;
    }

    public String Fel3() {
        // leggyorsabb jármű
        String out = "";
        int maxSpeed = 0;
        Data fastestV = records.get(0);

        for (Data v : records) {
            if (v.getSpeed() > maxSpeed) {
                maxSpeed = v.getSpeed();
                fastestV = v;
            }
        }
        out += maxSpeed;

        if (fastestV.getType().getSpeedLimit() > maxSpeed) {
            out += " túllépte ";
        } else {
            out += " nem_lépte_túl ";
        }

        out += fastestV.getAllInformation();

        return out;
    }

    public String Fel4() {
        Set<String> rendszamok = new HashSet<>();

        for (Data v : records) {
            if (v.getFelsegJel().equals("H") && ! rendszamok.contains(v.getRendSzam())) {
                rendszamok.add(v.getRendSzam());
            }

        }
    return "";
    }
}
