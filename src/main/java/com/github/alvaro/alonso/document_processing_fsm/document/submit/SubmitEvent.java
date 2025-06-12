package com.github.alvaro.alonso.document_processing_fsm.document.submit;

import com.github.alvaro.alonso.document_processing_fsm.document.Document;
import com.github.alvaro.alonso.document_processing_fsm.document.fsm.DocumentEvent;
import com.github.alvaro.alonso.document_processing_fsm.document.fsm.DocumentEventType;

public class SubmitEvent implements DocumentEvent {
    @Override
    public DocumentEventType getEventType() {
        return DocumentEventType.SUBMIT;
    }

    @Override
    public void handle(Document document) {
        System.out.println("Sending out notification of submission");
    }
}
