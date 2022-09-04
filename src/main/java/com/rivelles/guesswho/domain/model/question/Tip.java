/* (C)2022 */
package com.rivelles.guesswho.domain.model.question;

public record Tip(String title, int orderOfAppearance) implements Comparable<Tip> {

    @Override
    public int compareTo(Tip tip) {
        return Integer.compare(this.orderOfAppearance, tip.orderOfAppearance);
    }
}
