package com.sterlite.smt.servicemanagerapi.servicemanagerapi.exceptions;

public class ServerNotFoundException extends Exception{

    private String message;

    public ServerNotFoundException(String message){
        super(message);
        this.message = message;
    }

    @Override
    public String toString() {
        return "ServerNotFoundException{" +
                "message='" + message + '\'' +
                '}';
    }
}
