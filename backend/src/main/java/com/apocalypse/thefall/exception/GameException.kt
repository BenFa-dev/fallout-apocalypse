package com.apocalypse.thefall.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GameException extends RuntimeException {
    private final String messageKey;
    private final String[] args;
    private final HttpStatus status;

    public GameException(String messageKey, HttpStatus status, String... args) {
        this.messageKey = messageKey;
        this.args = args;
        this.status = status;
    }
}