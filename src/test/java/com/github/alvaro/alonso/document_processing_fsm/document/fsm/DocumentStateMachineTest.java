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
        var result = documentStateMachine.getNextState(DocumentState.DRAFT, DocumentEventType.SUBMIT);
        assertEquals(DocumentState.SUBMITTED, result);
    }

    @Test
    void shouldRejectInvalidTransitionEventsFromDraft() {
        List<DocumentEventType> validEvents = new ArrayList<>();
        validEvents.add(DocumentEventType.SUBMIT);

        Stream.of(DocumentEventType.values())
                .filter(state -> !validEvents.contains(state))
                .forEach((state) -> {
                    assertThrows(IllegalStateException.class, () -> documentStateMachine.getNextState(DocumentState.DRAFT, state));
                });

    }

    @Test
    void shouldAllowValidTransitionsFromSubmitted() {
        var result = documentStateMachine.getNextState(DocumentState.SUBMITTED, DocumentEventType.ASSIGN_REVIEWER);
        assertEquals(DocumentState.UNDER_REVIEW, result);
    }

    @Test
    void shouldRejectInvalidTransitionEventsFromSubmitted() {
        List<DocumentEventType> validEvents = new ArrayList<>();
        validEvents.add(DocumentEventType.ASSIGN_REVIEWER);

        Stream.of(DocumentEventType.values())
                .filter(state -> !validEvents.contains(state))
                .forEach((state) -> {
                    assertThrows(IllegalStateException.class, () -> documentStateMachine.getNextState(DocumentState.SUBMITTED, state));
                });
    }

    @Test
    void shouldAllowValidTransitionsFromAssignReview() {
        var approved = documentStateMachine.getNextState(DocumentState.UNDER_REVIEW, DocumentEventType.APPROVE);
        var reject = documentStateMachine.getNextState(DocumentState.UNDER_REVIEW, DocumentEventType.REJECT);
        var requestChanges = documentStateMachine.getNextState(DocumentState.UNDER_REVIEW, DocumentEventType.REQUEST_CHANGES);

        assertEquals(documentStateMachine.getNextState(DocumentState.UNDER_REVIEW, DocumentEventType.APPROVE), approved);
        assertEquals(documentStateMachine.getNextState(DocumentState.UNDER_REVIEW, DocumentEventType.REJECT), reject);
        assertEquals(documentStateMachine.getNextState(DocumentState.UNDER_REVIEW, DocumentEventType.REQUEST_CHANGES), requestChanges);
    }

    @Test
    void shouldRejectInvalidTransitionEventsFromUnderReview() {
        List<DocumentEventType> validEvents = new ArrayList<>();
        validEvents.add(DocumentEventType.APPROVE);
        validEvents.add(DocumentEventType.REJECT);
        validEvents.add(DocumentEventType.REQUEST_CHANGES);

        Stream.of(DocumentEventType.values())
                .filter(state -> !validEvents.contains(state))
                .forEach((state) -> {
                    assertThrows(IllegalStateException.class, () -> documentStateMachine.getNextState(DocumentState.UNDER_REVIEW, state));
                });
    }

    @Test
    void shouldAllowValidTransitionsFromApproved() {

        var result = documentStateMachine.getNextState(DocumentState.APPROVED, DocumentEventType.ARCHIVE);
        assertEquals(DocumentState.ARCHIVED, result);
    }

    @Test
    void shouldRejectInvalidTransitionEventsFromUnderApproved() {
        List<DocumentEventType> validEvents = new ArrayList<>();
        validEvents.add(DocumentEventType.ARCHIVE);

        Stream.of(DocumentEventType.values())
                .filter(state -> !validEvents.contains(state))
                .forEach((state) -> {
                    assertThrows(IllegalStateException.class, () -> documentStateMachine.getNextState(DocumentState.APPROVED, state));
                });
    }

    @Test
    void shouldAllowValidTransitionsFromRejected() {
        DocumentState result = documentStateMachine.getNextState(DocumentState.REJECTED, DocumentEventType.ARCHIVE);
        assertEquals(DocumentState.ARCHIVED, result);
    }

    @Test
    void shouldRejectInvalidTransitionEventsFromUnderReject() {
        List<DocumentEventType> validEvents = new ArrayList<>();
        validEvents.add(DocumentEventType.ARCHIVE);

        Stream.of(DocumentEventType.values())
                .filter(state -> !validEvents.contains(state))
                .forEach((state) -> {
                    assertThrows(IllegalStateException.class, () -> documentStateMachine.getNextState(DocumentState.REJECTED, state));
                });
    }

}