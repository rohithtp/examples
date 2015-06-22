/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.code.examples.patterns.create.single;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rtheramb
 */
public class SingletonTest {
    
    public SingletonTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getInstance method, of class Singleton.
     */
    @org.junit.Test
    public void testGetInstance() {
        System.out.println("getInstance");
        Singleton expResult = Singleton.getInstance();
        Singleton result = Singleton.getInstance();
        assertEquals(expResult, result);
    }
    
}
