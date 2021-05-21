package com.evoluum.resource.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@Builder
@JsonInclude(Include.NON_NULL)
public class ApiError {

    private OffsetDateTime timestamp;
    private String type;
    private String title;
    private String code;
    private String message;
    private String userMessage;
    private String detail;
}
