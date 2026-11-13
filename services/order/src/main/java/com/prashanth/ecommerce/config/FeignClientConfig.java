package com.prashanth.ecommerce.config;

import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import jakarta.servlet.http.HttpServletRequest;
import feign.RequestInterceptor;
import feign.RequestTemplate;

public class FeignClientConfig implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {

        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new IllegalStateException("No HTTP request context available");
        }
        HttpServletRequest request = attributes.getRequest();
        String authentication = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authentication == null || authentication.isBlank()) {
            throw new IllegalStateException("Authorization header is missing");
        }
        requestTemplate.header(HttpHeaders.AUTHORIZATION, authentication);

    }
}
