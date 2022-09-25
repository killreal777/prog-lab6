package client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;


public abstract class ClientIo {
    private final Socket socket;
    private final InetSocketAddress inetSocketAddress;


    public ClientIo(String hostname, int port) {
        this.socket = new Socket();
        this.inetSocketAddress = new InetSocketAddress(hostname, port);
    }


    public void connect() throws IOException {
        socket.connect(inetSocketAddress);
    }


    protected void sendRequest(byte[] request) throws IOException {
        socket.getOutputStream().write(request);
    }

    protected byte[] getResponse() throws IOException {
        return socket.getInputStream().readAllBytes();
    }
}
