package com.github.alvaro.alonso.document_processing_fsm.document;

import com.github.alvaro.alonso.document_processing_fsm.document.fsm.DocumentState;
import jakarta.validation.constraints.NotBlank;

public record Document(

        String id,

        @NotBlank
        String title,

        @NotBlank
        String content,

        @NotBlank
        String author,

        @NotBlank
        DocumentState state,

        @NotBlank(groups = ReviewValidation.class)
        String reviewer) {
    public Document withState(DocumentState state) {
        return new Document(id, title, content, author, state, reviewer);
    }

    public interface ReviewValidation {}
}



