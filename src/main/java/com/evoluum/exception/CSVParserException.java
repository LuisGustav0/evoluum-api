package com.evoluum.exception;

import lombok.Getter;

public class CSVParserException extends RuntimeException {

    @Getter
    private String code;

    public CSVParserException(Throwable ex) {
        super(ex);
        this.code = "PARSER_ERROR_CSV";
    }
}
