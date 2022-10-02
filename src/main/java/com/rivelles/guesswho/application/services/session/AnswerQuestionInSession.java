/* (C)2022 */
package com.rivelles.guesswho.application.services.session;

import com.rivelles.guesswho.adapters.database.QuestionsRepository;
import com.rivelles.guesswho.adapters.database.SessionsRepository;
import com.rivelles.guesswho.domain.model.session.Session;
import com.rivelles.guesswho.domain.model.session.UserIdentifier;
import com.rivelles.guesswho.domain.services.SessionService;
import org.springframework.stereotype.Service;

@Service
public class AnswerQuestionInSession {
    private final SessionService sessionService;
    private final SessionsRepository sessionsRepository;
    private final QuestionsRepository questionsRepository;

    public AnswerQuestionInSession(
            SessionsRepository sessionsRepository, QuestionsRepository questionsRepository) {
        this.sessionService = new SessionService();
        this.sessionsRepository = sessionsRepository;
        this.questionsRepository = questionsRepository;
    }

    public Session answer(UserIdentifier userIdentifier, String providedAnswer) {
        var maybeSession =
                sessionsRepository.findSessionByUserIdentifierAnsweredToday(userIdentifier);
        var questionId =
                maybeSession.map(Session::getQuestionId).orElseThrow(RuntimeException::new);
        var maybeQuestion = questionsRepository.findById(questionId);

        var session =
                maybeQuestion
                        .map(
                                question ->
                                        sessionService.markAsAnswered(
                                                maybeSession.get(), question, providedAnswer))
                        .orElseThrow(RuntimeException::new);

        if (session.isAnswered()) sessionsRepository.save(session);

        return session;
    }
}
