/* (C)2022 */
package com.rivelles.guesswho.application.services.question;

import com.rivelles.guesswho.adapters.database.QuestionsRepository;
import com.rivelles.guesswho.domain.model.question.Answer;
import com.rivelles.guesswho.domain.model.question.Question;
import com.rivelles.guesswho.domain.model.question.QuestionTips;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class CreateQuestion {

    private final QuestionsRepository questionsRepository;

    public CreateQuestion(QuestionsRepository questionsRepository) {
        this.questionsRepository = questionsRepository;
    }

    public void create(Map<String, Integer> tipsByOrderOfAppearance, String answer) {
        var questionTips = new QuestionTips(tipsByOrderOfAppearance);
        var questionAnswer = new Answer(answer);
        var question = new Question(questionTips, questionAnswer);

        questionsRepository.save(question);
    }
}
