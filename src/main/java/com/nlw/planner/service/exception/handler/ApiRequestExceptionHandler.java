package com.nlw.planner.service.exception.handler;

import com.nlw.planner.infra.JsonExceptionConfig;
import com.nlw.planner.service.exception.ActivityDateNotInTripDateException;
import com.nlw.planner.service.exception.EndDateBeforeStartDateException;
import com.nlw.planner.service.exception.IdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class ApiRequestExceptionHandler {

    @ExceptionHandler(EndDateBeforeStartDateException.class)
    public ResponseEntity<JsonExceptionConfig> endDateBeforeStartDateExceptionHandler(EndDateBeforeStartDateException exception){

        JsonExceptionConfig exceptionConfig = new JsonExceptionConfig(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionConfig);
    }

    @ExceptionHandler(ActivityDateNotInTripDateException.class)
    public ResponseEntity<JsonExceptionConfig> activityDateNotInTripDateExceptionHandler(ActivityDateNotInTripDateException exception){

        JsonExceptionConfig exceptionConfig = new JsonExceptionConfig(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionConfig);
    }

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<JsonExceptionConfig> linkNotFoundExceptionHandler(IdNotFoundException exception){

        JsonExceptionConfig exceptionConfig = new JsonExceptionConfig(
                HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionConfig);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<JsonExceptionConfig> requiredUUIDProvidedStringExceptionHandler(MethodArgumentTypeMismatchException exception){

        JsonExceptionConfig exceptionConfig = new JsonExceptionConfig(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionConfig);
    }


}
