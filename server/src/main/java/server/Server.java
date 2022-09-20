package server;

import abstractions.requests.CommandRequest;
import serialization.Serializer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.function.Function;


public class Server extends ServerNio {
    private final Function<CommandRequest, String> executeCommandFunction;
    private final Serializer<CommandRequest> commandRequestSerializer;
    private final Serializer<String> stringSerializer;
    private String response;


    public Server(Function<CommandRequest, String> executeCommandFunction) throws IOException {
        super("localhost", 7770);
        this.executeCommandFunction = executeCommandFunction;
        this.commandRequestSerializer = new Serializer<>();
        this.stringSerializer = new Serializer<>();
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
