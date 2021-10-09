package com.dusza;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Control {
    private static final char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();

    private static final String REGEX = ",";

    private final List<SpeedMeter> meterList = new ArrayList<>();
    private final List<Data> allData = new ArrayList<>();

    public Control(List<String> input) {
        String[] meters = input.get(0).split(REGEX);

        for(String s : meters) {
            int num = Integer.parseInt(s);
            SpeedMeter meter = new SpeedMeter(num);
            meterList.add(meter);
        }

        for(int i=1; i<input.size(); i++) {
            char ch = input.get(i).split(REGEX)[2].charAt(0);

            SpeedMeter speedMeter = getSpeedMeter(ch);
            speedMeter.addRecord(input.get(i));
        }

        for(SpeedMeter sm : meterList) {
            allData.addAll(sm.getRecords());
        }
    }

    public SpeedMeter getSpeedMeter(int index) {
        return meterList.get(index);
    }

    public SpeedMeter getSpeedMeter(char c) {
        int index = getABC(c);
        if(index == -1) return null;
        return getSpeedMeter(index);
    }

    public List<SpeedMeter> getMeterList() {
        return Collections.unmodifiableList(meterList);
    }

    private int getABC(char c) {
        for(int i = 0; i< ALPHABET.length; i++) {
            if(c == ALPHABET[i]) return i;
        }
        return -1;
    }

    public boolean isPresentAtAllPoints(String rendszam) {
        boolean present = true;

        for(SpeedMeter speedMeter : meterList) {
            present = present && speedMeter.isPresent(rendszam);
        }

        return present;
    }

    public boolean isPresentAtPoints(String rendszam, char start, char end) {
        boolean present = true;

        int startIndex = getABC(start);
        int endIndex = getABC(end);

        for(int i = startIndex; i < endIndex; i++) {
            present = present && meterList.get(i).isPresent(rendszam);
        }

        return present;
    }

    public float getAverageSpeed(String rendszam, char start, char end) {
        int startIndex = getABC(start);
        int endIndex = getABC(end);

        int distance = Math.abs(meterList.get(endIndex).getDistance() - meterList.get(startIndex).getDistance());

        if(isPresentAtPoints(rendszam, start, end)) {
            SpeedMeter m1 = getSpeedMeter(start);
            SpeedMeter m2 = getSpeedMeter(end);

            Data d1 = m1.getData(rendszam);
            Data d2 = m2.getData(rendszam);

            Date t1 = d1.getTime();
            Date t2 = d2.getTime();

            long dt = Math.abs( t2.getTime()-t1.getTime());

            TimeUnit time = TimeUnit.HOURS;
            float dTimeH = time.convert(dt, TimeUnit.MILLISECONDS);

            return distance / dTimeH;
        }
        return -1f;
    }

    public List<Data> getAllData() {
        return allData;
    }
}
