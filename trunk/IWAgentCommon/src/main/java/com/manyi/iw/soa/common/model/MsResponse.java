package com.manyi.iw.soa.common.model;

public class MsResponse extends Response {
    private int status = 0;

    private int num;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

}
