package com.github.alvaro.alonso.document_processing_fsm.document;

import com.github.alvaro.alonso.document_processing_fsm.document.fsm.DocumentState;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record Document(

        String id,

        @NotBlank (message = "Document title not provided")
        String title,

        @NotBlank (message = "Document content not provided")
        String content,

        @NotBlank (message = "Document author not provided")
        String author,

        @NotNull(message = "Document state not provided")
        DocumentState state,

        @NotBlank(groups = ReviewValidation.class)
        String reviewer) {
    public Document withState(DocumentState state) {
        return new Document(id, title, content, author, state, reviewer);
    }

    public interface ReviewValidation {}
}



