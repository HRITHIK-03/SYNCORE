package com.example.syncore.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DataMaskingFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(DataMaskingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String uri = req.getRequestURI();
        // Demo masking log (don't log sensitive payloads)
        if (uri.contains("/api/patients") && ("POST".equalsIgnoreCase(req.getMethod()))) {
            log.info("Incoming patient create request - payload masked for privacy");
        }
        chain.doFilter(request, response);
    }
}
