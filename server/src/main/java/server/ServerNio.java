package server;

import exceptions.ConnectionException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
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
        this.selector = Selector.open();
        this.serverSocketChannel = ServerSocketChannel.open();
        configureServerSocketChannel(host, port);
    }

    private void configureServerSocketChannel(String host, int port) throws IOException {
        SocketAddress serverSocketAddress = new InetSocketAddress(host, port);
        serverSocketChannel.socket().bind(serverSocketAddress);
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    protected void handleSelector() throws IOException {
        selector.select(500);
        Set<SelectionKey> keys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = keys.iterator();
        handleSelectionKeys(iterator);
    }

    private void handleSelectionKeys(Iterator<SelectionKey> iterator) throws IOException {
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            handleKey(key);
            iterator.remove();
        }
    }

    private void handleKey(SelectionKey key) throws IOException {
        try {
            if (key.isValid()) {
                if (key.isAcceptable())
                    accept(key);
                if (key.isReadable())
                    read(key);
                if (key.isWritable())
                    write(key);
            }
        } catch (SocketException | ConnectionException e) {
            key.cancel();
        }
    }

    private void accept(SelectionKey key) throws IOException {
        SocketChannel clientSocketChannel = serverSocketChannel.accept();
        clientSocketChannel.configureBlocking(false);
        clientSocketChannel.register(key.selector(), SelectionKey.OP_READ);
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
        client.read(buffer);
        handleRequestBuffer(buffer);
        client.register(key.selector(), SelectionKey.OP_WRITE);
    }

    private void write(SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        client.write(prepareResponseBuffer());
        client.register(key.selector(), SelectionKey.OP_READ);
    }

    abstract protected void handleRequestBuffer(ByteBuffer requestBuffer);

    abstract protected ByteBuffer prepareResponseBuffer();
}
