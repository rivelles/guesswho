/* (C)2022 */
package com.rivelles.guesswho.adapters.http.requests;

import java.util.Map;

public record CreateQuestionRequest(Map<String, Integer> tipsByOrderOfAppearance, String answer) {}
