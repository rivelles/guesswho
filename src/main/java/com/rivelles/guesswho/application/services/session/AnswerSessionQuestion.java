/* (C)2022 */
package com.rivelles.guesswho.application.services.session;

import com.rivelles.guesswho.adapters.database.QuestionsRepository;
import com.rivelles.guesswho.adapters.database.SessionsRepository;
import com.rivelles.guesswho.domain.model.session.Session;
import com.rivelles.guesswho.domain.model.session.UserIdentifier;
import com.rivelles.guesswho.domain.services.SessionService;

public record AnswerSessionQuestion(
        SessionService sessionService,
        SessionsRepository sessionsRepository,
        QuestionsRepository questionsRepository) {

    public Session answer(UserIdentifier userIdentifier, String providedAnswer) {
        var sessionOptional =
                sessionsRepository.findSessionByUserIdentifierAndDateIsToday(userIdentifier);
        var questionId =
                sessionOptional.map(Session::getQuestionId).orElseThrow(RuntimeException::new);
        var questionOptional = questionsRepository.findById(questionId);

        var session =
                questionOptional
                        .map(
                                question ->
                                        sessionService.markAsAnswered(
                                                sessionOptional.get(), question, providedAnswer))
                        .orElseThrow(RuntimeException::new);

        if (session.isAnswered()) sessionsRepository.save(session);

        return session;
    }
}
