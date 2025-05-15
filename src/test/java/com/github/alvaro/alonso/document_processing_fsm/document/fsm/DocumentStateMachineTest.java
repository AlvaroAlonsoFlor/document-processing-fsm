package com.github.alvaro.alonso.document_processing_fsm.document.fsm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DocumentStateMachineTest {

    private DocumentStateMachine documentStateMachine;

    @BeforeEach
    void setUp() {
        documentStateMachine = new DocumentStateMachine();
    }

    @Test
    void shouldAllowValidStatesFromDraft() {
        assertTrue(documentStateMachine.canTransition(DocumentState.DRAFT, DocumentEvent.SUBMIT));
    }

    @Test
    void shouldRejectInvalidTransitionEventsFromDraft() {
        List<DocumentEvent> validEvents = new ArrayList<>();
        validEvents.add(DocumentEvent.SUBMIT);

        Stream.of(DocumentEvent.values())
                .filter(state -> !validEvents.contains(state))
                .forEach((state) -> assertFalse(documentStateMachine.canTransition(DocumentState.DRAFT, state)));

    }

    @Test
    void shouldAllowValidTransitionsFromSubmitted() {
        assertTrue(documentStateMachine.canTransition(DocumentState.SUBMITTED, DocumentEvent.ASSIGN_REVIEWER));
    }

    @Test
    void shouldRejectInvalidTransitionEventsFromSubmitted() {
        List<DocumentEvent> validEvents = new ArrayList<>();
        validEvents.add(DocumentEvent.ASSIGN_REVIEWER);

        Stream.of(DocumentEvent.values())
                .filter(state -> !validEvents.contains(state))
                .forEach((state) -> assertFalse(documentStateMachine.canTransition(DocumentState.SUBMITTED, state)));
    }

    @Test
    void shouldAllowValidTransitionsFromAssignReview() {
        assertTrue(documentStateMachine.canTransition(DocumentState.UNDER_REVIEW, DocumentEvent.APPROVE));
        assertTrue(documentStateMachine.canTransition(DocumentState.UNDER_REVIEW, DocumentEvent.REJECT));
        assertTrue(documentStateMachine.canTransition(DocumentState.UNDER_REVIEW, DocumentEvent.REQUEST_CHANGES));
    }

    @Test
    void shouldRejectInvalidTransitionEventsFromUnderReview() {
        List<DocumentEvent> validEvents = new ArrayList<>();
        validEvents.add(DocumentEvent.APPROVE);
        validEvents.add(DocumentEvent.REJECT);
        validEvents.add(DocumentEvent.REQUEST_CHANGES);

        Stream.of(DocumentEvent.values())
                .filter(state -> !validEvents.contains(state))
                .forEach((state) -> assertFalse(documentStateMachine.canTransition(DocumentState.UNDER_REVIEW, state)));
    }

    @Test
    void shouldAllowValidTransitionsFromApproved() {
        assertTrue(documentStateMachine.canTransition(DocumentState.APPROVED, DocumentEvent.ARCHIVE));
    }

    @Test
    void shouldRejectInvalidTransitionEventsFromUnderApproved() {
        List<DocumentEvent> validEvents = new ArrayList<>();
        validEvents.add(DocumentEvent.ARCHIVE);

        Stream.of(DocumentEvent.values())
                .filter(state -> !validEvents.contains(state))
                .forEach((state) -> assertFalse(documentStateMachine.canTransition(DocumentState.APPROVED, state)));
    }

    @Test
    void shouldAllowValidTransitionsFromRejected() {
        assertTrue(documentStateMachine.canTransition(DocumentState.APPROVED, DocumentEvent.ARCHIVE));
    }

    @Test
    void shouldRejectInvalidTransitionEventsFromUnderReject() {
        List<DocumentEvent> validEvents = new ArrayList<>();
        validEvents.add(DocumentEvent.ARCHIVE);

        Stream.of(DocumentEvent.values())
                .filter(state -> !validEvents.contains(state))
                .forEach((state) -> assertFalse(documentStateMachine.canTransition(DocumentState.REJECTED, state)));
    }

}