package com.rivelles.guesswho.domain.session.usecases;

import com.rivelles.guesswho.adapters.database.QuestionsRepository;
import com.rivelles.guesswho.adapters.database.SessionsRepository;
import com.rivelles.guesswho.domain.session.model.Session;
import com.rivelles.guesswho.domain.session.model.UserIdentifier;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;

@Service
public class CreateSession {
    private final SessionsRepository sessionsRepository;
    QuestionsRepository questionsRepository;

    public CreateSession(SessionsRepository sessionsRepository, QuestionsRepository questionsRepository) {
        this.sessionsRepository = sessionsRepository;
        this.questionsRepository = questionsRepository;
    }

    public void create(UserIdentifier userIdentifier) {
        var sessionQueryResult = sessionsRepository.findSessionByUserIdentifierAndDateIsToday(userIdentifier);
        sessionQueryResult.ifPresent(session -> {
            throw new RuntimeException();
        });

        var questionId = questionsRepository.findQuestionIdByDateOfAppearance(LocalDate.now());
        if (questionId.isEmpty()) {
            questionId = questionsRepository.findQuestionIdWithMinimumDateOfAppearance();
            questionId.ifPresentOrElse(
                    id -> questionsRepository.updateDateOfAppearance(id),
                    () -> { throw new RuntimeException(); }
            );
        }

        var session = new Session(userIdentifier, questionId.orElseThrow(RuntimeException::new));

        sessionsRepository.save(session);
    }
}
