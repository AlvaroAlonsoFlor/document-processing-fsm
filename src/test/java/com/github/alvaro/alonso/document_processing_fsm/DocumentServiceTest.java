package com.github.alvaro.alonso.document_processing_fsm;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.alvaro.alonso.document_processing_fsm.document.Document;
import com.github.alvaro.alonso.document_processing_fsm.document.DocumentService;
import com.github.alvaro.alonso.document_processing_fsm.document.fsm.DocumentState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DocumentServiceTest {

    private DocumentService service;

    @BeforeEach
    void setUp() {
        service = new DocumentService();
    }

    @Test
    void shouldReturnSubmittedDocumentWithUpdatedStatus() {
        Document document =
                new Document(
                        null, "Title", "some content", "Some author", DocumentState.DRAFT, null);
        Document updatedDocument = service.submitDraft(document);
        assertEquals(updatedDocument, document.withState(DocumentState.SUBMITTED));
    }
}
