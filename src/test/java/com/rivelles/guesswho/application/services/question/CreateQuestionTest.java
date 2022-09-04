package com.rivelles.guesswho.application.services.question;


import com.rivelles.guesswho.adapters.database.QuestionsRepository;
import com.rivelles.guesswho.domain.model.question.Question;
import com.rivelles.guesswho.application.services.question.CreateQuestion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Given a created question")
class CreateQuestionTest {
    @Mock
    QuestionsRepository questionsRepository;
    @InjectMocks
    CreateQuestion createQuestionUseCase;

    @Test
    @DisplayName("Should save question to database")
    void shouldSaveToDatabase() {
        var tipsByOrderOfAppearance = Map.of(
                "Was an american singer", 1,
                "Died in 2009", 2,
                "Is known as the King of Pop music", 3,
                "Had a band with 4 siblings", 4,
                "Invented the famous Moonwalk dance move", 5
        );

        createQuestionUseCase.create(tipsByOrderOfAppearance, "Michael Jackson");

        verify(questionsRepository).save(any(Question.class));
    }

    @Test
    @DisplayName("Should create a question with correct fields")
    void shouldHaveQuestionWithFields() {
        var tipsByOrderOfAppearance = Map.of(
                "Was an american singer", 1,
                "Died in 2009", 2,
                "Is known as the King of Pop music", 3,
                "Had a band with 4 siblings", 4,
                "Invented the famous Moonwalk dance move", 5
        );
        var question = ArgumentCaptor.forClass(Question.class);

        createQuestionUseCase.create(tipsByOrderOfAppearance, "Michael Jackson");

        verify(questionsRepository).save(question.capture());

        assertThat(question.getValue().getId()).isNotNull();
        assertThat(question.getValue().answer("Michael Jackson")).isTrue();
        assertThat(question.getValue().answer("Wrong answer")).isFalse();

        tipsByOrderOfAppearance.forEach((__, ___) -> question.getValue().flopNextTip());
        assertThat(question.getValue().showTips()).isEqualTo(List.of(
                "Was an american singer",
                "Died in 2009",
                "Is known as the King of Pop music",
                "Had a band with 4 siblings",
                "Invented the famous Moonwalk dance move"
        ));
    }
}