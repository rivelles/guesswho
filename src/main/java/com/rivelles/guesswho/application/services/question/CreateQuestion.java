package com.rivelles.guesswho.application.services.question;

import com.rivelles.guesswho.adapters.database.QuestionsRepository;
import com.rivelles.guesswho.domain.model.question.Question;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CreateQuestion {

    QuestionsRepository questionsRepository;

    public CreateQuestion(QuestionsRepository questionsRepository) {
        this.questionsRepository = questionsRepository;
    }

    public void create(Map<String, Integer> tipsByOrderOfAppearance, String answer) {
        var question = new Question(tipsByOrderOfAppearance, answer);

        questionsRepository.save(question);
    }
}
