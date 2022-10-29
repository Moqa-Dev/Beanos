package com.moqa.beanos.infrastructure.logging.models;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;
import java.util.Collection;

@Getter
@Setter
@Log4j2
public class FunctionsLogMessage {

    private static final String NULL_VALUE = "Null";
    public static final String MESSAGE_TYPE = "Function";

    private String methodName;
    private String methodFullName;
    private Object[] arguments;
    private Object returnValue;
    private Throwable exception;
    private long executionTime;

    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();
        message
                .append("#### Function Logger ####")
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
            if(returnValue instanceof Collection){
                message
                        .append("Returning List of: ")
                        .append(System.lineSeparator())
                        .append("\t")
                        .append(((Collection)returnValue).size())
                        .append(" instance(s)")
                        .append(System.lineSeparator());
                message
                        .append("Values: ")
                        .append(System.lineSeparator());
                ((Collection<?>) returnValue).stream()
                        .forEach(o -> message
                                .append("\t")
                                .append(returnValue.toString())
                                .append(System.lineSeparator()));
            }else{
                message
                        .append("Returning: ")
                        .append(System.lineSeparator())
                        .append("\t")
                        .append(returnValue.toString())
                        .append(System.lineSeparator());
            }
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
        if(returnValue instanceof Collection){

            return Arrays.toString(((Collection<?>) returnValue).toArray());
        }else{
            return returnValue.toString();
        }
    }

    public String getExceptionType(){
        if(exception == null)
            return NULL_VALUE;
        return exception.getClass().getSimpleName();
    }
}
