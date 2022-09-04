/* (C)2022 */
package com.rivelles.guesswho.application.services.question;

import com.rivelles.guesswho.adapters.database.QuestionsRepository;
import com.rivelles.guesswho.domain.model.question.Question;
import java.util.Map;
import org.springframework.stereotype.Service;

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
