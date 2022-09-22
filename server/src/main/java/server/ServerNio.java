package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;


public abstract class ServerNio {
    private final Selector selector;
    private final ServerSocketChannel serverSocketChannel;


    public ServerNio(String host, int port) throws IOException {
        //System.out.println("Creating ServerNio...");
        this.selector = Selector.open();
        this.serverSocketChannel = ServerSocketChannel.open();
        configureServerSocketChannel(host, port);
        //System.out.println("ServerNio created!");
    }


    private void configureServerSocketChannel(String host, int port) throws IOException {
        //System.out.print("Configuring server socket channel... ");
        SocketAddress serverSocketAddress = new InetSocketAddress(host, port);
        serverSocketChannel.socket().bind(serverSocketAddress);
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, serverSocketChannel.validOps());
        //System.out.println("Server socket channel configured!");
    }


    public void run() throws IOException {
        while (true) {
            handleSelector();
        }
    }


    protected void handleSelector() throws IOException {
        //System.out.println("Handling selector... ");
        selector.select(500);
        //System.out.println("Selected");
        Set<SelectionKey> keys = selector.selectedKeys();
        //System.out.println("Key set created");
        Iterator<SelectionKey> iterator = keys.iterator();
        //System.out.println("Iterator created");
        handleSelectionKeys(iterator);
        //System.out.println("Selector handled!");
    }


    private void handleSelectionKeys(Iterator<SelectionKey> iterator) throws IOException {
        //System.out.println("Handling key iterator... ");
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            handleKey(key);
            iterator.remove();
        }
        //System.out.println("Key iterator handled!");
    }

    private void handleKey(SelectionKey key) throws IOException {
        //System.out.println("Handling selection key... ");
        if (key.isValid()) {
            if (key.isAcceptable())
                accept(key);
            if (key.isReadable())
                read(key);
            if (key.isWritable())
                write(key);
        }
        //System.out.println("Selection key handled!");
    }


    private void accept(SelectionKey key) throws IOException {
        //System.out.print("Accepting... ");
        SocketChannel clientSocketChannel = serverSocketChannel.accept();
        clientSocketChannel.configureBlocking(false);
        clientSocketChannel.register(key.selector(), SelectionKey.OP_READ);
        //System.out.println("Accepted!");
    }

    private void read(SelectionKey key) throws IOException {
        //System.out.print("Reading... ");
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
        client.read(buffer);
        handleRequestBuffer(buffer);
        client.register(key.selector(), SelectionKey.OP_WRITE);
        //System.out.println("Read!");
    }

    private void write(SelectionKey key) throws IOException {
        //System.out.print("Writing... ");
        SocketChannel client = (SocketChannel) key.channel();
        client.write(prepareResponseBuffer());
        client.register(key.selector(), SelectionKey.OP_READ);
        //System.out.println("Written!");
    }


    abstract protected void handleRequestBuffer(ByteBuffer requestBuffer);

    abstract protected ByteBuffer prepareResponseBuffer();
}
