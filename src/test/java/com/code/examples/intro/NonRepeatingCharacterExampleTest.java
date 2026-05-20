package com.code.examples.intro;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.code.examples.intro.solutions.NonRepeatingCharacter;

public class NonRepeatingCharacterExampleTest {
    @Test
    public void testFirstNonRepeating() {
        NonRepeatingCharacter nonRepeatingCharacter = new NonRepeatingCharacter("swiss");
        assertEquals('w', nonRepeatingCharacter.firstNonRepeating());
    }
}
