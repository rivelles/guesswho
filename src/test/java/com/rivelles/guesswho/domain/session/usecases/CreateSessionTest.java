package com.rivelles.guesswho.domain.session.usecases;

import com.rivelles.guesswho.adapters.database.QuestionsRepository;
import com.rivelles.guesswho.adapters.database.SessionsRepository;
import com.rivelles.guesswho.domain.question.model.QuestionId;
import com.rivelles.guesswho.domain.session.model.Session;
import com.rivelles.guesswho.domain.session.model.UserIdentifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Given a created session")
class CreateSessionTest {

    @Mock SessionsRepository sessionsRepository;
    @Mock QuestionsRepository questionsRepository;

    @InjectMocks
    CreateSession createSession;
    final QuestionId questionId = new QuestionId();
    final UserIdentifier userIdentifier = new UserIdentifier("127.0.0.1");

    @Test
    @DisplayName("When there is a question that already appeared today, should create session and save to database")
    void whenThereIsQuestionToday_shouldSaveToDatabase() {
        var sessionCaptor = ArgumentCaptor.forClass(Session.class);
        when(sessionsRepository.findSessionByUserIdentifierAndDateIsToday(userIdentifier)).thenReturn(Optional.empty());
        when(questionsRepository.findQuestionIdByDateOfAppearance(LocalDate.now())).thenReturn(Optional.of(questionId));

        createSession.create(userIdentifier);

        verify(sessionsRepository).save(sessionCaptor.capture());
        assertThat(sessionCaptor.getValue().getQuestionId()).isEqualTo(questionId);
        assertThat(sessionCaptor.getValue().getUserIdentifier()).isEqualTo(userIdentifier);

        verify(questionsRepository, never()).findQuestionIdWithMinimumDateOfAppearance();
    }

    @Test
    @DisplayName("When there is no question that appeared today, should find the image with minimum date of appearance")
    void whenThereIsNoQuestionToday_shouldFindQuestionWithMinimumDate() {
        var sessionCaptor = ArgumentCaptor.forClass(Session.class);
        when(sessionsRepository.findSessionByUserIdentifierAndDateIsToday(userIdentifier)).thenReturn(Optional.empty());
        when(questionsRepository.findQuestionIdByDateOfAppearance(LocalDate.now())).thenReturn(Optional.empty());
        when(questionsRepository.findQuestionIdWithMinimumDateOfAppearance()).thenReturn(Optional.of(questionId));

        createSession.create(userIdentifier);

        verify(sessionsRepository).save(sessionCaptor.capture());
        assertThat(sessionCaptor.getValue().getQuestionId()).isEqualTo(questionId);
        assertThat(sessionCaptor.getValue().getUserIdentifier()).isEqualTo(userIdentifier);
    }

    @Test
    @DisplayName("When there are no questions, should throw exception")
    void whenThereAreNoQuestions_shouldThrowAnException() {
        when(sessionsRepository.findSessionByUserIdentifierAndDateIsToday(userIdentifier)).thenReturn(Optional.empty());
        when(questionsRepository.findQuestionIdByDateOfAppearance(any())).thenReturn(Optional.empty());
        when(questionsRepository.findQuestionIdWithMinimumDateOfAppearance()).thenReturn(Optional.empty());

        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> createSession.create(userIdentifier));
    }

    @Test
    @DisplayName("When user already has session in current day, should throw exception")
    void whenUserHasAlreadySessionInTheDay_shouldThrowAnException() {
        var session = new Session(userIdentifier, questionId);
        when(sessionsRepository.findSessionByUserIdentifierAndDateIsToday(userIdentifier)).thenReturn(Optional.of(session));

        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> createSession.create(userIdentifier));
    }
}