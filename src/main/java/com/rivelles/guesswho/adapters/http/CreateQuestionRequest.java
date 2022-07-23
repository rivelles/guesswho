package com.rivelles.guesswho.adapters.http;

import java.util.Map;

record CreateQuestionRequest(
    Map<String, Integer> tipsByOrderOfAppearance,
    String answer
) {}
