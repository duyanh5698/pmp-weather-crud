package com.oddle.app.weather.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RestExceptionHandler extends RuntimeException{
    public RestExceptionHandler(String message){
        super(message);
    }
}
