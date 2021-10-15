package com.dusza;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.SQLOutput;
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
        logger.nextTask();

        meter = control.getSpeedMeter('B');
        dataList = meter.getSpeeders(VehicleType.CAR, VehicleType.BUS, VehicleType.HEAVY);

        for(Data d : dataList) {
            logger.log(String.format("%s %s %s %d", d.getType().getType(), d.getFelsegJel(), d.getRendSzam(), d.getSpeed() - d.getType().getSpeedLimit()));
        }
      
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
      
        // 5. feladat

        logger.nextTask();

        meter = control.getSpeedMeter('C');
        dataList = meter.getRecords();

        Date start = Data.formatStringToDate("09:00:00");
        Date end = Data.formatStringToDate("13:00:00");

        for(Data d : dataList) {
            if(d.getTime().after(start) && d.getTime().before(end)) {
                if(d.getType() == VehicleType.CAR && d.getSpeed() > 110 && d.getSpeed() <= 130) {
                    logger.log(d.getAllInformation());
                }
            }
        }

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

            float[] avgSpeeds = new float[mind2.size() /2];
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
      
      // 7. feladat
        logger.nextTask();
        dataList = control.getSpeedMeter('A').getRecords();

        for(Data d : dataList) {
            if(control.isPresentAtAllPoints(d.getRendSzam())) {
                float avgSpeed = control.getAverageSpeed(d.getRendSzam(), 'A', 'C');
                logger.log(String.format("%s %s %s", avgSpeed < d.getType().getSpeedLimit() ? "igen" : "nem", d.getFelsegJel(), d.getRendSzam()));
            }
        }

        // 8. feladat

        logger.nextTask();

        System.out.print("Kérek egy rendszámot ellenőrzésre: ");
        Scanner input = new Scanner(System.in);
        String rendszam = input.nextLine();
        input.close();

        List<SpeedMeter> speedMeters = control.presentAtPoints(rendszam);

        boolean tul = false;

        if(speedMeters.size() > 0) {
            for(SpeedMeter m : speedMeters) {
                dataList = m.getRecords();
                for(Data d : dataList) {
                    if(d.getType() == VehicleType.SPECIAL && d.getRendSzam().equalsIgnoreCase(rendszam)) {
                        if(d.getSpeed() > VehicleType.CAR.getSpeedLimit()) tul = true;
                        break;
                    }
                }

            }
            if(tul) {
                logger.log("szerepel tul_lepte");
            } else {
                logger.log("szerepel nem_lepte_tul");
            }
        } else {
            logger.log("nem_szerepel");
        }

        // 9. feladat

        logger.nextTask();

        dataList = control.getWrongHungarianRendszam();

        for(Data d : dataList) {
            logger.log(d.getRendSzam());
        }


        logger.save();


    }
}
