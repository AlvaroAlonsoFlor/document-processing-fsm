package com.github.alvaro.alonso.document_processing_fsm.document;

import com.github.alvaro.alonso.document_processing_fsm.document.fsm.DocumentEvent;
import com.github.alvaro.alonso.document_processing_fsm.document.fsm.DocumentEventType;
import com.github.alvaro.alonso.document_processing_fsm.document.fsm.DocumentState;
import com.github.alvaro.alonso.document_processing_fsm.document.fsm.DocumentStateMachine;

public class DocumentProcessor {

    private final DocumentStateMachine stateMachine;

    public DocumentProcessor() {
        this.stateMachine = new DocumentStateMachine();
    }

    public boolean processEvent(Document document, DocumentEvent event, String performer) {
        DocumentState currentState = document.state();
        DocumentState newState = stateMachine.getNextState(currentState, event.getEventType());

        if (!guardConditions(document, event.getEventType(), performer)) {
            return false;
        }

        Document newDocument = document.withState(newState);

        event.handle(newDocument);

        return true;
    }

    private boolean guardConditions(Document document, DocumentEventType event, String performer) {
        return true;
    }
}
