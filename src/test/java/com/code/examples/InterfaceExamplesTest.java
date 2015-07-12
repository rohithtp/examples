package com.code.examples;

import com.code.examples.patterns.create.single.Singleton;
import org.junit.Assert;

import static org.junit.Assert.assertEquals;

/**
 * Created by rtheramb on 7/12/2015.
 */
public class InterfaceExamplesTest {
    /**
     * Test of getInstance method, of class Singleton.
     */
    @org.junit.Test
    public void testOps() {
        InterfaceExamples examples = new InterfaceExamples();
        Integer[] values1 = {3, 4};
        Integer[] values2 = {4, 2};
        int result = examples.process(values1, new Addition());
        Assert.assertEquals(7, result);
        result = examples.process(values2, new Subtraction());
        Assert.assertEquals(2, result);
    }


}
