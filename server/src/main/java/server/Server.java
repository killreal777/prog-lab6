package server;

import abstractions.requests.CommandRequest;
import serialization.Serializer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.function.Function;


public class Server extends ServerNio {
    private final Function<CommandRequest, String> executeCommandFunction;
    private final Runnable checkTerminalRequest;
    private final Serializer<CommandRequest> commandRequestSerializer;
    private final Serializer<String> stringSerializer;
    private String response;


    public Server(Function<CommandRequest, String> executeCommandFunction, Runnable checkTerminalRequest) throws IOException {
        super("localhost", 7770);
        System.out.println("Creating server... ");
        this.executeCommandFunction = executeCommandFunction;
        this.checkTerminalRequest = checkTerminalRequest;
        this.commandRequestSerializer = new Serializer<>();
        this.stringSerializer = new Serializer<>();
        System.out.println("Server created!");
    }


    @Override
    public void run() throws IOException {
        System.out.println("Server started");
        while (true) {
            System.out.println("Server iteration started");
            checkTerminalRequest.run();
            handleSelector();
            System.out.println("Server iteration finished");
        }
    }


    @Override
    protected void handleRequestBuffer(ByteBuffer requestBuffer) {
        CommandRequest request = commandRequestSerializer.deserializeFormByteArray(requestBuffer.array());
        response = executeCommandFunction.apply(request);
    }

    @Override
    protected ByteBuffer prepareResponseBuffer() {
        return ByteBuffer.wrap(stringSerializer.serializeToByteArray(response));
    }
}
