package cn.edu.cug.cs.gtl.grpc;

import cn.edu.cug.cs.gtl.protos.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class SqlCommandClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(SqlCommandClient.class);

    private int port;
    private String host;

    private SqlCommandServiceGrpc.SqlCommandServiceBlockingStub blockingStub;
    private SqlCommandServiceGrpc.SqlCommandServiceStub asyncStub;
    private ManagedChannel channel;

    /**
     * Construct client for accessing RouteGuide server at {@code host:port}.
     */
    public SqlCommandClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext());
    }

    /**
     * Construct client for accessing server using the existing channel.
     */
    public SqlCommandClient(ManagedChannelBuilder<?> channelBuilder) {
        channel = channelBuilder.build();
        blockingStub = SqlCommandServiceGrpc.newBlockingStub(channel);
        asyncStub = SqlCommandServiceGrpc.newStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    /**
     * @param commandText
     * @return
     */
    public SqlResult execute(String commandText) {
        try {
            SqlResult sqlResult = blockingStub.execute(
                    SqlCommand.newBuilder()
                            .setCommandText(commandText)
                            .build());
            return sqlResult;
        } catch (StatusRuntimeException e) {
            LOGGER.warn("RPC failed: {0}", e.getStatus());
            return SqlResult.newBuilder().setStatus(false).build();
        }
    }

    public static void main(String[] args) {
        SqlCommandClient sqlClient = new SqlCommandClient("localhost", 8980);
        String s = "select h.name, h.w5 from hax_nn_view h, sax_view s where trim(h.name)=trim(s.name) and h.w5>s.w5";
        SqlResult r = sqlClient.execute(s);
        if (r.getStatus()) {
            SqlDataSet ds = r.getDataset();
            for (String str : ds.getColumnNameList()) {
                System.out.println(str);
            }
            for (SqlRecord record : ds.getRecordList()) {
                System.out.print(record);
                System.out.print(" ");
            }
            System.out.println(" ");
        } else {
            LOGGER.warn("{0} error status", r.getCommandText());
        }
    }
}
