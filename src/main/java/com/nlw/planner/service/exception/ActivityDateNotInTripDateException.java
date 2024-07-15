package com.nlw.planner.service.exception;

public class ActivityDateNotInTripDateException extends RuntimeException{

    public ActivityDateNotInTripDateException(){
        super("Activity date must be included between trip date!");
    }

    public ActivityDateNotInTripDateException(String message) {
        super(message);
    }
}
