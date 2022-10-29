package com.moqa.beanos.infrastructure.exceptions_handling.exceptions;

import org.springframework.http.HttpStatus;

public class InternalException extends BaseRuntimeException {

    public InternalException(String systemError) {
        this(systemError, "Internal Server error due to connection issue!");
    }

    public InternalException(String systemError, String errorMsg) {
        super(
                systemError,
                errorMsg,
                HttpStatus.INTERNAL_SERVER_ERROR,
                50001
        );
    }

    public InternalException(String systemError, String errorMsg, int errorCode) {
        super(
                systemError,
                errorMsg,
                HttpStatus.INTERNAL_SERVER_ERROR,
                errorCode
        );
    }

}
