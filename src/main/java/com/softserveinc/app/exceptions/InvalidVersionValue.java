package com.softserveinc.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidVersionValue extends RuntimeException{

    public InvalidVersionValue(String message){
        super(message);
    }
}
