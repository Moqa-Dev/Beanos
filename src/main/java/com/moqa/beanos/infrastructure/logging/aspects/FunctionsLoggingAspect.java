package com.moqa.beanos.infrastructure.logging.aspects;

import com.moqa.beanos.infrastructure.logging.models.FunctionsLogMessage;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Aspect
@Component
public class FunctionsLoggingAspect {

    @Value("${template.properties.application-name:SampleApplication}")
    private String appName;

    private static final Logger LOGGER = LoggerFactory.getLogger(FunctionsLoggingAspect.class);

    @Pointcut("@annotation(com.moqa.beanos.infrastructure.logging.annotations.Loggable)")
    public void executeLogging(){}

    @Pointcut("within(com.moqa.beanos.*.*)")
    public void projectLogging(){}

    @Around(value = "executeLogging() || projectLogging()")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        FunctionsLogMessage message = new FunctionsLogMessage();
        long startTime = System.currentTimeMillis();

        message.setMethodName(joinPoint.getSignature().getName());
        message.setMethodFullName(joinPoint.getSignature().toLongString());
        message.setArguments(joinPoint.getArgs());

        Object returnValue;
        try{
            returnValue = joinPoint.proceed();
            message.setReturnValue(returnValue);
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

    private void logMessage(FunctionsLogMessage message){
        MDC.put("id", UUID.randomUUID().toString());
        MDC.put("applicationName", appName);
        MDC.put("messageType", FunctionsLogMessage.MESSAGE_TYPE);
        MDC.put("methodName", message.getMethodName());
        MDC.put("methodFullName", message.getMethodFullName());
        MDC.put("arguments", message.getFormattedArguments());
        MDC.put("returnValue", message.getFormattedResult());
        MDC.put("exception", message.getFormattedException());
        MDC.put("stackTrace", message.getFormattedStackTrace());
        MDC.put("executionTime", String.valueOf(message.getExecutionTime()));
        MDC.put("exceptionType", message.getExceptionType());
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
        MDC.remove("exceptionType");
    }

}
