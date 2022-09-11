/* (C)2022 */
package com.rivelles.guesswho.domain.model.question;

import java.util.*;

public class Question implements Cloneable {

    private final QuestionId questionId;
    private QuestionTips questionTips;
    private final Answer answer;

    public Question(QuestionTips questionTips, Answer answer) {
        this.questionId = new QuestionId();
        this.questionTips = questionTips;
        this.answer = answer;
    }

    private Question(QuestionId questionId, QuestionTips questionTips, Answer answer) {
        this.questionId = questionId;
        this.questionTips = questionTips;
        this.answer = answer;
    }

    public QuestionId getId() {
        return questionId;
    }

    public boolean answer(String answered) {
        var providedAnswer = new Answer(answered);
        return answer.provide(providedAnswer);
    }

    public Question flopNextTip() {
        var tips = questionTips.showNextTip();
        return new Question(questionId, tips, answer);
    }

    public List<String> showTips() {
        return questionTips.showAllAvailableTips();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(questionId, question.questionId)
                && Objects.equals(questionTips, question.questionTips)
                && Objects.equals(answer, question.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId, questionTips, answer);
    }
}
