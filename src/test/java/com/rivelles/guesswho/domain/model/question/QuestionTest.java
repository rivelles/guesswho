/* (C)2022 */
package com.rivelles.guesswho.domain.model.question;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class QuestionTest {

    private static final QuestionTips MICHAEL_JACKSON_TIPS =
            new QuestionTips(
                    Map.of(
                            "Was an american singer", 1,
                            "Died in 2009", 2,
                            "Is known as the King of Pop music", 3,
                            "Had a band with 4 siblings", 4,
                            "Invented the famous Moonwalk dance move", 5));
    private static final Answer MICHAEL_JACKSON_ANSWER = new Answer("Michael Jackson");

    @Nested
    @DisplayName("Given a created question")
    class QuestionCreatedTest {
        @Test
        @DisplayName("Should have a valid UUID")
        void whenAnswerIsWrong_answerShouldReturnFalse() {
            var question = new Question(MICHAEL_JACKSON_TIPS, MICHAEL_JACKSON_ANSWER);

            assertThat(question.getId())
                    .asString()
                    .containsPattern(
                            "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}");
        }
    }

    @Nested
    @DisplayName("Given an answered question")
    class QuestionAnsweredTest {

        @Test
        @DisplayName("When answer is wrong, answer should return false")
        void whenAnswerIsWrong_answerShouldReturnFalse() {
            var question = new Question(MICHAEL_JACKSON_TIPS, MICHAEL_JACKSON_ANSWER);

            var result = question.answer("Bruno Mars");

            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("When answer is wrong, answer should return true")
        void whenAnswerIsCorrect_answerShouldReturnTrue() {
            var question = new Question(MICHAEL_JACKSON_TIPS, MICHAEL_JACKSON_ANSWER);

            var result = question.answer("Michael Jackson");

            assertThat(result).isTrue();
        }
    }

    @Nested
    @DisplayName("Given a next tip request")
    class NextTipRequested {
        @Test
        @DisplayName("When there are no more tips available, should throw a RuntimeException")
        void whenNoTipsAreAvailable_shouldThrowRuntimeException() {

            var question = new Question(MICHAEL_JACKSON_TIPS, MICHAEL_JACKSON_ANSWER);

            question = question.flopNextTip();
            question = question.flopNextTip();
            question = question.flopNextTip();
            question = question.flopNextTip();
            question = question.flopNextTip();

            assertThatExceptionOfType(RuntimeException.class).isThrownBy(question::flopNextTip);
        }

        @Test
        @DisplayName("When there are more tips available, should return next available tip")
        void whenTipsAreAvailable_shouldReturnNextAvailableTip() {
            var question = new Question(MICHAEL_JACKSON_TIPS, MICHAEL_JACKSON_ANSWER);

            question = question.flopNextTip();
            question = question.flopNextTip();
            question = question.flopNextTip();
            question = question.flopNextTip();
            question = question.flopNextTip();

            var expectedResult = List.of("Was an american singer", "Died in 2009", "Is known as the King of Pop music", "Had a band with 4 siblings", "Invented the famous Moonwalk dance move");
            assertThat(question.showTips()).isEqualTo(expectedResult);
        }
    }
}