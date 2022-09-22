package serialization;

import exceptions.DeserializationException;
import exceptions.MessagedRuntimeException;

import java.io.*;


public class Serializer<T extends Serializable> {
    public byte[] serializeToByteArray(T object) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public T deserializeFormByteArray(byte[] byteArray) {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return (T) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new DeserializationException();
        }
    }

    public T deserializeFormInputStream(InputStream inputStream) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            return (T) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new DeserializationException();
        }
    }
}
