package com.moqa.beanos.infrastructure.logging.models;

import com.moqa.beanos.infrastructure.logging.aspects.ExceptionHandlersLoggingAspect;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Enumeration;

@Getter
@Setter
@Log4j2
@AllArgsConstructor
public class RequestsLogMessage {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlersLoggingAspect.class);

    private static final String NULL_VALUE = "Null";
    public static final String MESSAGE_TYPE = "Request";

    private HttpServletRequest request;
    private HttpServletResponse response;


    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();
        message
                .append("#### Request Logger ####")
                .append(System.lineSeparator());
        message
                .append("Request: ")
                .append(System.lineSeparator())
                .append("\t")
                .append(request.toString())
                .append(System.lineSeparator());

        message
                .append("Response: ")
                .append(System.lineSeparator())
                .append("\t")
                .append(response.toString())
                .append(System.lineSeparator());
        return message.toString();
    }

    public String getMethodType(){
        if(request == null || request.getMethod() == null)
            return NULL_VALUE;
        return request.getMethod();
    }

    public String getServletPath(){
        if(request == null || request.getServletPath() == null)
            return NULL_VALUE;
        return request.getServletPath();
    }

    public String getMethod(){
        if(request == null || request.getServletPath() == null)
            return NULL_VALUE;
        return request.getMethod();
    }

    public String getURL(){
        if(request == null || request.getServletPath() == null)
            return NULL_VALUE;
        return request.getRequestURI();
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

    public String getStatusCodeValue(){
        if(response == null)
            return NULL_VALUE;
        return String.valueOf(response.getStatus());
    }

    public String getResponseHeaders(){
        if(response == null || response.getHeaderNames() == null)
            return NULL_VALUE;
        StringBuilder sb = new StringBuilder();
        Collection<String> headerNames = response.getHeaderNames();
        headerNames.forEach(headerName -> {
            sb
                    .append("(")
                    .append(headerName)
                    .append(": ")
                    .append(response.getHeader(headerName))
                    .append("), ");
        });
        return sb.toString();
    }


}
