package cn.edu.cug.cs.gtl.mybatis.rpc;

import cn.edu.cug.cs.gtl.io.File;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;

public class SqlCommandServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SqlCommandServer.class);

    private  final int port;
    private  final Server server;
    private  SqlSessionFactory factory;
    private  SqlSession sqlSession ;

    /** Start serving requests. */
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

    /** Stop serving requests and shutdown resources. */
    public void stop() {
        if (server != null) {
            server.shutdown();
        }
        this.sqlSession.close();
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public SqlCommandServer(){
        this(8980);
    }

    public SqlCommandServer(int port) {
        this.port = port;
        String resource = "mybatis-config.xml";
        try {
            Reader reader = Resources.getResourceAsReader(resource);
            factory = new SqlSessionFactoryBuilder().build(reader);
            reader.close();
            //factory = new SqlSessionFactoryBuilder().build(new FileInputStream(new File(resource)));
            sqlSession = factory.openSession();

        }
        catch (IOException e){
            e.printStackTrace();
        }
        ServerBuilder serverBuilder= ServerBuilder.forPort(port);

        this.server=serverBuilder
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
