package com.code.examples;

import com.code.examples.patterns.create.single.Singleton;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InterfaceExamplesTest {

    /**
     * Test of getInstance method, of class Singleton.
     */
    @Test
    public void testOps() {
        InterfaceExamples examples = new InterfaceExamples();
        Integer[] values1 = {3, 4};
        Integer[] values2 = {4, 2};
        int result = examples.process(values1, new Addition());
        assertEquals(7, result);
        result = examples.process(values2, new Subtraction());
        assertEquals(2, result);
    }
}
