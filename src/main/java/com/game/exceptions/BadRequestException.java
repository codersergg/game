package com.game.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid data")
public class BadRequestException extends RuntimeException{
    public BadRequestException(String message) {
        //400
        super(message);
    }
}
