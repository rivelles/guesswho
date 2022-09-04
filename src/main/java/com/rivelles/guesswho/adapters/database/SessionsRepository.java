package com.rivelles.guesswho.adapters.database;

import com.rivelles.guesswho.domain.model.session.Session;
import com.rivelles.guesswho.domain.model.session.UserIdentifier;

import java.util.Optional;

public interface SessionsRepository {
    void save(Session session);
    Optional<Session> findSessionByUserIdentifierAndDateIsToday(UserIdentifier userIdentifier);
}
