/* (C)2022 */
package com.rivelles.guesswho.application.services.session;

import com.rivelles.guesswho.adapters.database.QuestionsRepository;
import com.rivelles.guesswho.adapters.database.SessionsRepository;
import com.rivelles.guesswho.domain.model.question.QuestionId;
import com.rivelles.guesswho.domain.model.session.Session;
import com.rivelles.guesswho.domain.model.session.UserIdentifier;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public record CreateSession(
        SessionsRepository sessionsRepository, QuestionsRepository questionsRepository) {

    public void create(UserIdentifier userIdentifier) {
        validateIfUserHasAnsweredSessionToday(userIdentifier);

        var maybeQuestionId = getQuestionIdOfTheDay();

        var session =
                new Session(userIdentifier, maybeQuestionId.orElseThrow(RuntimeException::new));

        sessionsRepository.save(session);
    }

    private Optional<QuestionId> getQuestionIdOfTheDay() {
        var questionId = questionsRepository.findQuestionIdByDateOfAppearance(LocalDate.now());
        if (questionId.isEmpty()) {
            questionId = questionsRepository.findQuestionIdWithMinimumDateOfAppearance();
            questionId.ifPresentOrElse(
                    questionsRepository::setDateOfAppearanceToNow,
                    () -> {
                        throw new RuntimeException();
                    });
        }
        return questionId;
    }

    private void validateIfUserHasAnsweredSessionToday(UserIdentifier userIdentifier) {
        var sessionQueryResult =
                sessionsRepository.findSessionByUserIdentifierAndDateIsToday(userIdentifier);
        sessionQueryResult.ifPresent(
                session -> {
                    throw new RuntimeException();
                });
    }
}
