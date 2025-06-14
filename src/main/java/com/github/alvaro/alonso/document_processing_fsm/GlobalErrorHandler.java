package com.github.alvaro.alonso.document_processing_fsm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.alvaro.alonso.document_processing_fsm.document.DocumentValidationException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class GlobalErrorHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper;

    public GlobalErrorHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {

        ServerHttpResponse response = exchange.getResponse();

        if (ex instanceof DocumentValidationException) {
            var validationException = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
            validationException.setDetail(ex.getMessage());
            return writeResponse(response, HttpStatus.BAD_REQUEST, validationException);
        }

        var defaultError = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        defaultError.setType(URI.create("internal-server-error"));
        // TODO: add proper logging
        System.out.println(
                "Unhandled exception: [" + ex.getClass() + "] [" + ex.getMessage() + "]");
        ex.printStackTrace();
        return writeResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, defaultError);
    }

    private Mono<Void> writeResponse(
            ServerHttpResponse response, HttpStatus status, ProblemDetail problemDetail) {
        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_PROBLEM_JSON);

        try {
            String json = objectMapper.writeValueAsString(problemDetail);
            DataBuffer buffer =
                    response.bufferFactory().wrap(json.getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(buffer));
        } catch (JsonProcessingException e) {
            // Fallback to simple JSON
            String fallbackJson =
                    String.format(
                            "{\"type\":\"%s\",\"title\":\"%s\",\"status\":%d,\"detail\":\"%s\"}",
                            problemDetail.getType(),
                            problemDetail.getTitle(),
                            problemDetail.getStatus(),
                            problemDetail.getDetail());
            DataBuffer buffer =
                    response.bufferFactory().wrap(fallbackJson.getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(buffer));
        }
    }
}
