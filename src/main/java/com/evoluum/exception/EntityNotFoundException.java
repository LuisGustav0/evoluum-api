package com.evoluum.exception;

import lombok.Getter;

public class EntityNotFoundException extends RuntimeException {

    @Getter
    private String code;

    public EntityNotFoundException(String code, String message) {
        super(message);
        this.code = code;
    }
}
