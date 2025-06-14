package com.github.alvaro.alonso.document_processing_fsm.document;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class DocumentHandler {

    private final Validator validator;

    private final DocumentService documentService;

    public DocumentHandler(DocumentService documentService, Validator validator) {
        this.documentService = documentService;
        this.validator = validator;
    }

    public Mono<ServerResponse> submit(ServerRequest req) {
        Mono<Document> documentMono = req.bodyToMono(Document.class);

        return documentMono
                .doOnNext(this::validate)
                .flatMap((doc) -> Mono.just(documentService.submitDraft(doc)))
                .flatMap(
                        (document) ->
                                ServerResponse.status(HttpStatus.CREATED)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(document));
    }

    private void validate(Document document) {
        var constraintViolations = validator.validate(document);

        if (!constraintViolations.isEmpty()) {
            var errorMessage =
                    constraintViolations.stream()
                            .map(ConstraintViolation::getMessage)
                            .sorted()
                            .collect(Collectors.joining(", "));
            throw new DocumentValidationException(errorMessage);
        }
    }
}
