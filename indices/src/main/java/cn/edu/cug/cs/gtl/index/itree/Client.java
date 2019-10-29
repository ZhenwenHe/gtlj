package cn.edu.cug.cs.gtl.index.itree;

import cn.edu.cug.cs.gtl.ipc.CommandDescriptor;
import cn.edu.cug.cs.gtl.ipc.MasterProtocol;
import cn.edu.cug.cs.gtl.ipc.ParameterDescriptor;
import cn.edu.cug.cs.gtl.ipc.ResultDescriptor;
import cn.edu.cug.cs.gtl.common.Variant;
import cn.edu.cug.cs.gtl.geom.Timeline;
import org.apache.commons.cli.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Client {
    public static void main(String[] args) {
        try {
            CommandLineParser cmp = new BasicParser();
            // Create a Parser
            CommandLineParser parser = new BasicParser();
            Options options = new Options();
            options.addOption("h", "help", false, "Print this usage information");
            options.addOption("m", "master", true, "address of master node");
            options.addOption("f", "file", true, "Data file");
            // Parse the program arguments
            CommandLine commandLine = parser.parse(options, args);
            // Set the appropriate variables based on supplied options
            String masterIP = "127.0.0.1";
            int masterPort = 8888;
            String dataFile = TimelineGenerator.outputFileName;
            if (commandLine.hasOption('h')) {
                System.out.println("Help Message");
                showUsage();
                System.in.read();
                System.exit(0);
            }
            if (commandLine.hasOption('m')) {
                String[] ss = commandLine.getOptionValue('m').split(":");
                masterIP = ss[0];
                masterPort = Integer.valueOf(ss[1]);
            }
            if (commandLine.hasOption('f')) {
                dataFile = commandLine.getOptionValue('f');
            }

            Client client = new Client();
            MasterProtocol sp = RPC.waitForProxy(
                    MasterProtocol.class,
                    MasterProtocol.versionID,
                    new InetSocketAddress(masterIP, masterPort),
                    new Configuration());


            client.createDTITree(sp, dataFile);
            for (int i = 0; i < 1000; ++i) {
                Timeline t = TimelineGenerator.generate();
                Timeline r = client.executeSimilarityQuery(sp, t);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static void showUsage() {
        System.out.println("* Options");
        System.out.println("* -i,--ip the master address of salve(String) 127.0.0.1:8888");
        System.out.println("* -f,--file the data file,hdfs://127.0.0.1:9000/data/test.timeline");
        System.out.println("* -h,--help print help information");
    }

    /**
     * 设置好命令参数，远程调用Master上的executeCommand，
     * 实现DTITree的创建，
     * 命令名称唯一，为函数名称createDTITree
     *
     * @param sp
     * @param dataFile
     */
    void createDTITree(MasterProtocol sp, String dataFile) {
        CommandDescriptor md = new CommandDescriptor("createDTITree");
        md.addParameterDescriptor(new ParameterDescriptor(new Variant(dataFile)));
        ResultDescriptor rd = sp.executeCommand(md);
    }

    /**
     * 设置好命令参数，远程调用Master上的executeCommand，
     * 实现相似性查询，
     * 令名称唯一，为函数名称executeSimilarityQuery
     *
     * @param sp MasterProtocol
     * @param q  计算与q最相似的Timeline
     * @return 计算与q最相似的Timeline, 并返回
     */
    Timeline executeSimilarityQuery(MasterProtocol sp, Timeline q) {
        CommandDescriptor md = new CommandDescriptor("executeSimilarityQuery");
        md.addParameterDescriptor(new ParameterDescriptor(q));
        ResultDescriptor rd = sp.executeCommand(md);
        return (Timeline) rd.getResult();
    }

}
