package com.manyi.iw.soa.common.model;

public enum SeekhouseState {

    预约中(new Byte("1")), 待确认(new Byte("2")), 待看房(new Byte("3")), 待登记(new Byte("4")), 已看房(new Byte("5")), 未看房(new Byte(
            "6")), 改期中(new Byte("7")), 取消中(new Byte("8")), 已下架(new Byte("9"));

    private Byte value;

    private SeekhouseState(Byte value) {
        this.value = value;
    }

    public Byte getValue() {
        return value;
    }
}
