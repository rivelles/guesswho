/* (C)2022 */
package com.rivelles.guesswho.application.services.session;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

import com.rivelles.guesswho.adapters.database.QuestionsRepository;
import com.rivelles.guesswho.adapters.database.SessionsRepository;
import com.rivelles.guesswho.domain.model.question.Answer;
import com.rivelles.guesswho.domain.model.question.Question;
import com.rivelles.guesswho.domain.model.question.QuestionId;
import com.rivelles.guesswho.domain.model.question.QuestionTips;
import com.rivelles.guesswho.domain.model.session.Session;
import com.rivelles.guesswho.domain.model.session.UserIdentifier;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Given an answer provided to a question's session")
class AnswerQuestionInSessionTest {

    @Mock SessionsRepository sessionsRepository;

    @Mock QuestionsRepository questionsRepository;

    AnswerQuestionInSession answerQuestionInSession;

    @BeforeEach
    void setup() {
        answerQuestionInSession =
                new AnswerQuestionInSession(sessionsRepository, questionsRepository);
    }

    private static final QuestionTips MICHAEL_JACKSON_TIPS =
            new QuestionTips(
                    Map.of(
                            "Was an american singer", 1,
                            "Died in 2009", 2,
                            "Is known as the King of Pop music", 3,
                            "Had a band with 4 siblings", 4,
                            "Invented the famous Moonwalk dance move", 5));
    private static final Answer MICHAEL_JACKSON_ANSWER = new Answer("Michael Jackson");

    @Test
    @DisplayName("When answer is correct, should mark session as answered")
    void whenAnswerIsCorrect_shouldMarkSessionAsAnswered() {
        var userIdentifier = new UserIdentifier("127.0.0.1");
        var questionId = new QuestionId();
        var session = new Session(userIdentifier, questionId);
        var question = new Question(MICHAEL_JACKSON_TIPS, MICHAEL_JACKSON_ANSWER);

        when(sessionsRepository.findSessionByUserIdentifierAnsweredToday(userIdentifier))
                .thenReturn(Optional.of(session));
        when(questionsRepository.findById(questionId)).thenReturn(Optional.of(question));

        var returnedSession = answerQuestionInSession.answer(userIdentifier, "Michael Jackson");

        verify(sessionsRepository).save(any(Session.class));

        assertThat(returnedSession.isAnswered()).isTrue();
    }

    @Test
    @DisplayName("When answer is incorrect, should return false")
    void whenAnswerIsIncorrect_shouldReturnFalse() {
        var userIdentifier = new UserIdentifier("127.0.0.1");
        var questionId = new QuestionId();
        var session = new Session(userIdentifier, questionId);
        var question = new Question(MICHAEL_JACKSON_TIPS, MICHAEL_JACKSON_ANSWER);

        when(sessionsRepository.findSessionByUserIdentifierAnsweredToday(userIdentifier))
                .thenReturn(Optional.of(session));
        when(questionsRepository.findById(questionId)).thenReturn(Optional.of(question));

        var returnedSession = answerQuestionInSession.answer(userIdentifier, "Michael Scott");

        verify(sessionsRepository, never()).save(any());

        assertThat(returnedSession.isAnswered()).isFalse();
    }

    @Test
    @DisplayName("When no session is found, should throw exception")
    void whenNoSessionIsFound_shouldThrowException() {
        var userIdentifier = new UserIdentifier("127.0.0.1");

        when(sessionsRepository.findSessionByUserIdentifierAnsweredToday(userIdentifier))
                .thenReturn(Optional.empty());

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(
                        () -> answerQuestionInSession.answer(userIdentifier, "Michael Jackson"));

        verify(sessionsRepository, never()).save(any());
    }
}
