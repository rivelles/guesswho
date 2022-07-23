package com.rivelles.guesswho.adapters.http;

import com.rivelles.guesswho.domain.question.usecases.CreateQuestion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("v1/questions")
public class QuestionsController {

    private CreateQuestion createQuestionUseCase;

    public QuestionsController(CreateQuestion createQuestionUseCase) {
        this.createQuestionUseCase = createQuestionUseCase;
    }

    @PostMapping("")
    public ResponseEntity createQuestion(CreateQuestionRequest createQuestionRequest) {
        createQuestionUseCase.create(createQuestionRequest.tipsByOrderOfAppearance(), createQuestionRequest.answer());

        return ResponseEntity.created(URI.create("")).build();
    }
}
