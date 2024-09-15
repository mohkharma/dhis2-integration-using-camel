package com.integration.exception;


public class Dhis2APICallException extends Exception{

    public Dhis2APICallException(){
        super();
    }

    public Dhis2APICallException(String message){
        super(message);
    }

}

