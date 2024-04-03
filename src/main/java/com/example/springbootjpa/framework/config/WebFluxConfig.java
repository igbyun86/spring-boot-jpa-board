package com.example.springbootjpa.framework.config;

import com.example.springbootjpa.biz.reactive.handler.HelloHandlerFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class WebFluxConfig {

    @Bean
    public RouterFunction<ServerResponse> route(HelloHandlerFunction handlerFunction) {
        return RouterFunctions
                .route(RequestPredicates.GET("/hello"), handlerFunction::hello);
    }


}
