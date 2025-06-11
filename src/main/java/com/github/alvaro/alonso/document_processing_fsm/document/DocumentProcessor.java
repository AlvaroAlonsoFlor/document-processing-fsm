package com.github.alvaro.alonso.document_processing_fsm.document;

import com.github.alvaro.alonso.document_processing_fsm.document.fsm.DocumentEvent;
import com.github.alvaro.alonso.document_processing_fsm.document.fsm.DocumentState;
import com.github.alvaro.alonso.document_processing_fsm.document.fsm.DocumentStateMachine;

public class DocumentProcessor {

    private final DocumentStateMachine stateMachine;

    public DocumentProcessor() {
        this.stateMachine = new DocumentStateMachine();
    }

    public boolean processEvent(Document document, DocumentEvent event, String performer) {
        DocumentState currentState = document.state();
        DocumentState newState = stateMachine.getNextState(currentState, event);

        if (!guardConditions(document, event, performer)) {
            return false;
        }

        executeExitActions(document);

        Document newDocument = document.withState(newState);
        // TODO: save document

        executeEntryActions(newDocument);

        return true;
    }

    private void executeEntryActions(Document document) {
        System.out.println("Executing entry actions to document: " + document.toString());
    }

    private void executeExitActions(Document document) {
        System.out.println("Executing exit actions to document: " + document.toString());
    }

    private boolean guardConditions(Document document, DocumentEvent event, String performer) {
        return true;
    }
}
