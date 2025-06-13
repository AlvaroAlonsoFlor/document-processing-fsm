package com.github.alvaro.alonso.document_processing_fsm;

import com.github.alvaro.alonso.document_processing_fsm.document.Document;
import com.github.alvaro.alonso.document_processing_fsm.document.DocumentHandler;
import com.github.alvaro.alonso.document_processing_fsm.document.DocumentRouter;
import com.github.alvaro.alonso.document_processing_fsm.document.DocumentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.Mockito.when;

@WebFluxTest
@Import({DocumentRouter.class, DocumentHandler.class})
@AutoConfigureWebTestClient
public class DocumentRouterTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private DocumentService service;

    static final String API_URL = "/api/v1/document";
    static final String SUBMIT_URL = API_URL + "/submit";

    @Test
    void submitDocument() {

        // TODO: introduce validation
        var document = new Document(null, null, null, null, null, null);

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

}
