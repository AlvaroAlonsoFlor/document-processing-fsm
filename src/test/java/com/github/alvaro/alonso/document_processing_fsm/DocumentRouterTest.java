package com.github.alvaro.alonso.document_processing_fsm;

import static org.mockito.Mockito.when;

import com.github.alvaro.alonso.document_processing_fsm.document.Document;
import com.github.alvaro.alonso.document_processing_fsm.document.DocumentHandler;
import com.github.alvaro.alonso.document_processing_fsm.document.DocumentRouter;
import com.github.alvaro.alonso.document_processing_fsm.document.DocumentService;
import com.github.alvaro.alonso.document_processing_fsm.document.fsm.DocumentState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest
@Import({DocumentRouter.class, DocumentHandler.class, GlobalErrorHandler.class})
@AutoConfigureWebTestClient
public class DocumentRouterTest {

    @Autowired private WebTestClient webTestClient;

    @MockitoBean private DocumentService service;

    static final String API_URL = "/api/v1/document";
    static final String SUBMIT_URL = API_URL + "/submit";

    @Test
    void submitDocument() {
        var document =
                new Document(
                        null, "test-doc", "lorem ipsum", "some-id", DocumentState.SUBMITTED, null);

        when(service.submitDraft(document)).thenReturn(document);

        webTestClient
                .post()
                .uri(SUBMIT_URL)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(document)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(Document.class);
    }

    @Test
    void submitDocumentFailsValidation() {

        var document = new Document(null, null, null, null, null, null);

        when(service.submitDraft(document)).thenReturn(document);

        webTestClient
                .post()
                .uri(SUBMIT_URL)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(document)
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(ProblemDetail.class)
                .isEqualTo(
                        ProblemDetail.forStatusAndDetail(
                                HttpStatus.BAD_REQUEST,
                                "Document author not provided, Document content not provided,"
                                    + " Document state not provided, Document title not provided"));
    }
}
