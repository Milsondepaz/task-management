package com.milsondev.taskmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.util.Collections;

@Configuration
public class FavIconConfig {

    private final static String FAVICON_ICO = "static/favicon.ico";

    @Bean
    public SimpleUrlHandlerMapping customFavIcon() {
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setOrder(Integer.MIN_VALUE);
        mapping.setUrlMap(Collections.singletonMap(FAVICON_ICO, favIconRequest()));
        return mapping;
    }

    @Bean
    protected ResourceHttpRequestHandler favIconRequest() {
        ResourceHttpRequestHandler handler = new ResourceHttpRequestHandler();
        handler.setLocations(Collections.singletonList(new ClassPathResource("/")));
        return handler;
    }
}
