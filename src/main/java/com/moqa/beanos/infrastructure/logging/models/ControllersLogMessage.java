package com.moqa.beanos.infrastructure.logging.models;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;

@Getter
@Setter
@Log4j2
public class ControllersLogMessage {

    private static final String NULL_VALUE = "Null";
    public static final String MESSAGE_TYPE = "Controller";

    private String methodName;
    private String methodFullName;
    private Object[] arguments;
    private HttpServletRequest request;
    private ResponseEntity returnValue;
    private Throwable exception;
    private long executionTime;

    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();
        message
                .append("#### Controller Logger ####")
                .append(System.lineSeparator());
        message
                .append("Method: ")
                .append(System.lineSeparator())
                .append("\t")
                .append(methodName)
                .append(System.lineSeparator());
        message
                .append("FullName: ")
                .append(System.lineSeparator())
                .append("\t")
                .append(methodFullName)
                .append(System.lineSeparator());

        if (arguments != null && arguments.length>0){
            message
                    .append("Arguments: ")
                    .append(System.lineSeparator());
            Arrays.asList(arguments).forEach(arg->{
                message
                        .append("\t")
                        .append(arg)
                        .append(System.lineSeparator());
            });
        }

        message
                .append("Execution Time: ")
                .append(System.lineSeparator())
                .append("\t")
                .append(executionTime)
                .append("ms")
                .append(System.lineSeparator());

        if(returnValue != null){
            message
                    .append("Returning: ")
                    .append(System.lineSeparator())
                    .append("\t")
                    .append(returnValue.toString())
                    .append(System.lineSeparator());
        }
        if(exception != null){
            message
                    .append("Exception Thrown: ")
                    .append(System.lineSeparator())
                    .append("\t")
                    .append(exception.getMessage())
                    .append(System.lineSeparator())
                    .append("Stack Trace: ")
                    .append(System.lineSeparator())
                    .append("\t")
                    .append(Arrays.toString(exception.getStackTrace()))
                    .append(System.lineSeparator());
        }

        return message.toString();
    }

    public String getFormattedArguments(){
        if(arguments == null)
            return NULL_VALUE;
        return Arrays.toString(arguments);
    }

    public String getFormattedStackTrace(){
        if(exception == null)
            return NULL_VALUE;
        return Arrays.toString(exception.getStackTrace());
    }
    public String getFormattedException(){
        if(exception == null)
            return NULL_VALUE;
        return exception.getMessage();
    }
    public String getFormattedResult(){
        if(returnValue == null)
            return NULL_VALUE;
        return returnValue.toString();
    }

    public String getMethodType(){
        if(request == null || request.getMethod() == null)
            return NULL_VALUE;
        return request.getMethod();
    }

    public String getExceptionType(){
        if(exception == null)
            return NULL_VALUE;
        return exception.getClass().getSimpleName();
    }

    public String getServletPath(){
        if(request == null || request.getServletPath() == null)
            return NULL_VALUE;
        return request.getServletPath();
    }

    public String getFormattedHeaders(){
        StringBuilder sb = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            sb
                    .append("(")
                    .append(headerName)
                    .append(": ")
                    .append(request.getHeader(headerName))
                    .append("), ");
        }
        return sb.toString();
    }

    public String getStatusCode(){
        if(returnValue == null || returnValue.getStatusCode() == null)
            return NULL_VALUE;
        return returnValue.getStatusCode().toString();
    }

    public String getStatusCodeValue(){
        if(returnValue == null)
            return NULL_VALUE;
        return String.valueOf(returnValue.getStatusCodeValue());
    }

    public String getResponseHeaders(){
        if(returnValue == null || returnValue.getHeaders() == null)
            return NULL_VALUE;
        return returnValue.getHeaders().toString();
    }

    public String getResponseBody(){
        if(returnValue == null || returnValue.getBody() == null)
            return NULL_VALUE;
        return returnValue.getBody().toString();
    }

}
