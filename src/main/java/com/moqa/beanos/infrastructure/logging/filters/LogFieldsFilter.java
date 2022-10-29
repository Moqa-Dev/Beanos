package com.moqa.beanos.infrastructure.logging.filters;

import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Component
@Order(1)
public class LogFieldsFilter implements Filter {


    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        MDC.put("userId", getUserId(req));
        MDC.put("requestId", getRRequestId(req));
        chain.doFilter(request, response);
        MDC.remove("userId");
        MDC.remove("requestId");
    }

    private String getUserId(HttpServletRequest request){
        return request.getRemoteUser();
    }

    private String getRRequestId(HttpServletRequest request){
        return UUID.randomUUID().toString();
    }

}
