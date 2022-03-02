package com.sterlite.smt.servicemanagerapi.servicemanagerapi.exceptions;

public class IpAddressAlreadyExistException extends Exception{

    private String message;

    public IpAddressAlreadyExistException(String message){
        super(message);
        this.message = message;
    }

    @Override
    public String toString() {
        return "IpAddressAlreadyExistException{" +
                "message='" + message + '\'' +
                '}';
    }
}
