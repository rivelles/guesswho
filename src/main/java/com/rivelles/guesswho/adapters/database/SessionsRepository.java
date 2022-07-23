package com.rivelles.guesswho.adapters.database;

import com.rivelles.guesswho.domain.session.model.Session;
import com.rivelles.guesswho.domain.session.model.UserIdentifier;

import java.util.Optional;

public interface SessionsRepository {
    void save(Session session);
    Optional<Session> findSessionByUserIdentifierAndDateIsToday(UserIdentifier userIdentifier);
}
