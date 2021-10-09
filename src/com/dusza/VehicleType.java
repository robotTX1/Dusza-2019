package com.dusza;

public enum VehicleType {
    CAR("sz", 130),
    MOTOR("m", 130),
    BUS("b", 100),
    HEAVY("t", 80),
    SPECIAL("sz", Integer.MAX_VALUE);

    private String type;
    private int speedLimit;
    private VehicleType(String type, int speedLimit) {
        this.type = type;
        this.speedLimit = speedLimit;
    }

    public String getType() {
        return type;
    }

    public int getSpeedLimit() {
        return speedLimit;
    }
}
