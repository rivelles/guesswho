package com.rivelles.guesswho.adapters.http;

import com.rivelles.guesswho.adapters.database.QuestionsRepository;
import com.rivelles.guesswho.adapters.http.requests.CreateQuestionRequest;
import com.rivelles.guesswho.domain.question.model.QuestionId;
import com.rivelles.guesswho.domain.question.usecases.CreateQuestion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("v1/questions")
public class QuestionsController {

    private final CreateQuestion createQuestionUseCase;
    private final QuestionsRepository questionsRepository;

    public QuestionsController(CreateQuestion createQuestionUseCase, QuestionsRepository questionsRepository) {
        this.createQuestionUseCase = createQuestionUseCase;
        this.questionsRepository = questionsRepository;
    }

    @PostMapping("")
    public ResponseEntity createQuestion(CreateQuestionRequest createQuestionRequest) {
        createQuestionUseCase.create(createQuestionRequest.tipsByOrderOfAppearance(), createQuestionRequest.answer());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{questionId}")
    public ResponseEntity getQuestion(UUID questionId) {
        var questionOptional = questionsRepository.findById(new QuestionId(questionId));

        return questionOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
