package com.rivelles.guesswho.domain.question.model;

record Answer(String title) {
    boolean provide(Answer providedAnswer) {
        return providedAnswer
                .title()
                .toLowerCase()
                .matches(title.toLowerCase());
    }
}