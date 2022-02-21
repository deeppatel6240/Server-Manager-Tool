package com.sterlite.smt.servicemanagerapi.servicemanagerapi.enumeration;

/*
 * author: deepkumar.sherathiya
 * version: 1.0
 * date: 21/02/2022
 */

public enum Status {
    SERVER_UP("SERVER_UP"),
    SERVER_DOWN("SERVER_DOWN");

    private final String status;

    Status(String status){
        this.status = status;
    }

    public String getStatus(){
        return this.status;
    }
}
