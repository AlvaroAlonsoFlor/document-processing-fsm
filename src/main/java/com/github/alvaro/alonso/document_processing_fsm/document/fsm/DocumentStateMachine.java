package com.github.alvaro.alonso.document_processing_fsm.document.fsm;

import java.util.HashMap;
import java.util.Map;

public class DocumentStateMachine {
    Map<DocumentState, Map<DocumentEvent, DocumentState>> transitionsTable;

    public DocumentStateMachine() {
        transitionsTable = new HashMap<>();

        Map<DocumentEvent, DocumentState> draftsTransitions = new HashMap<>();
        draftsTransitions.put(DocumentEvent.SUBMIT, DocumentState.SUBMITTED);
        transitionsTable.put(DocumentState.DRAFT, draftsTransitions);

        Map<DocumentEvent, DocumentState> submittedTransitions = new HashMap<>();
        submittedTransitions.put(DocumentEvent.ASSIGN_REVIEWER, DocumentState.UNDER_REVIEW);
        transitionsTable.put(DocumentState.SUBMITTED, submittedTransitions);

        Map<DocumentEvent, DocumentState> reviewTransitions = new HashMap<>();
        reviewTransitions.put(DocumentEvent.APPROVE, DocumentState.APPROVED);
        reviewTransitions.put(DocumentEvent.REJECT, DocumentState.REJECTED);
        reviewTransitions.put(DocumentEvent.REQUEST_CHANGES, DocumentState.DRAFT);
        transitionsTable.put(DocumentState.UNDER_REVIEW, reviewTransitions);

        Map<DocumentEvent, DocumentState> approveTransitions = new HashMap<>();
        approveTransitions.put(DocumentEvent.ARCHIVE, DocumentState.ARCHIVED);
        transitionsTable.put(DocumentState.APPROVED, approveTransitions);

        Map<DocumentEvent, DocumentState> rejectTransitions = new HashMap<>();
        rejectTransitions.put(DocumentEvent.ARCHIVE, DocumentState.ARCHIVED);
        transitionsTable.put(DocumentState.REJECTED, rejectTransitions);
    }

    public boolean canTransition(DocumentState from, DocumentEvent to) {
        Map<DocumentEvent, DocumentState> availableTransitions = transitionsTable.get(from);
        return availableTransitions != null && availableTransitions.containsKey(to);
    }

    public DocumentState getNextState(DocumentState documentState, DocumentEvent documentEvent) {
        if (!this.canTransition(documentState, documentEvent)) {
            throw new IllegalStateException("Invalid transition. State: [" + documentState + "] " + "Event: [" + documentEvent + "]");
        }
        return transitionsTable.get(documentState).get(documentEvent);
    }
}
