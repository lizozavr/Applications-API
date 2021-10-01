package com.softserveinc.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidContentRateException extends RuntimeException {

    public InvalidContentRateException(String message) {
        super(message);
    }
}
