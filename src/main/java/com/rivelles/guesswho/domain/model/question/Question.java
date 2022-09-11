/* (C)2022 */
package com.rivelles.guesswho.domain.model.question;

import java.util.*;
import java.util.stream.Collector;

public class Question {

    private final QuestionId questionId;
    private QuestionTips questionTips;
    private final Answer answer;

    public Question(Map<String, Integer> tipsByOrderOfAppearance, String answer) {
        this.questionId = new QuestionId();
        this.questionTips = fromMapToQuestionTips(tipsByOrderOfAppearance);
        this.answer = new Answer(answer);
    }

    public QuestionId getId() {
        return questionId;
    }

    public boolean answer(String answered) {
        var providedAnswer = new Answer(answered);
        return answer.provide(providedAnswer);
    }

    public Question flopNextTip() {
        questionTips = questionTips.showNextTip();
        return this;
    }

    public List<String> showTips() {
        return questionTips.showAllAvailableTips();
    }

    private QuestionTips fromMapToQuestionTips(Map<String, Integer> tipsByOrderOfAppearance) {
        var tips =
                tipsByOrderOfAppearance.entrySet().stream()
                        .map(
                                stringIntegerEntry ->
                                        new Tip(
                                                stringIntegerEntry.getKey(),
                                                stringIntegerEntry.getValue(),
                                                false))
                        .sorted()
                        .collect(
                                Collector.of(
                                        () -> new LinkedHashSet<Tip>(),
                                        (tipBooleanLinkedHashMap, tip) ->
                                                tipBooleanLinkedHashMap.add(tip),
                                        (tipBooleanLinkedHashMap, tip) -> {
                                            tipBooleanLinkedHashMap.addAll(tip);
                                            return tipBooleanLinkedHashMap;
                                        },
                                        tipBooleanLinkedHashMap -> tipBooleanLinkedHashMap));
        return new QuestionTips(tips);
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
