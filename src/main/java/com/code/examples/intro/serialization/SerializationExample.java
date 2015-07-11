package com.code.examples.intro.serialization;


import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;


/**
 * Created by rtheramb on 7/11/2015.
 */

public class SerializationExample {

    public <T> T read(InputStream inputStream, T t) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInStream = null;
        T returnResult = null;
        try {
            objectInStream = new ObjectInputStream(inputStream);
            returnResult = (T) objectInStream.readObject();
        }    finally {
            try {
                if (null != objectInStream) {
                    objectInStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return returnResult;
    }

    public <T> void write(OutputStream out, T t) throws IOException {
        ObjectOutputStream objectOutStream = new ObjectOutputStream(out);
        objectOutStream.writeObject(t);
        objectOutStream.close();
    }

}

