package com.code.examples.intro.solutions;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class NonRepeatingCharacter {
    private String str;

    public NonRepeatingCharacter(String str) {
        this.str = str;
    }

    public char firstNonRepeating() {

        Map<Character, Integer> map = new LinkedHashMap<>();

        for (char c : str.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                return entry.getKey();
            }
        }

        return '\0';
    }

    public char firstNonRepeatingOptimized() {

        Map<Character, Integer> map = new HashMap<>();
        str.chars()
                .forEach(c -> map.put((char) c, map.getOrDefault((char) c, 0) + 1));

        return map.entrySet()
                .stream()
                .filter(entry -> entry.getValue() == 1)
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse('\0');

    }
}
