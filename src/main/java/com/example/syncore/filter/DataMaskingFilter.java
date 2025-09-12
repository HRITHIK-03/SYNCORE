package com.example.syncore.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DataMaskingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // A simple demo filter: do not log full sensitive params to console
        HttpServletRequest req = (HttpServletRequest) request;
        // In real project we would wrap request and mask in logs. For demo, set attribute
        String uri = req.getRequestURI();
        if (uri.contains("email") || uri.contains("phone")) {
            request.setAttribute("masked", true);
        }
        chain.doFilter(request, response);
    }
}
