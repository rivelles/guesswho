package com.rivelles.guesswho.domain.question.model;

import java.util.Objects;
import java.util.UUID;

public class QuestionId {
    public final UUID questionId;
    public QuestionId() {
        this.questionId = UUID.randomUUID();
    }

    public QuestionId(UUID questionId) {
        this.questionId = questionId;
    }

    @Override
    public String toString() {
        return "QuestionId{" +
                "questionId=" + questionId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionId that = (QuestionId) o;
        return Objects.equals(questionId, that.questionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId);
    }
}
