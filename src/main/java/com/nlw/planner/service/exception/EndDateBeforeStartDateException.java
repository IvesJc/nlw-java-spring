package com.nlw.planner.service.exception;

public class EndDateBeforeStartDateException extends RuntimeException{

    public EndDateBeforeStartDateException(){
        super("End date can't be validate before start date!");
    }

    public EndDateBeforeStartDateException(String message) {
        super(message);
    }
}
