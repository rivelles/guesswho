package com.rivelles.guesswho.domain.session.usecases;

import com.rivelles.guesswho.adapters.database.QuestionsRepository;
import com.rivelles.guesswho.adapters.database.SessionsRepository;
import com.rivelles.guesswho.domain.session.model.Session;
import com.rivelles.guesswho.domain.session.model.UserIdentifier;

public record AnswerSessionQuestion(SessionsRepository sessionsRepository, QuestionsRepository questionsRepository) {

    public boolean answer(UserIdentifier userIdentifier, String providedAnswer) {
        var sessionOptional = sessionsRepository.findSessionByUserIdentifierAndDateIsToday(userIdentifier);
        var questionId = sessionOptional.map(Session::getQuestionId).orElseThrow(RuntimeException::new);
        var questionOptional = questionsRepository.findById(questionId);

        var isCorrect = questionOptional.map(question -> question.answer(providedAnswer)).orElseThrow(RuntimeException::new);

        if (isCorrect) {
            var session = sessionOptional.get();
            session.markAsAnswered();
            sessionsRepository.save(session);
            return true;
        }

        return false;
    }
}