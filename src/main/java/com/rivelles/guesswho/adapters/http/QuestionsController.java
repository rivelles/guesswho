/* (C)2022 */
package com.rivelles.guesswho.adapters.http;

import com.rivelles.guesswho.adapters.database.QuestionsRepository;
import com.rivelles.guesswho.adapters.http.requests.CreateQuestionRequest;
import com.rivelles.guesswho.application.services.question.CreateQuestion;
import com.rivelles.guesswho.domain.model.question.QuestionId;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/questions")
public class QuestionsController {

    private final CreateQuestion createQuestionUseCase;
    private final QuestionsRepository questionsRepository;

    public QuestionsController(
            CreateQuestion createQuestionUseCase, QuestionsRepository questionsRepository) {
        this.createQuestionUseCase = createQuestionUseCase;
        this.questionsRepository = questionsRepository;
    }

    @PostMapping("")
    public ResponseEntity createQuestion(CreateQuestionRequest createQuestionRequest) {
        createQuestionUseCase.create(
                createQuestionRequest.tipsByOrderOfAppearance(), createQuestionRequest.answer());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{questionId}")
    public ResponseEntity getQuestion(@PathVariable UUID questionId) {
        var questionOptional = questionsRepository.findById(new QuestionId(questionId));

        return questionOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
