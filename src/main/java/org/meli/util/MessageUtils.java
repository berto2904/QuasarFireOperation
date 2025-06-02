package org.meli.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MessageUtils {

    public static String reconstructMessage(String[][] messages) {
        if (messages == null || messages.length == 0) {
            return null;
        }

        int maxLength = Arrays.stream(messages)
                .mapToInt(m -> m.length)
                .max()
                .orElse(0);

        String[] result = new String[maxLength];

        for (String[] message : messages) {
            int offset = maxLength - message.length;

            for (int i = 0; i < message.length; i++) {
                String word = message[i];
                int position = i + offset;

                if (!word.isBlank()) {
                    if (result[position] == null) {
                        result[position] = word;
                    } else if (!result[position].equals(word)) {
                        return null;
                    }
                }
            }
        }

        return String.join(" ", removeConsecutiveDuplicates(result)).trim();
    }

    private static String[] removeConsecutiveDuplicates(String[] words) {
        List<String> filtered = new ArrayList<>();
        String prev = null;

        for (String word : words) {
            if (word != null && !word.equals(prev)) {
                filtered.add(word);
                prev = word;
            }
        }

        return filtered.toArray(new String[0]);
    }
}