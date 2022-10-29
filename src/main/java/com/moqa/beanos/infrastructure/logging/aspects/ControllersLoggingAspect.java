package com.moqa.beanos.infrastructure.logging.aspects;

import com.moqa.beanos.infrastructure.logging.models.ControllersLogMessage;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Aspect
@Component
public class ControllersLoggingAspect {

    @Value("${template.properties.application-name:SampleApplication}")
    private String appName;

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllersLoggingAspect.class);

    @Pointcut("within((@org.springframework.stereotype.Controller *))")
    public void projectLogging(){}

    @Around(value = "projectLogging()")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();

        ControllersLogMessage message = new ControllersLogMessage();
        long startTime = System.currentTimeMillis();

        message.setMethodName(joinPoint.getSignature().getName());
        message.setMethodFullName(joinPoint.getSignature().toLongString());
        message.setArguments(joinPoint.getArgs());
        message.setRequest(request);

        Object returnValue;
        try{
            returnValue = joinPoint.proceed();
            message.setReturnValue((ResponseEntity) returnValue);
        }catch (Throwable e){
            message.setException(e);
            throw e;
        }finally {
            long executionTime = System.currentTimeMillis()-startTime;
            message.setExecutionTime(executionTime);

            logMessage(message);
        }
        return returnValue;
    }

    private void logMessage(ControllersLogMessage message){
        MDC.put("id", UUID.randomUUID().toString());
        MDC.put("applicationName", appName);
        MDC.put("messageType", ControllersLogMessage.MESSAGE_TYPE);
        MDC.put("methodName", message.getMethodName());
        MDC.put("methodFullName", message.getMethodFullName());
        MDC.put("arguments", message.getFormattedArguments());
        MDC.put("returnValue", message.getFormattedResult());
        MDC.put("exception", message.getFormattedException());
        MDC.put("stackTrace", message.getFormattedStackTrace());
        MDC.put("executionTime", String.valueOf(message.getExecutionTime()));

        MDC.put("request", message.getRequest().toString());
        MDC.put("methodType", message.getMethodType());
        MDC.put("exceptionType", message.getExceptionType());
        MDC.put("servletPath", message.getServletPath());
        MDC.put("headers", message.getFormattedHeaders());

        if(message.getReturnValue() != null)
            MDC.put("response", message.getReturnValue().toString());
        MDC.put("responseStatusCode", message.getStatusCode());
        MDC.put("responseStatusCodeValue", message.getStatusCodeValue());
        MDC.put("responseHeaders", message.getResponseHeaders());
        MDC.put("responseBody", message.getResponseBody());
        if(message.getException() != null)
            LOGGER.error(message.toString());
        else
            LOGGER.info(message.toString());
        MDC.remove("id");
        MDC.remove("applicationName");
        MDC.remove("messageType");
        MDC.remove("methodName");
        MDC.remove("methodFullName");
        MDC.remove("arguments");
        MDC.remove("returnValue");
        MDC.remove("exception");
        MDC.remove("stackTrace");
        MDC.remove("executionTime");

        MDC.remove("request");
        MDC.remove("methodType");
        MDC.remove("exceptionType");
        MDC.remove("servletPath");
        MDC.remove("headers");

        MDC.remove("response");
        MDC.remove("responseStatusCode");
        MDC.remove("responseStatusCodeValue");
        MDC.remove("responseHeaders");
        MDC.remove("responseBody");
    }

}
