/* (C)2022 */
package com.rivelles.guesswho.domain.model.question;

import java.util.*;
import java.util.stream.Collectors;

public class QuestionTips {
    private TreeSet<Tip> tips;

    public QuestionTips(Map<String, Integer> tipsNamesByOrderOfAppearance) {
        fromMapToQuestionTips(tipsNamesByOrderOfAppearance);
    }

    private QuestionTips(TreeSet<Tip> tips) {
        this.tips = tips;
    }

    private void fromMapToQuestionTips(Map<String, Integer> tipsNamesByOrderOfAppearance) {
        this.tips =
                tipsNamesByOrderOfAppearance.entrySet().stream()
                        .map(
                                tipNameAndOrder ->
                                        new Tip(
                                                tipNameAndOrder.getKey(),
                                                tipNameAndOrder.getValue(),
                                                false))
                        .collect(Collectors.toCollection(TreeSet::new));
    }

    QuestionTips showNextTip() {
        var tipsWithNextVisible = new TreeSet<>(tips);
        setNextInvisibleTipToVisible(tipsWithNextVisible);

        return new QuestionTips(tipsWithNextVisible);
    }

    private static void setNextInvisibleTipToVisible(TreeSet<Tip> tipsReturned) {
        var tip =
                tipsReturned.stream()
                        .filter(it -> !it.isVisible())
                        .findFirst()
                        .orElseThrow(RuntimeException::new);
        tipsReturned.remove(tip);
        tipsReturned.add(new Tip(tip.title(), tip.orderOfAppearance(), true));
    }

    List<String> showAllAvailableTips() {
        return tips.stream().filter(Tip::isVisible).map(Tip::title).toList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionTips that = (QuestionTips) o;
        return Objects.equals(tips, that.tips);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tips);
    }
}
