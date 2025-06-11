package com.github.alvaro.alonso.document_processing_fsm.document.fsm;

import java.util.HashMap;
import java.util.Map;

public class DocumentStateMachine {
    Map<DocumentState, Map<DocumentEventType, DocumentState>> transitionsTable;

    public DocumentStateMachine() {
        transitionsTable = new HashMap<>();

        Map<DocumentEventType, DocumentState> draftsTransitions = new HashMap<>();
        draftsTransitions.put(DocumentEventType.SUBMIT, DocumentState.SUBMITTED);
        transitionsTable.put(DocumentState.DRAFT, draftsTransitions);

        Map<DocumentEventType, DocumentState> submittedTransitions = new HashMap<>();
        submittedTransitions.put(DocumentEventType.ASSIGN_REVIEWER, DocumentState.UNDER_REVIEW);
        transitionsTable.put(DocumentState.SUBMITTED, submittedTransitions);

        Map<DocumentEventType, DocumentState> reviewTransitions = new HashMap<>();
        reviewTransitions.put(DocumentEventType.APPROVE, DocumentState.APPROVED);
        reviewTransitions.put(DocumentEventType.REJECT, DocumentState.REJECTED);
        reviewTransitions.put(DocumentEventType.REQUEST_CHANGES, DocumentState.DRAFT);
        transitionsTable.put(DocumentState.UNDER_REVIEW, reviewTransitions);

        Map<DocumentEventType, DocumentState> approveTransitions = new HashMap<>();
        approveTransitions.put(DocumentEventType.ARCHIVE, DocumentState.ARCHIVED);
        transitionsTable.put(DocumentState.APPROVED, approveTransitions);

        Map<DocumentEventType, DocumentState> rejectTransitions = new HashMap<>();
        rejectTransitions.put(DocumentEventType.ARCHIVE, DocumentState.ARCHIVED);
        transitionsTable.put(DocumentState.REJECTED, rejectTransitions);
    }

    public boolean canTransition(DocumentState from, DocumentEventType to) {
        Map<DocumentEventType, DocumentState> availableTransitions = transitionsTable.get(from);
        return availableTransitions != null && availableTransitions.containsKey(to);
    }

    public DocumentState getNextState(DocumentState documentState, DocumentEventType documentEvent) {
        if (!this.canTransition(documentState, documentEvent)) {
            throw new IllegalStateException("Invalid transition. State: [" + documentState + "] " + "Event: [" + documentEvent + "]");
        }
        return transitionsTable.get(documentState).get(documentEvent);
    }
}
