/* (C)2022 */
package com.rivelles.guesswho.domain.model.question;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

record QuestionTips(LinkedHashMap<Tip, Boolean> tips) {
    QuestionTips setNextAvailableTipToVisible() {
        var tipsReturned = new LinkedHashMap<Tip, Boolean>(tips);
        tipsReturned.entrySet().stream()
                .filter(tip -> !tip.getValue())
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .setValue(true);

        return new QuestionTips(tipsReturned);
    }

    List<String> showAllAvailableTips() {
        return tips.entrySet().stream()
                .filter(Map.Entry::getValue)
                .map(tip -> tip.getKey().title())
                .collect(Collectors.toList());
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
