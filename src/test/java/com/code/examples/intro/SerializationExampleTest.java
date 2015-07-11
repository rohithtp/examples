package com.code.examples.intro;

import com.code.examples.intro.serialization.SerializationExample;
import org.junit.Assert;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;


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

    @org.junit.Test
    public void testSerializationNested() throws IOException, ClassNotFoundException {
        Room room = new Room();
        Room.Door[] doors = new Room.Door[1];
        doors[0] = (new Room().new Door(State.OPEN));
        room.setDoors(doors);
        SerializationExample example = new SerializationExample();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        example.write(out, room);
        String personString = new String(out.toByteArray());
        InputStream inStream = new ByteArrayInputStream(personString.getBytes());
        Room result = example.read(inStream, room);
        Assert.assertNotNull(result);
        for (Room.Door doorElem : room.getDoors()) {
            Assert.assertNotSame(doorElem.getState(), State.CLOSED);
        }
    }
}

enum State {OPEN, CLOSED};

class Room implements  Serializable {
    class Door implements  Serializable {
        private transient State state;

        public Door(State state) {
            super();
            setState(state);
        }

        public State getState() {
            return state;
        }

        public void setState(State state) {
            this.state = state;
        }
    }

    private Door[] doors;

    public Door[] getDoors() {
        return doors;
    }

    public void setDoors(Door[] doors) {
        this.doors = doors;
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
