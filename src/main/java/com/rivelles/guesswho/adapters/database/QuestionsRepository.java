package com.rivelles.guesswho.adapters.database;

import com.rivelles.guesswho.domain.question.model.Question;
import com.rivelles.guesswho.domain.question.model.QuestionId;

import java.time.LocalDate;
import java.util.Optional;

public interface QuestionsRepository {
    void save(Question question);
    Question findById(QuestionId questionId);
    Optional<QuestionId> findQuestionIdByDateOfAppearance(LocalDate dateOfAppearance);
    Optional<QuestionId> findQuestionIdWithMinimumDateOfAppearance();
    void setDateOfAppearanceToNow(QuestionId id);
}
