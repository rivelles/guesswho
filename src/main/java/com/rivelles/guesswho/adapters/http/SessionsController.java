/* (C)2022 */
package com.rivelles.guesswho.adapters.http;

import com.rivelles.guesswho.adapters.http.requests.AnswerQuestionInSessionRequest;
import com.rivelles.guesswho.adapters.http.requests.CreateSessionForUserRequest;
import com.rivelles.guesswho.adapters.http.responses.AnswerQuestionInSessionResponse;
import com.rivelles.guesswho.application.services.session.AnswerQuestionInSession;
import com.rivelles.guesswho.application.services.session.CreateSessionForUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/sessions")
public class SessionsController {

    private final CreateSessionForUser createSessionForUser;
    private final AnswerQuestionInSession answerQuestionInSession;

    public SessionsController(
            CreateSessionForUser createSessionForUser,
            AnswerQuestionInSession answerQuestionInSession) {
        this.createSessionForUser = createSessionForUser;
        this.answerQuestionInSession = answerQuestionInSession;
    }

    @PostMapping("")
    public ResponseEntity createSessionForUser(
            CreateSessionForUserRequest createSessionForUserRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{userIdentifier}:answerQuestionInSession")
    public ResponseEntity<AnswerQuestionInSessionResponse> answerQuestionInSession(
            AnswerQuestionInSessionRequest answerQuestionInSessionRequest,
            @PathVariable String userIdentifier) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
