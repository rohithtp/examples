package com.code.examples.intro;

import com.code.examples.intro.serialization.SerializationExample;
import org.junit.Assert;


import java.io.*;


/**
 * Created by rtheramb on 7/11/2015.
 */
public class SerializationExampleTest {
    /**
     * Test of getInstance method, of class Singleton.
     */
    @org.junit.Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        Person person = new Person();
        person.setName("Java");
        SerializationExample example = new SerializationExample();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        example.write(out, person);
        String personString = new String(out.toByteArray());
        InputStream inStream = new ByteArrayInputStream(personString.getBytes());
        Person resultPerson = example.read(inStream, person);
        Assert.assertEquals(resultPerson.getName(), person.getName());
    }
}

class Person implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 2656125837769884719L;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person [name=" + name + "]";
    }

}
