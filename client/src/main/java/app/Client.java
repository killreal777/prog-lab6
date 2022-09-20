package app;

import abstractions.requests.CommandRequest;
import serialization.Serializer;

import java.io.IOException;
import java.net.Socket;


public class Client {
    private final Socket clientSocket;
    private final Serializer<CommandRequest> requestSerializer;
    private final Serializer<String> stringSerializer;

    public Client() throws IOException {
        this.clientSocket = new Socket("localhost", 7770);
        this.requestSerializer = new Serializer<>();
        this.stringSerializer = new Serializer<>();
    }

    private void sendRequest(CommandRequest request) throws IOException {
        clientSocket.getOutputStream().write(requestSerializer.serializeToByteArray(request));
        System.out.println("REQUEST IS SEND");
    }

    private String getResponse() throws IOException {
        return stringSerializer.deserializeFormInputStream(clientSocket.getInputStream());
    }

    public String interact(CommandRequest request) throws IOException, ClassNotFoundException {
        sendRequest(request);
        return getResponse();
    }
}