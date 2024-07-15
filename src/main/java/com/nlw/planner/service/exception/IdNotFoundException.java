package com.nlw.planner.service.exception;

public class IdNotFoundException extends RuntimeException{

    public IdNotFoundException(){
        super("ID not found!");
    }
    public IdNotFoundException(String message) {
        super(message);
    }
}
