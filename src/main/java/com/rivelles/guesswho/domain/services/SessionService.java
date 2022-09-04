/* (C)2022 */
package com.rivelles.guesswho.domain.services;

import com.rivelles.guesswho.domain.model.question.Question;
import com.rivelles.guesswho.domain.model.session.Session;

public class SessionService {
    public Session markAsAnswered(Session session, Question question, String answer) {
        var isCorrect = question.answer(answer);

        if (isCorrect) session.markAsAnswered();

        return session;
    }
}
