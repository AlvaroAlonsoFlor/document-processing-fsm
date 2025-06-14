package com.github.alvaro.alonso.document_processing_fsm.document;

import com.github.alvaro.alonso.document_processing_fsm.document.fsm.DocumentState;

public record Document(
        String id,
        String title,
        String content,
        String author,
        DocumentState state,
        String reviewer) {
    public Document withState(DocumentState state) {
        return new Document(id, title, content, author, state, reviewer);
    }
}
