package com.moqa.beanos.infrastructure.exceptions_handling.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BaseRuntimeException extends RuntimeException {

    final HttpStatus status;
    final int errorCode;
    final String customErrorMessage;

    public BaseRuntimeException(String systemsErrorMessage, String customErrorMessage, HttpStatus status, int errorCode) {
        super(systemsErrorMessage);
        this.status = status;
        this.errorCode = errorCode;
        this.customErrorMessage = customErrorMessage;
    }
}
