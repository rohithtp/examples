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

import static org.junit.Assert.*;

/**
 *
 * @author rtheramb
 */
public class SingletonTest {
    
    /**
     * Test of getInstance method, of class Singleton.
     */
    @org.junit.Test
    public void testGetInstance() {
        Singleton expResult = Singleton.getInstance();
        Singleton result = Singleton.getInstance();
        assertEquals(expResult, result);
    }
    
}
