/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.code.examples.patterns.create.single;

/**
 *
 * @author rtheramb
 */
public class Singleton {
    
    private static Singleton single;
    
    private Singleton() {
        
    }
    
    public static Singleton getInstance() {
        if(null == single) {
            single = new Singleton();
        }
        return single;
    }
}
