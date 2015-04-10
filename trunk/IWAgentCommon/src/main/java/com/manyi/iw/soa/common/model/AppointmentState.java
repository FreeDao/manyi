package com.manyi.iw.soa.common.model;

public enum AppointmentState {

    待确认(new Byte("1")), 待看房(new Byte("2")), 经纪人已签到(new Byte("3")), 未到未看房(new Byte("4")), 已到未看房(new Byte("5")), 已到已看房(
            new Byte("6")), 已改期(new Byte("7")), 已取消(new Byte("8"));
    private Byte value;

    private AppointmentState(Byte value) {
        this.value = value;
    }

    public Byte getValue() {
        return value;
    }

}
