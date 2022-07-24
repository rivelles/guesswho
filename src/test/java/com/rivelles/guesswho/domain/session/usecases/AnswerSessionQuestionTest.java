package com.rivelles.guesswho.domain.session.usecases;

import com.rivelles.guesswho.adapters.database.QuestionsRepository;
import com.rivelles.guesswho.adapters.database.SessionsRepository;
import com.rivelles.guesswho.domain.question.model.Question;
import com.rivelles.guesswho.domain.question.model.QuestionId;
import com.rivelles.guesswho.domain.session.model.Session;
import com.rivelles.guesswho.domain.session.model.UserIdentifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Given an answer provided to a question's session")
class AnswerSessionQuestionTest {

    @Mock
    SessionsRepository sessionsRepository;

    @Mock
    QuestionsRepository questionsRepository;

    @InjectMocks
    AnswerSessionQuestion answerSessionQuestion;

    static private final Map<String, Integer> MICHAEL_JACKSON_TIPS = Map.of(
            "Was an american singer", 1,
            "Died in 2009", 2,
            "Is known as the King of Pop music", 3,
            "Had a band with 4 siblings", 4,
            "Invented the famous Moonwalk dance move", 5
    );
    static private final String MICHAEL_JACKSON_ANSWER = "Michael Jackson";

    @Test
    @DisplayName("When answer is correct, should mark session as answered")
    void whenAnswerIsCorrect_shouldMarkSessionAsAnswered() {
        var userIdentifier = new UserIdentifier("127.0.0.1");
        var questionId = new QuestionId();
        var session = new Session(userIdentifier, questionId);
        var question = new Question(MICHAEL_JACKSON_TIPS, MICHAEL_JACKSON_ANSWER);

        when(sessionsRepository.findSessionByUserIdentifierAndDateIsToday(userIdentifier)).thenReturn(Optional.of(session));
        when(questionsRepository.findById(questionId)).thenReturn(Optional.of(question));

        var answer = answerSessionQuestion.answer(userIdentifier, "Michael Jackson");

        verify(sessionsRepository).save(any(Session.class));

        assertThat(answer).isTrue();
    }

    @Test
    @DisplayName("When answer is incorrect, should return false")
    void whenAnswerIsIncorrect_shouldReturnFalse() {
        var userIdentifier = new UserIdentifier("127.0.0.1");
        var questionId = new QuestionId();
        var session = new Session(userIdentifier, questionId);
        var question = new Question(MICHAEL_JACKSON_TIPS, MICHAEL_JACKSON_ANSWER);

        when(sessionsRepository.findSessionByUserIdentifierAndDateIsToday(userIdentifier)).thenReturn(Optional.of(session));
        when(questionsRepository.findById(questionId)).thenReturn(Optional.of(question));

        var answer = answerSessionQuestion.answer(userIdentifier, "Michael Scott");

        verify(sessionsRepository, never()).save(any());

        assertThat(answer).isFalse();
    }

    @Test
    @DisplayName("When no session is found, should throw exception")
    void whenNoSessionIsFound_shouldThrowException() {
        var userIdentifier = new UserIdentifier("127.0.0.1");

        when(sessionsRepository.findSessionByUserIdentifierAndDateIsToday(userIdentifier)).thenReturn(Optional.empty());

        assertThatExceptionOfType(RuntimeException.class).isThrownBy(
                () -> answerSessionQuestion.answer(userIdentifier, "Michael Jackson")
        );

        verify(sessionsRepository, never()).save(any());
    }
}