package cn.edu.cug.cs.gtl.ipc;

import org.apache.hadoop.ipc.VersionedProtocol;

import java.util.List;

/**
 * master access protocol interface
 */
public interface MasterProtocol extends VersionedProtocol {
    static final long versionID = 1L; //版本号，默认情况下，不同版本号的RPC Client和Server之间不能相互通信

    boolean registerSlave(String slaveIP, int slavePort);

    MasterDescriptor getMasterDescriptor();

    List<SlaveDescriptor> getSlaveDescriptors();

    ResultDescriptor executeCommand(CommandDescriptor cd);
}
