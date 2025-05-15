package com.github.alvaro.alonso.document_processing_fsm.document.fsm;

public enum DocumentState {
    DRAFT,
    SUBMITTED,
    UNDER_REVIEW,
    APPROVED,
    REJECTED,
    ARCHIVED
}