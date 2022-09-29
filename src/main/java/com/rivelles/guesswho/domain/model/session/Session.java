/* (C)2022 */
package com.rivelles.guesswho.domain.model.session;

import static java.util.Objects.nonNull;

import com.rivelles.guesswho.domain.model.question.QuestionId;
import java.time.Instant;
import java.util.Objects;

public class Session {
    private final UserIdentifier userIdentifier;
    private final QuestionId questionId;
    private final Instant startDateTime;
    private Instant dateQuestionAnswered;

    public Session(UserIdentifier userIdentifier, QuestionId questionId) {
        this.userIdentifier = userIdentifier;
        this.questionId = questionId;
        this.startDateTime = Instant.now();
    }

    public void markAsAnswered() {
        dateQuestionAnswered = Instant.now();
    }

    public boolean isAnswered() {
        return nonNull(dateQuestionAnswered);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(userIdentifier, session.userIdentifier)
                && Objects.equals(questionId, session.questionId)
                && Objects.equals(startDateTime, session.startDateTime)
                && Objects.equals(dateQuestionAnswered, session.dateQuestionAnswered);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userIdentifier, questionId, startDateTime, dateQuestionAnswered);
    }
}
