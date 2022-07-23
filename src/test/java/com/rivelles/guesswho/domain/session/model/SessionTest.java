package com.rivelles.guesswho.domain.session.model;

import com.rivelles.guesswho.domain.question.model.Question;
import com.rivelles.guesswho.domain.question.model.QuestionId;
import com.rivelles.guesswho.domain.session.model.Session;
import com.rivelles.guesswho.domain.session.model.UserIdentifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class SessionTest {

    static private final Map<String, Integer> MICHAEL_JACKSON_TIPS = Map.of(
            "Was an american singer", 1,
            "Died in 2009", 2,
            "Is known as the King of Pop music", 3,
            "Had a band with 4 siblings", 4,
            "Invented the famous Moonwalk dance move", 5
    );
    static private final String MICHAEL_JACKSON_ANSWER = "Michael Jackson";
    static private final MockedStatic<Instant> instantMock = mockStatic(Instant.class);

    @Test
    @DisplayName("Given constructor called, when IP address is correct, should create successfully")
    void whenIpAddressIsCorrect_shouldCreateSession() {
        var questionId = new QuestionId();
        var userIdentifier = new UserIdentifier("127.0.0.1");
        var currentInstant = Instant.now();

        instantMock.when(Instant::now).thenReturn(currentInstant);

        var session = new Session(userIdentifier, questionId);

        assertThat(session.getUserIdentifier()).isEqualTo(userIdentifier);
        assertThat(session.getQuestionId()).isEqualTo(questionId);
        assertThat(session.getStartDateTime()).isEqualTo(currentInstant);
    }

    @Test
    @DisplayName("Given constructor called, when IP address is incorrect, should throw an Exception")
    void whenIpAddressIsIncorrect_shouldThrowException() {
        var questionId = new QuestionId();

        assertThatExceptionOfType(RuntimeException.class).isThrownBy(
                () -> new Session(new UserIdentifier("127001"), questionId)
        ).withMessageContaining("IP address format is wrong");
    }
}