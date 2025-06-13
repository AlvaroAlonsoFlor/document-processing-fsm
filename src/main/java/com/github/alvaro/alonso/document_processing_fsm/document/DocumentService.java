package com.github.alvaro.alonso.document_processing_fsm.document;

import com.github.alvaro.alonso.document_processing_fsm.document.fsm.DocumentEventType;
import com.github.alvaro.alonso.document_processing_fsm.document.fsm.DocumentState;
import com.github.alvaro.alonso.document_processing_fsm.document.fsm.DocumentStateMachine;

public class DocumentService {
    private final DocumentStateMachine stateMachine;


    public DocumentService() {
        stateMachine = new DocumentStateMachine();
    }

    public Document submitDraft(Document document) {
        // assumes controller validates body fields
        // find author exists
        DocumentState currentState = document.state();
        DocumentState newState = stateMachine.getNextState(currentState, DocumentEventType.SUBMIT);
        // save new document and then return back object for controller
        // maybe send notification
        // use audit service to audit action
        return document.withState(newState);

    }
}
