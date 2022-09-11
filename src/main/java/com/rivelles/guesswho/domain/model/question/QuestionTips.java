/* (C)2022 */
package com.rivelles.guesswho.domain.model.question;

import java.util.*;
import java.util.stream.Collectors;

record QuestionTips(LinkedHashSet<Tip> tips) {
    QuestionTips showNextTip() {
        var tipsReturned = new LinkedHashSet<Tip>(tips);
        setNextInvisibleTipToVisible(tipsReturned);

        return new QuestionTips(tipsReturned);
    }

    private static void setNextInvisibleTipToVisible(LinkedHashSet<Tip> tipsReturned) {
        var tip =
                tipsReturned.stream()
                        .filter(it -> !it.isVisible())
                        .findFirst()
                        .orElseThrow(RuntimeException::new);
        tipsReturned.remove(tip);
        tipsReturned.add(new Tip(tip.title(), tip.orderOfAppearance(), true));
    }

    List<String> showAllAvailableTips() {
        return tips.stream().filter(Tip::isVisible).map(Tip::title).collect(Collectors.toList());
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
