package com.dusza;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class Control {
    private static final char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();

    private static final String REGEX = ",";

    private final List<SpeedMeter> meterList = new ArrayList<>();

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


}
