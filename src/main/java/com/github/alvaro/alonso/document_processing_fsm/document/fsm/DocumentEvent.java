package com.github.alvaro.alonso.document_processing_fsm.document.fsm;

import com.github.alvaro.alonso.document_processing_fsm.document.Document;
import com.github.alvaro.alonso.document_processing_fsm.document.DocumentEventException;

public interface DocumentEvent {
    DocumentEventType getEventType();
    void handle(Document document) throws DocumentEventException;
}
