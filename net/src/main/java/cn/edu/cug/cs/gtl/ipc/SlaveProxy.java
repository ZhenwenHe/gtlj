package cn.edu.cug.cs.gtl.ipc;

import org.apache.hadoop.ipc.ProtocolSignature;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;

public abstract class SlaveProxy implements SlaveProtocol, java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public long getProtocolVersion(String protocol, long l) throws IOException {
        if (protocol.equals(SlaveProtocol.class.getName())) {
            return versionID;
        } else {
            throw new IOException("Unknown protocol to master node: " + protocol);
        }
    }

    @Override
    public ProtocolSignature getProtocolSignature(String s, long l, int i) throws IOException {
        return new ProtocolSignature(versionID, null);
    }

    @Override
    public abstract SlaveDescriptor getSlaveDescriptor();

    @Override
    public abstract ResultDescriptor executeCommand(CommandDescriptor cd);

    @Override
    public abstract String executeCommand(String cd);

    @Override
    public abstract byte[] executeCommand(byte[] cd);

    @Override
    public abstract ResultDescriptor executeCommand(ParameterDescriptor cd);

    public static SlaveProtocol startServer(
            RPC.Builder builder,
            SlaveProtocol protocol) throws IOException {
        try {
            if (protocol == null) return null;
            SlaveDescriptor sd = protocol.getSlaveDescriptor();
            if (sd == null) return null;
            RPC.Server server = builder.setBindAddress(sd.getIPAddress())
                    .setPort(sd.getPort())
                    .setProtocol(SlaveProtocol.class)
                    .setNumHandlers(10)
                    .setInstance(protocol)
                    .build();

            server.start();
            return protocol;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return protocol;
    }
}
