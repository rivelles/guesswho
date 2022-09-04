/* (C)2022 */
package com.rivelles.guesswho.domain.model.question;

record Answer(String title) {
    boolean provide(Answer providedAnswer) {
        return providedAnswer.title().toLowerCase().matches(title.toLowerCase());
    }
}
