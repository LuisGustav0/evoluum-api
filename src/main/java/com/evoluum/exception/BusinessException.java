package com.evoluum.exception;

import lombok.Getter;

public class BusinessException extends RuntimeException {

    @Getter
    private String code;

    public BusinessException(String code, Throwable ex) {
        super(ex.getMessage(), ex);
        this.code = code;
    }
}
