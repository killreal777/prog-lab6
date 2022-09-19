package app;

import abstractions.requests.CommandRequest;

import java.io.*;
import java.net.Socket;


public class ClientConnector {
    private Socket clientSocket;

    private void connect() throws IOException {
        this.clientSocket = new Socket("localhost", 7770);
        System.out.println("CLIENT IS RUNNING");
    }

    private void sendRequest(CommandRequest request) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(request);
        clientSocket.getOutputStream().write(byteArrayOutputStream.toByteArray());
        System.out.println("REQUEST IS SEND");
        byteArrayOutputStream.close();
        objectOutputStream.close();
    }

    private String getResponse() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
        String response = (String) objectInputStream.readObject();
        System.out.println("CLIENT RECEIVED RESPONSE");
        objectInputStream.close();
        return response;
    }

    public String interact(CommandRequest request) throws IOException, ClassNotFoundException {
        connect();
        sendRequest(request);
        return getResponse();
    }
}
