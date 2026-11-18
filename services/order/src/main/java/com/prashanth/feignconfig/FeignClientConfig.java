package com.prashanth.feignconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import jakarta.servlet.http.HttpServletRequest;
import feign.RequestInterceptor;

@Configuration(proxyBeanMethods = false)
public class FeignClientConfig {
    @Bean
    public RequestInterceptor customerRequestInterceptor() {

        return requestTemplate -> {
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
            requestTemplate.headers().get(HttpHeaders.AUTHORIZATION);
        };
    }

}
