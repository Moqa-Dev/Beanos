package com.moqa.beanos.infrastructure.logging.filters;

import com.moqa.beanos.infrastructure.logging.models.RequestsLogMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
@Order(2)
public class LogRequestsFilter implements Filter {

    @Value("${template.properties.application-name:SampleApplication}")
    private String appName;

    private static final Logger LOGGER = LoggerFactory.getLogger(LogRequestsFilter.class);

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        long startTime = System.currentTimeMillis();
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        chain.doFilter(request, response);

        long executionTime = System.currentTimeMillis()-startTime;
        RequestsLogMessage message = new RequestsLogMessage(req, res);

        MDC.put("id", UUID.randomUUID().toString());
        MDC.put("applicationName", appName);
        MDC.put("messageType", RequestsLogMessage.MESSAGE_TYPE);
        MDC.put("executionTime", String.valueOf(executionTime));
        MDC.put("request", message.getRequest().toString());
        MDC.put("methodType", message.getMethodType());
        MDC.put("servletPath", message.getServletPath());
        MDC.put("headers", message.getFormattedHeaders());
        MDC.put("response", message.getResponse().toString());
        MDC.put("responseStatusCodeValue", message.getStatusCodeValue());
        MDC.put("responseHeaders", message.getResponseHeaders());
        MDC.put("method", message.getMethod());
        MDC.put("url", message.getURL());

        LOGGER.info(message.toString());

        MDC.remove("id");
        MDC.remove("applicationName");
        MDC.remove("messageType");
        MDC.remove("executionTime");
        MDC.remove("request");
        MDC.remove("methodType");
        MDC.remove("servletPath");
        MDC.remove("headers");
        MDC.remove("response");
        MDC.remove("responseStatusCodeValue");
        MDC.remove("responseHeaders");
        MDC.remove("method");
        MDC.remove("url");
    }

}
