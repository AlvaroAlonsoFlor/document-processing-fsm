package com.github.alvaro.alonso.document_processing_fsm.document.fsm;

import com.github.alvaro.alonso.document_processing_fsm.document.Document;

public interface DocumentEvent {
    DocumentEventType getEventType();
    void handle(Document document);
}
