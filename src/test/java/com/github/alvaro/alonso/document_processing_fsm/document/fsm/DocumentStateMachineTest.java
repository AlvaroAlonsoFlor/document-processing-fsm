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
        var result = documentStateMachine.getNextState(DocumentState.DRAFT, DocumentEvent.SUBMIT);
        assertEquals(DocumentState.SUBMITTED, result);
    }

    @Test
    void shouldRejectInvalidTransitionEventsFromDraft() {
        List<DocumentEvent> validEvents = new ArrayList<>();
        validEvents.add(DocumentEvent.SUBMIT);

        Stream.of(DocumentEvent.values())
                .filter(state -> !validEvents.contains(state))
                .forEach((state) -> {
                    assertThrows(IllegalStateException.class, () -> documentStateMachine.getNextState(DocumentState.DRAFT, state));
                });

    }

    @Test
    void shouldAllowValidTransitionsFromSubmitted() {
        var result = documentStateMachine.getNextState(DocumentState.SUBMITTED, DocumentEvent.ASSIGN_REVIEWER);
        assertEquals(DocumentState.UNDER_REVIEW, result);
    }

    @Test
    void shouldRejectInvalidTransitionEventsFromSubmitted() {
        List<DocumentEvent> validEvents = new ArrayList<>();
        validEvents.add(DocumentEvent.ASSIGN_REVIEWER);

        Stream.of(DocumentEvent.values())
                .filter(state -> !validEvents.contains(state))
                .forEach((state) -> {
                    assertThrows(IllegalStateException.class, () -> documentStateMachine.getNextState(DocumentState.SUBMITTED, state));
                });
    }

    @Test
    void shouldAllowValidTransitionsFromAssignReview() {
        var approved = documentStateMachine.getNextState(DocumentState.UNDER_REVIEW, DocumentEvent.APPROVE);
        var reject = documentStateMachine.getNextState(DocumentState.UNDER_REVIEW, DocumentEvent.REJECT);
        var requestChanges = documentStateMachine.getNextState(DocumentState.UNDER_REVIEW, DocumentEvent.REQUEST_CHANGES);

        assertEquals(documentStateMachine.getNextState(DocumentState.UNDER_REVIEW, DocumentEvent.APPROVE), approved);
        assertEquals(documentStateMachine.getNextState(DocumentState.UNDER_REVIEW, DocumentEvent.REJECT), reject);
        assertEquals(documentStateMachine.getNextState(DocumentState.UNDER_REVIEW, DocumentEvent.REQUEST_CHANGES), requestChanges);
    }

    @Test
    void shouldRejectInvalidTransitionEventsFromUnderReview() {
        List<DocumentEvent> validEvents = new ArrayList<>();
        validEvents.add(DocumentEvent.APPROVE);
        validEvents.add(DocumentEvent.REJECT);
        validEvents.add(DocumentEvent.REQUEST_CHANGES);

        Stream.of(DocumentEvent.values())
                .filter(state -> !validEvents.contains(state))
                .forEach((state) -> {
                    assertThrows(IllegalStateException.class, () -> documentStateMachine.getNextState(DocumentState.UNDER_REVIEW, state));
                });
    }

    @Test
    void shouldAllowValidTransitionsFromApproved() {

        var result = documentStateMachine.getNextState(DocumentState.APPROVED, DocumentEvent.ARCHIVE);
        assertEquals(DocumentState.ARCHIVED, result);
    }

    @Test
    void shouldRejectInvalidTransitionEventsFromUnderApproved() {
        List<DocumentEvent> validEvents = new ArrayList<>();
        validEvents.add(DocumentEvent.ARCHIVE);

        Stream.of(DocumentEvent.values())
                .filter(state -> !validEvents.contains(state))
                .forEach((state) -> {
                    assertThrows(IllegalStateException.class, () -> documentStateMachine.getNextState(DocumentState.APPROVED, state));
                });
    }

    @Test
    void shouldAllowValidTransitionsFromRejected() {
        DocumentState result = documentStateMachine.getNextState(DocumentState.REJECTED, DocumentEvent.ARCHIVE);
        assertEquals(DocumentState.ARCHIVED, result);
    }

    @Test
    void shouldRejectInvalidTransitionEventsFromUnderReject() {
        List<DocumentEvent> validEvents = new ArrayList<>();
        validEvents.add(DocumentEvent.ARCHIVE);

        Stream.of(DocumentEvent.values())
                .filter(state -> !validEvents.contains(state))
                .forEach((state) -> {
                    assertThrows(IllegalStateException.class, () -> documentStateMachine.getNextState(DocumentState.REJECTED, state));
                });
    }

}