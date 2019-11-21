package cn.edu.cug.cs.gtl.grpc.demo;

import cn.edu.cug.cs.gtl.protos.*;
import cn.edu.cug.cs.gtl.protoswrapper.SqlWrapper;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import io.grpc.Server;
import java.io.IOException;

public class HelloServer {

    private Server server;

    private void start() throws IOException {
        /* The port on which the server should run */
        int port = 50051;
        server = ServerBuilder.forPort(port)
                .addService(new SqlServiceImpl())
                .build()
                .start();
        System.out.println("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                HelloServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    private void stop() {
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

    /**
     * Main launches the server from the command line.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        final HelloServer server = new HelloServer();
        server.start();
        server.blockUntilShutdown();
    }

    static class SqlServiceImpl extends SqlServiceGrpc.SqlServiceImplBase {

        /**
         * @param request
         * @param responseObserver
         */
        @Override
        public void execute(SqlCommand request, StreamObserver<SqlResult> responseObserver) {
            //super.execute(request, responseObserver);
            SqlResult.Builder builder = SqlResult.newBuilder();
            builder.setCommandText(request.getCommandText());
            //TODO
            builder.setStatus(true);
            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();
        }

        /**
         * @param request
         * @param responseObserver
         */
        @Override
        public void insert(SqlInsertStatement request, StreamObserver<SqlResult> responseObserver) {
            //super.insert(request, responseObserver);
            SqlResult.Builder builder = SqlResult.newBuilder();
            builder.setCommandText(SqlWrapper.toSqlStatement(request));
            //TODO
            builder.setStatus(true);
            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();
        }

        /**
         * @param request
         * @param responseObserver
         */
        @Override
        public void delete(SqlDeleteStatement request, StreamObserver<SqlResult> responseObserver) {
            //super.delete(request, responseObserver);
            SqlResult.Builder builder = SqlResult.newBuilder();
            builder.setCommandText(SqlWrapper.toSqlStatement(request));
            //TODO
            builder.setStatus(true);
            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();
        }

        /**
         * @param request
         * @param responseObserver
         */
        @Override
        public void query(SqlQueryStatement request, StreamObserver<SqlResult> responseObserver) {
            //super.query(request, responseObserver);
            SqlResult.Builder builder = SqlResult.newBuilder();
            builder.setCommandText(SqlWrapper.toSqlStatement(request));
            //TODO
            builder.setStatus(true);
            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();
        }

        /**
         * @param request
         * @param responseObserver
         */
        @Override
        public void update(SqlUpdateStatement request, StreamObserver<SqlResult> responseObserver) {
            //super.update(request, responseObserver);
            SqlResult.Builder builder = SqlResult.newBuilder();
            builder.setCommandText(SqlWrapper.toSqlStatement(request));
            //TODO
            builder.setStatus(true);
            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();
        }
    }
}
