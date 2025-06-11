package com.github.alvaro.alonso.document_processing_fsm.document.review;

import com.github.alvaro.alonso.document_processing_fsm.document.Document;
import com.github.alvaro.alonso.document_processing_fsm.document.fsm.DocumentEvent;
import com.github.alvaro.alonso.document_processing_fsm.document.fsm.DocumentEventType;

public class AssignReviewerEvent implements DocumentEvent {
    @Override
    public DocumentEventType getEventType() {
        return DocumentEventType.ASSIGN_REVIEWER;
    }

    @Override
    public void handle(Document document) {
        System.out.println("Assigning reviewer to document [" + document.id() + "] with reviewer [" + document.reviewer() + "]");
    }
}
