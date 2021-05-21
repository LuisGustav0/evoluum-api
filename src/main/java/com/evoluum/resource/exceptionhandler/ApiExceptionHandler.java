package com.evoluum.resource.exceptionhandler;

import com.evoluum.exception.BusinessException;
import com.evoluum.exception.CSVParserException;
import com.evoluum.exception.EntityNotFoundException;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    public static String MESSAGE_HYSTRIX = "Ocorreu um erro na requisição do serviço";
    public static final String MSG_ERROR_GENERIC_USER_FINAL = "Ocorreu um erro interno inesperado no sistema. " +
            "Tente novamente e se o problema persistir, entre em contato com o administrador do sistema.";

    private ApiError.ApiErrorBuilder createProblemBuilder(String code, ApiErrorType apiErrorType, String detail) {

        return ApiError.builder()
                       .timestamp(OffsetDateTime.now())
                       .code(code)
                       .type(apiErrorType.getUri())
                       .title(apiErrorType.getTitle())
                       .detail(detail);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusiness(BusinessException ex, WebRequest request) {

        ApiErrorType apiErrorType = ApiErrorType.ERROR_BUSINESS;
        String detail = ex.getMessage();

        ApiError apiError = createProblemBuilder(ex.getCode(), apiErrorType, detail).userMessage(detail).build();

        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(HystrixRuntimeException.class)
    public ResponseEntity<?> handleHystrix(HystrixRuntimeException ex, WebRequest request) {

        ApiErrorType apiErrorType = ApiErrorType.ERROR_SYSTEM;
        String detail = ex.getMessage();

        ApiError apiError = createProblemBuilder("CODE_HYSTRIX", apiErrorType, detail).userMessage(MESSAGE_HYSTRIX)
                                                                                      .build();

        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(CSVParserException.class)
    public ResponseEntity<?> handleCSVParser(CSVParserException ex, WebRequest request) {

        ApiErrorType apiErrorType = ApiErrorType.ERROR_SYSTEM;
        String detail = MSG_ERROR_GENERIC_USER_FINAL;

        ApiError apiError = createProblemBuilder(ex.getCode(), apiErrorType, ex.getMessage()).userMessage(detail).build();

        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {

        ApiErrorType apiErrorType = ApiErrorType.RESOURCE_NOT_FOUND;
        String detail = ex.getMessage();

        ApiError apiError = createProblemBuilder(ex.getCode(), apiErrorType, detail).userMessage(detail).build();

        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
