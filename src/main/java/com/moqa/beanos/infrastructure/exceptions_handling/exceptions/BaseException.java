package com.moqa.beanos.infrastructure.exceptions_handling.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BaseException extends Exception {

    final HttpStatus status;
    final int errorCode;
    final String customErrorMessage;

    public BaseException(String systemsErrorMessage, String customErrorMessage, HttpStatus status, int errorCode) {
        super(systemsErrorMessage);
        this.status = status;
        this.errorCode = errorCode;
        this.customErrorMessage = customErrorMessage;
    }

}
