package com.game.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Player not found")
public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
        //404
        super(message);
    }
}
