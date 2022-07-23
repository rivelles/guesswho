package com.rivelles.guesswho.domain.session.model;

import com.rivelles.guesswho.domain.question.model.QuestionId;

import java.time.Clock;
import java.time.Instant;
import java.util.Objects;

public class Session {
    private final UserIdentifier userIdentifier;
    private final QuestionId questionId;
    private final Instant startDateTime;
    private Instant endDateTime;

    public Session(UserIdentifier userIdentifier, QuestionId questionId) {
        this.userIdentifier = userIdentifier;
        this.questionId = questionId;
        this.startDateTime = Instant.now();
    }

    public void markAsAnswered() {
        endDateTime = Instant.now();
    }

    public UserIdentifier getUserIdentifier() {
        return userIdentifier;
    }

    public QuestionId getQuestionId() {
        return questionId;
    }

    public Instant getStartDateTime() {
        return startDateTime;
    }
    public Instant getEndDateTime() {
        return endDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(userIdentifier, session.userIdentifier) && Objects.equals(questionId, session.questionId) && Objects.equals(startDateTime, session.startDateTime) && Objects.equals(endDateTime, session.endDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userIdentifier, questionId, startDateTime, endDateTime);
    }
}