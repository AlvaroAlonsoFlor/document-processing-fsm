package com.github.alvaro.alonso.document_processing_fsm.document;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration(proxyBeanMethods = false)
public class DocumentRouter {

    @Bean
    public RouterFunction<ServerResponse> documentRoutes(DocumentHandler documentHandler) {
        return route().path(
                        "/api/v1/document",
                        baseRoute ->
                                baseRoute.nest(
                                        accept(APPLICATION_JSON),
                                        baseContentType ->
                                                baseContentType.POST(
                                                        "/submit", documentHandler::submit)))
                .build();
    }
}
