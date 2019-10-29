package cn.edu.cug.cs.gtl.grpc;

import cn.edu.cug.cs.gtl.io.File;
import cn.edu.cug.cs.gtl.mybatis.Session;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.io.Reader;

public class SqlCommandServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SqlCommandServer.class);

    private final int port;
    private final Server server;
    private Session sqlSession;

    /**
     * Start serving requests.
     */
    public void start() throws IOException {
        server.start();
        LOGGER.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                SqlCommandServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    /**
     * Stop serving requests and shutdown resources.
     */
    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public SqlCommandServer() throws Exception {
        this(8980);
    }

    public SqlCommandServer(int port) throws Exception {
        this.port = port;
        sqlSession = Session.open();
        ServerBuilder serverBuilder = ServerBuilder.forPort(port);

        this.server = serverBuilder
                .addService(new SqlCommandService(this.sqlSession))
                .build();

    }

    /**
     * Main method.  This comment makes the linter happy.
     */
    public static void main(String[] args) throws Exception {
        final SqlCommandServer server = new SqlCommandServer();
        server.start();
        server.blockUntilShutdown();
    }


}
