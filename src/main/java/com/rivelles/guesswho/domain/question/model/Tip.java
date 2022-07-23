package com.rivelles.guesswho.domain.question.model;

public record Tip(String title, int orderOfAppearance) implements Comparable<Tip> {

    @Override
    public int compareTo(Tip tip) { return Integer.compare(this.orderOfAppearance, tip.orderOfAppearance); }
}
