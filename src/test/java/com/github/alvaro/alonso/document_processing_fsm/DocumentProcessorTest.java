package com.github.alvaro.alonso.document_processing_fsm;

import com.github.alvaro.alonso.document_processing_fsm.document.Document;
import com.github.alvaro.alonso.document_processing_fsm.document.DocumentProcessor;
import com.github.alvaro.alonso.document_processing_fsm.document.fsm.DocumentEvent;
import com.github.alvaro.alonso.document_processing_fsm.document.fsm.DocumentState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DocumentProcessorTest {

    DocumentProcessor documentProcessor;

    @BeforeEach
    void setUp() {
        documentProcessor = new DocumentProcessor();
    }

    @Test
    void shouldProcessEventSuccessfully() {
        Document document = new Document("test", "title", "some content", "Test McTest", DocumentState.DRAFT, null);
        var result = documentProcessor.processEvent(document, DocumentEvent.SUBMIT, "Test McTest");
        assertTrue(result);
    }

    @Test
    void shouldErrorWhenProvidedEventNotValid() {
        Document document = new Document("test", "title", "some content", "Test McTest", DocumentState.DRAFT, null);
        assertThrows(IllegalStateException.class, () -> documentProcessor.processEvent(document, DocumentEvent.REJECT, "Test McTest"));
    }
}
