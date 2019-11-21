package cn.edu.cug.cs.gtl.grpc.demo;

import cn.edu.cug.cs.gtl.protos.SqlCommand;
import cn.edu.cug.cs.gtl.protos.SqlResult;
import cn.edu.cug.cs.gtl.protos.SqlServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

public class HelloClient {
    private final ManagedChannel channel;
    private final SqlServiceGrpc.SqlServiceBlockingStub blockingStub;

    /** Construct client connecting to HelloWorld server at {@code host:port}. */
    public HelloClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext()
                .build());
    }

    /** Construct client for accessing HelloWorld server using the existing channel. */
    HelloClient(ManagedChannel channel) {
        this.channel = channel;
        blockingStub = SqlServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, java.util.concurrent.TimeUnit.MINUTES);
    }

    /** execute sql to server. */
    public void executeSQL(String name) {
        //logger.info("Will try to greet " + name + " ...");
        SqlCommand request = SqlCommand.newBuilder().setCommandText(name).build();
        SqlResult response;
        try {
            response = blockingStub.execute(request);
            System.out.println(response.getCommandText());
        } catch (StatusRuntimeException e) {
            System.out.println("RPC failed");
            return;
        }
    }

    /**
     * Greet server. If provided, the first element of {@code args} is the name to use in the
     * greeting.
     */
    public static void main(String[] args) throws Exception {
        // Access a service running on the local machine on port 50051
        HelloClient client = new HelloClient("localhost", 50051);
        try {
            String sql = "select id, title, contents from gtl where contents=beam";
            client.executeSQL(sql);
        } finally {
            client.shutdown();
        }
    }
}
