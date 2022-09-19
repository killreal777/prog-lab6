package app;

import abstractions.requests.CommandRequest;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;


public class ClientConnector {
    private Socket clientSocket;

    private void connect() throws IOException {
        this.clientSocket = new Socket("localhost", 7770);
        System.out.println("CLIENT IS RUNNING");
    }

    private void sendRequest(CommandRequest request) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        objectOutputStream.writeObject(request);
        objectOutputStream.close();
        System.out.println("REQUEST IS SEND");
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
