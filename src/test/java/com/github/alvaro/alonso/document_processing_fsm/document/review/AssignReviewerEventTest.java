package com.github.alvaro.alonso.document_processing_fsm.document.review;

import com.github.alvaro.alonso.document_processing_fsm.document.Document;
import com.github.alvaro.alonso.document_processing_fsm.document.DocumentEventException;
import com.github.alvaro.alonso.document_processing_fsm.document.fsm.DocumentState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AssignReviewerEventTest {

    private AssignReviewerEvent assignReviewerEvent;

    @BeforeEach
    void setUp() {
        assignReviewerEvent = new AssignReviewerEvent();
    }

    @Test
    void shouldFailWhenNoReviewerIsProvided() {
        Document document = new Document("some-id", "title", "some content", "author", DocumentState.UNDER_REVIEW, null);
        var exception = assertThrows(DocumentEventException.class, () -> assignReviewerEvent.handle(document));

        assertThat(exception.getMessage()).contains("Document reviewer is empty. [documentId=some-id] [eventType=ASSIGN_REVIEWER]");
    }

    @Test
    void shouldHandleEventWithNoIssues() {
        Document document = new Document("some-id", "title", "some content", "author", DocumentState.UNDER_REVIEW, "a-reviewer");
        assertDoesNotThrow(() -> assignReviewerEvent.handle(document));
    }

}