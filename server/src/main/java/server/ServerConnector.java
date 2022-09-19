package server;

import abstractions.requests.CommandRequest;
import requestes.simple.ArglessCommandRequest;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;


public class ServerConnector {
    private final Scanner scanner;
    private final Selector selector;
    private final ServerSocketChannel serverSocketChannel;
    private final Function<CommandRequest, String> executeCommandFunction;
    private String response;


    public ServerConnector(Function<CommandRequest, String> executeCommandFunction) throws IOException {
        this.scanner = new Scanner(System.in);
        this.executeCommandFunction = executeCommandFunction;
        this.selector = Selector.open();
        this.serverSocketChannel = ServerSocketChannel.open();
        SocketAddress serverSocketAddress = new InetSocketAddress("localhost", 7700);
        serverSocketChannel.bind(serverSocketAddress);
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, serverSocketChannel.validOps());
    }


    public void run() throws IOException, ClassNotFoundException {
        while (true) {
            checkSaveRequest();
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            processSelectionKeys(iterator);
        }
    }

    private void processSelectionKeys(Iterator<SelectionKey> iterator) throws IOException, ClassNotFoundException {
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            processSelectionKey(key);
            iterator.remove();
        }
    }

    private void processSelectionKey(SelectionKey key) throws IOException, ClassNotFoundException {
        if (key.isValid()) {
            if (key.isAcceptable())
                accept(key);
            if (key.isReadable())
                read(key);
            if (key.isWritable())
                write(key);
        }
    }


    private void accept(SelectionKey key) throws IOException {
        SocketChannel clientSocketChannel = serverSocketChannel.accept();
        System.out.println("CLIENT ACCEPTED " + clientSocketChannel.getLocalAddress());
        clientSocketChannel.configureBlocking(false);
        clientSocketChannel.register(key.selector(), SelectionKey.OP_READ);
    }

    private void read(SelectionKey key) throws IOException, ClassNotFoundException {
        SocketChannel clientSocketChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
        clientSocketChannel.read(buffer);
        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(buffer.array()));
        CommandRequest request = (CommandRequest) objectInputStream.readObject();
        response = executeCommandFunction.apply(request);
        clientSocketChannel.configureBlocking(false);
        clientSocketChannel.register(key.selector(), SelectionKey.OP_WRITE);
        objectInputStream.close();
        System.out.println("CHANNEL READ");
    }

    private void write(SelectionKey key) throws IOException {
        SocketChannel clientSocketChannel = (SocketChannel) key.channel();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(response);
        clientSocketChannel.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
        clientSocketChannel.register(key.selector(), SelectionKey.OP_ACCEPT);
        System.out.println("CHANNEL WRITTEN");
    }


    private void checkSaveRequest() throws IOException {
        if (System.in.available() > 0) {
            String input = scanner.nextLine();
            if (input.equals("save"))
                System.out.println(executeCommandFunction.apply(new ArglessCommandRequest("save")));
        }
    }
}
