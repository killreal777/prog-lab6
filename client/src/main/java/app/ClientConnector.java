package app;

import abstractions.commands.Command;
import abstractions.requests.CommandRequest;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientConnector {
    private SocketChannel clientSocketChannel;


    private void connect() throws IOException {
        this.clientSocketChannel = SocketChannel.open();
        SocketAddress serverSocketAddress = new InetSocketAddress("localhost", 7770);
        System.out.println("CLIENT IS RUNNING");
        clientSocketChannel.connect(serverSocketAddress);
    }

    private void sendRequest(CommandRequest request) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(request);
        clientSocketChannel.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
        objectOutputStream.close();
        System.out.println("REQUEST IS SEND");
    }

    private String getResponse() throws IOException, ClassNotFoundException {
        ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
        //clientSocketChannel.read(buffer);
        clientSocketChannel.socket().getInputStream().read(buffer.array());
        System.out.println("CLIENT RECEIVED RESPONSE");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.array());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        String response = (String) objectInputStream.readObject();
        objectInputStream.close();
        return response;
    }


    public String interact(CommandRequest request) throws IOException, ClassNotFoundException {
        connect();
        sendRequest(request);
        return getResponse();
    }
}
