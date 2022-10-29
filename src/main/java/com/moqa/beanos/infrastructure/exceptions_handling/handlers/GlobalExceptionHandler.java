package com.moqa.beanos.infrastructure.exceptions_handling.handlers;

import com.moqa.beanos.models.api.GenericResponse;
import com.moqa.beanos.models.api.Response;
import com.moqa.beanos.models.api.Status;
import com.moqa.beanos.infrastructure.exceptions_handling.exceptions.BadRequestException;
import com.moqa.beanos.infrastructure.exceptions_handling.exceptions.BaseException;
import com.moqa.beanos.infrastructure.exceptions_handling.exceptions.BaseRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Response<Object>> handleException(Exception ex, WebRequest request) {
        if(ex instanceof MethodArgumentNotValidException){
            StringBuilder customErrorMsg = new StringBuilder();
            customErrorMsg.append( "Bad request: Invalid Input");
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) ex;
            exception.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                customErrorMsg.append(", ");
                customErrorMsg.append(fieldName);
                customErrorMsg.append(": ");
                customErrorMsg.append(errorMessage);
            });

            HttpStatus status = HttpStatus.BAD_REQUEST;
            int errorCode = status.value();
            return handleGenericException(
                    customErrorMsg.toString(),
                    ex.getMessage(),
                    status,
                    errorCode);

        }else if (ex instanceof BadRequestException || ex instanceof HttpMessageNotReadableException || ex instanceof ConstraintViolationException) {
            String customErrorMsg = "Bad request!";
            HttpStatus status = HttpStatus.BAD_REQUEST;
            int errorCode = status.value();
            return handleGenericException(
                    customErrorMsg, 
                    ex.getMessage(), 
                    status, 
                    errorCode);
        } else if (ex instanceof BaseException) {
            BaseException businessException = (BaseException) ex;
            int errorCode = businessException.getErrorCode();
            return handleGenericException(
                    businessException.getCustomErrorMessage(), 
                    businessException.getMessage(), 
                    businessException.getStatus(), 
                    errorCode);
        } else if (ex instanceof BaseRuntimeException) {
            BaseRuntimeException runtimeBusinessException = (BaseRuntimeException) ex;
            int errorCode = runtimeBusinessException.getErrorCode();
            return handleGenericException(
                    runtimeBusinessException.getCustomErrorMessage(), 
                    runtimeBusinessException.getMessage(), 
                    runtimeBusinessException.getStatus(), 
                    errorCode);
        } else {
            String customErrorMsg = "Internal server Error!";
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            int errorCode = status.value();
            return handleGenericException(customErrorMsg, ex.getMessage(), status, errorCode);
        }
    }

    private ResponseEntity<Response<Object>> handleGenericException(String customErrorMsg, String error, HttpStatus httpStatus, int errorCode) {
        Status errorStatus = new Status(errorCode, customErrorMsg, error);
        Response<Object> genResponse = new Response<>(errorStatus, null, null);

        return new GenericResponse<>(genResponse, httpStatus).getResponse();
    }
}
