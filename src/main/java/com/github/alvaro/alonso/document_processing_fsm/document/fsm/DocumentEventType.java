package com.github.alvaro.alonso.document_processing_fsm.document.fsm;

public enum DocumentEventType {
    SUBMIT,
    ASSIGN_REVIEWER,
    APPROVE,
    REJECT,
    REQUEST_CHANGES,
    ARCHIVE
}
