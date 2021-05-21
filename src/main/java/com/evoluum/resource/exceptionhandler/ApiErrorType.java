package com.evoluum.resource.exceptionhandler;

import lombok.Getter;

@Getter
public enum ApiErrorType {

    RESOURCE_NOT_FOUND("/resource-not-found", "Recurso não encontrado"),
    ERROR_BUSINESS("/error-business", "Violação de regra de negócio"),
    ERROR_SYSTEM("/error-system", "Erro de sistema");

    private String title;
    private String uri;

    ApiErrorType(String urn, String title) {
        this.uri = "https://localhost:8080" + urn;
        this.title = title;
    }
}
