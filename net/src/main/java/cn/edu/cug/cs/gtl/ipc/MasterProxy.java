package cn.edu.cug.cs.gtl.ipc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.ProtocolSignature;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;
import java.util.List;

/**
 *
 */
public abstract class MasterProxy implements MasterProtocol, java.io.Serializable {

    private static final long serialVersionUID = 1L;

    MasterDescriptor masterDescriptor;

    /**
     * @param ip   default 127.0.0.1
     * @param port default 8888
     */
    public MasterProxy(String ip, int port) {
        this.masterDescriptor = new MasterDescriptor(ip, port);
    }

    public MasterProxy() {
        this.masterDescriptor = new MasterDescriptor("127.0.0.1", 8888);
    }

    @Override
    public boolean registerSlave(String slaveIP, int slavePort) {
        SlaveDescriptor sd = new SlaveDescriptor(slaveIP, slavePort);

        try {
            SlaveProtocol sp = RPC.waitForProxy(
                    SlaveProtocol.class,
                    SlaveProtocol.versionID,
                    sd.getAddress(),
                    new Configuration());
            sd.setSlave(sp);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.masterDescriptor.addSlave(sd);
        System.out.println("Slave " + slaveIP + ":" + slavePort + " registered");
        return true;
    }

    @Override
    public MasterDescriptor getMasterDescriptor() {
        return this.masterDescriptor;
    }

    @Override
    public List<SlaveDescriptor> getSlaveDescriptors() {
        return this.masterDescriptor.getSlaves();
    }

    @Override
    public abstract ResultDescriptor executeCommand(CommandDescriptor cd);

    @Override
    public long getProtocolVersion(String protocol, long l) throws IOException {
        if (protocol.equals(MasterProtocol.class.getName())) {
            return versionID;
        } else if (protocol.equals(ClientProtocol.class.getName())) {
            return ClientProtocol.versionID;
        } else if (protocol.equals(SlaveProtocol.class.getName())) {
            return SlaveProtocol.versionID;
        } else {
            throw new IOException("Unknown protocol to master node: " + protocol);
        }
    }

    @Override
    public ProtocolSignature getProtocolSignature(String s, long l, int i) throws IOException {
        return new ProtocolSignature(versionID, null);
    }

    public static MasterProtocol startServer(
            RPC.Builder builder,
            MasterProtocol protocol) throws IOException {
        try {
            if (protocol == null) return null;
            MasterDescriptor md = protocol.getMasterDescriptor();
            if (md == null) return null;
            RPC.Server server = builder.setBindAddress(md.getIPAddress())
                    .setPort(md.getPort())
                    .setProtocol(MasterProtocol.class)
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
