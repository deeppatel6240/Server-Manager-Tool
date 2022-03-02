package com.sterlite.smt.servicemanagerapi.servicemanagerapi.exceptions;

public class ServerAlreadyExistException extends  Exception{

    private String message;

    public ServerAlreadyExistException(String message){
        super(message);
        this.message = message;
    }

    @Override
    public String toString() {
        return "ServerAlreadyExistException{" +
                "message='" + message + '\'' +
                '}';
    }
}
