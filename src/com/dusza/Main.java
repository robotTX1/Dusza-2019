package com.dusza;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        Path workDir = FileSystems.getDefault().getPath("Data");

        CommandLineInterface cli = new CommandLineInterface(workDir);

        Path filePath = cli.start();

        if(filePath == null) System.exit(0);

        IOHandler ioHandler = new IOHandler(filePath);

        String name = String.valueOf(filePath.getFileName()).split("\\.")[0];
        Path logFile = FileSystems.getDefault().getPath("Valasz" + name + ".txt");

        Logger logger = new Logger(logFile);

        /////////////////////////////////////////////////////////////////////////////////

        Control control = new Control(ioHandler.readFile());

        // 1. feladat

        SpeedMeter meter = control.getSpeedMeter('A');
        List<Data> dataList = meter.getSpeeders(VehicleType.MOTOR);

        logger.log(dataList.size());

        // 2. feladat
        meter = control.getSpeedMeter('B');
        //dataList = meter.getSpeeders(VehicleType.CAR, VehicleType.)


        // 3. feladat
        logger.nextTask();
        meter = control.getSpeedMeter('C');

        int biggestSpeed = 0;
        Data fastestV = meter.getRecords().get(0);
        List<Data> speeders = meter.getSpeeders(VehicleType.CAR, VehicleType.BUS, VehicleType.HEAVY,
                VehicleType.MOTOR, VehicleType.SPECIAL);

        for (Data v : speeders) {
            if (v.getSpeed() > biggestSpeed) {
                biggestSpeed = v.getSpeed();
                fastestV = v;
            }
        }

        for (Data v : speeders) {
            String tullepte;

            if (fastestV.getType().getSpeedLimit() > biggestSpeed) {
                tullepte = " túllépte ";
            } else {
                tullepte = " nem_lépte_túl ";
            }

            logger.log(biggestSpeed + tullepte + fastestV.getAllInformation());
        }


        // 4. feladat
        logger.nextTask();
        Set<String> rendSzamok = new HashSet<>();
        for (SpeedMeter sMeter : control.getMeterList())
        {
            for (Data v : sMeter.getRecords()) {
                if (v.getFelsegJel().equals("H")) {
                    rendSzamok.add(v.getRendSzam());
                }
            }
        }
        logger.log(rendSzamok.size());

        // 6. feladat
        logger.nextTask();
        SpeedMeter speedMeterA;
        SpeedMeter speedMeterB;
        int speedMeterIndex = 0;
        while (speedMeterIndex+1 < control.getMeterList().size()) {
            speedMeterA = control.getMeterList().get(speedMeterIndex);
            speedMeterB = control.getMeterList().get(speedMeterIndex+1);

            int distanceAB = Math.abs(speedMeterA.getDistance() - speedMeterB.getDistance());

            // azok a járművek, amelyek mindkét ellenőrzőpontnál elhaladtak:
            List<Data> mind2 = new ArrayList<>();

            for (Data v : speedMeterA.getRecords()) {

                for (Data k : speedMeterB.getRecords()) {
                    if (v.getRendSzam().equals(k.getRendSzam())) {
                        mind2.add(v);
                        mind2.add(k);
                        break;
                    }
                }
            }

            // ezen járművek átlagsebbessége

            float[] avgSpeeds = new float[(int)mind2.size()/2];
            System.out.println(mind2.size());
            for (int i = 0; i<mind2.size(); i+=2) {

                Date t1 = mind2.get(i).getTime();
                Date t2 = mind2.get(i+1).getTime();


                long dt = Math.abs( t2.getTime()-t1.getTime());

                TimeUnit time = TimeUnit.HOURS;
                float dTimeH = time.convert(dt, TimeUnit.MILLISECONDS);
                avgSpeeds[i/2] = distanceAB / dTimeH;
            }

            // Gyorshajtók kiszűrése és kiírása
            char[] abc = "ABCDEFG".toCharArray();
            for (int i = 0; i < mind2.size(); i+=2) {
                Data v = mind2.get(i);
                if (v.getSpeed() > v.getType().getSpeedLimit()) {
                    logger.log(String.format("%s %s %s %s %s", v.getFelsegJel(),
                            v.getRendSzam(), abc[speedMeterIndex], abc[speedMeterIndex+1], avgSpeeds[i/2]));
                }
            }

            ++speedMeterIndex;
        }


        logger.save();


    }
}
