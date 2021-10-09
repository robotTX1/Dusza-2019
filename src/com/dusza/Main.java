package com.dusza;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

        // 7. feladat
        logger.nextTask();
        dataList = control.getAllData();

        for(Data d : dataList) {
            if(control.isPresentAtAllPoints(d.getRendSzam())) {

            }
        }



        logger.save();
    }
}
