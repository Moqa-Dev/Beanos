package com.moqa.beanos.infrastructure.exceptions_handling.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseRuntimeException {

    public BadRequestException(String systemError) {
        this(systemError, "Internal Server error due to invalid data!");
    }

    public BadRequestException(String systemError, String errorMsg) {
        super(
                systemError,
                errorMsg,
                HttpStatus.BAD_REQUEST,
                40001
        );
    }

    public BadRequestException(String systemError, String errorMsg, int errorCode) {
        super(
                systemError,
                errorMsg,
                HttpStatus.BAD_REQUEST,
                errorCode
        );
    }
}
