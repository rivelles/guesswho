package com.rivelles.guesswho.adapters.database;

import com.rivelles.guesswho.domain.model.question.Question;
import com.rivelles.guesswho.domain.model.question.QuestionId;

import java.time.LocalDate;
import java.util.Optional;

public interface QuestionsRepository {
    void save(Question question);
    Optional<Question> findById(QuestionId questionId);
    Optional<QuestionId> findQuestionIdByDateOfAppearance(LocalDate dateOfAppearance);
    Optional<QuestionId> findQuestionIdWithMinimumDateOfAppearance();
    void setDateOfAppearanceToNow(QuestionId id);
}
