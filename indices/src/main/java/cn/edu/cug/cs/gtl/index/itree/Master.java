package cn.edu.cug.cs.gtl.index.itree;

import cn.edu.cug.cs.gtl.geom.ComplexInterval;
import cn.edu.cug.cs.gtl.geom.Envelope;
import cn.edu.cug.cs.gtl.geom.Timeline;
import cn.edu.cug.cs.gtl.geom.Vector2D;
import cn.edu.cug.cs.gtl.ipc.*;
import cn.edu.cug.cs.gtl.common.Pair;
import cn.edu.cug.cs.gtl.common.Variant;
import cn.edu.cug.cs.gtl.jts.geom.Geom2DSuits;
import cn.edu.cug.cs.gtl.index.shape.TriangleShape;
import cn.edu.cug.cs.gtl.io.Serializable;
import org.apache.commons.cli.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Master extends MasterProxy implements TTree, java.io.Serializable {

    private static final long serialVersionUID = 1L;

    TriangleShape rootTriangle;
    String dataFile;
    List<Pair<TriangleShape, SlaveDescriptor>> slavePairs;
    transient QueryShapeGenerator queryShapeGenerator;

    public static void main(String[] args) {
        try {
            //1.default IP and port of the master
            String ipAddr = "127.0.0.1";
            int port = 8888;
            String dataFile = TimelineGenerator.outputFileName;
            //2.parse the arguments
            CommandLineParser cmp = new BasicParser();
            // Create a Parser
            CommandLineParser parser = new BasicParser();
            Options options = new Options();
            options.addOption("h", "help", false, "Print this usage information");
            options.addOption("i", "ip", true, "IP address of master node");
            options.addOption("p", "port", true, "Port of master node");
            options.addOption("f", "file", true, "Data file");
            // Parse the program arguments
            CommandLine commandLine = parser.parse(options, args);
            // Set the appropriate variables based on supplied options
            if (commandLine.hasOption('h')) {
                System.out.println("Help Message");
                showUsage();
                System.in.read();
                System.exit(0);
            }
            if (commandLine.hasOption('i')) {
                ipAddr = commandLine.getOptionValue('i');
            }
            if (commandLine.hasOption('p')) {
                port = Integer.valueOf(commandLine.getOptionValue('p'));
            }
            if (commandLine.hasOption('f')) {
                dataFile = commandLine.getOptionValue('f');
            }
            //3.initialize the master
            Master master = new Master(ipAddr, port);
            RPC.Builder builder = new RPC.Builder(new Configuration());
            MasterProtocol mp = startServer(builder, master);
            master.getMasterDescriptor().setMaster(mp);
            master.dataFile = dataFile;
            //4. create the index

            //5. waiting for the queries from the client.
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static void showUsage() {
        System.out.println("* Options");
        System.out.println("* -p,--port master port used (int) 8888");
        System.out.println("* -i,--ip the IP address of salve(String) 127.0.0.1");
        System.out.println("* -h,--help print help information");
    }

    public Master(String ip, int port) {
        super(ip, port);
    }

    /**
     * 提供Client远程调用
     *
     * @param cd
     * @return
     */
    @Override
    public ResultDescriptor executeCommand(CommandDescriptor cd) {
        ResultDescriptor resultDescriptor = new ResultDescriptor(new Variant(false));
        //createDTITree
        if (cd.getName().equals("createDTITree")) {
            String dataFile = cd.getParameterDescriptors().get(0).getParameter().toString();
            if (dataFile == null)
                dataFile = this.dataFile;
            createDTITree(dataFile);
            resultDescriptor.setResult(new Variant(true));
        }
        //executeSimilarityQuery
        if (cd.getName().equals("executeSimilarityQuery")) {
            Timeline t = (Timeline) (cd.getParameterDescriptors().get(0).getParameter());
            Timeline tt = executeSimilarityQuery(t);
            resultDescriptor.setResult((Object) tt);
        }

        return resultDescriptor;
    }

    /**
     * 从文件中读取数据，生成DTITree
     *
     * @param dataFile
     */
    public void createDTITree(String dataFile) {
//        SparkSession spark=SparkSession
//                .builder()
//                .master(getMasterDescriptor().getIPAddress())
//                .appName("Master")
//                .getOrCreate();
//        //1.load data from file
//        JavaRDD<Double> data = spark
//                .sparkContext()
//                .textFile(dataFile,3)
//                .toJavaRDD()                                  //JavaRDD<String>
//                .scene(r-> GeomSuits.createTimeline(r)) //JavaRDD<Timeline>
//                .flatMap(r->r.getLabeledIntervals().iterator())//JavaRDD<ComplexIntervalImpl>
//                .flatMap(r->{
//                    ArrayList<Double> al = new ArrayList<>(2);
//                    al.add(r.getUpperBound());
//                    al.add(r.getLowerBound());
//                    return al.iterator();})
//                .distinct();
//        //2. calculate the min and max values
//        double minTime=data.min(new DoubleComparator());
//        double maxTime=data.max(new DoubleComparator());
        double minTime = TimelineGenerator.timeMinValue;
        double maxTime = TimelineGenerator.timeMaxValue;
        //3. set the rootTriangle
        rootTriangle = new TriangleShape(
                new Vector2D(minTime - 1, maxTime + 1),
                new Vector2D(maxTime + 1, maxTime + 1),
                new Vector2D(minTime - 1, minTime - 1)
        );
        queryShapeGenerator = new QueryShapeGenerator(new TriangleShape(rootTriangle));
        //4.partition the triangle
        slavePairs = executePartition(getMasterDescriptor());
        //5.call slave methods,create TI-Tree
        createTITrees(slavePairs);
    }

    @Override
    public TriangleShape getRootTriangle() {
        return this.rootTriangle;
    }

    /**
     * 根据根节点的三角形和Slave数目，将根节点的三角形划分为子三角形
     *
     * @param md
     * @return
     */
    @Override
    public List<Pair<TriangleShape, SlaveDescriptor>> executePartition(MasterDescriptor md) {
        List<TriangleShape> listTriangles = new ArrayList<>();
        List<TriangleShape> tempListTriangles = new ArrayList<>();
        List<TriangleShape> swapListTriangles = null;
        listTriangles.add((TriangleShape) rootTriangle.clone());
        int slaveCount = md.getSlaves().size();
        int triangleCount = 1;
        int i = 0;
        //if the count of triangles is less than slaveCount,then split the triangles in listTriangles
        while (triangleCount < slaveCount) {
            //顺次分解listTriangles中的三角形
            for (i = 0; i < listTriangles.size(); ++i) {
                if (triangleCount < slaveCount) {
                    tempListTriangles.add(listTriangles.get(i).leftTriangle());
                    tempListTriangles.add(listTriangles.get(i).rightTriangle());
                    triangleCount++;
                } else {
                    break;
                }
            }
            //如果listTriangles还有没有分解的三角形，则直接加入到tempListTriangles
            //并停止分裂
            if (i < listTriangles.size()) {
                for (; i < listTriangles.size(); ++i)
                    tempListTriangles.add(listTriangles.get(i));
                listTriangles = tempListTriangles;
                break;
            } else {//否则，交换tempListTriangles和listTriangles;执行下一轮分裂
                listTriangles.clear();
                swapListTriangles = listTriangles;
                listTriangles = tempListTriangles;
                tempListTriangles = swapListTriangles;
            }
        }
        //构建返回对象
        i = 0;
        assert md.getSlaves().size() == triangleCount;
        List<Pair<TriangleShape, SlaveDescriptor>> r = new ArrayList<>();
        for (SlaveDescriptor s : md.getSlaves()) {
            r.add(new Pair<>(listTriangles.get(i), s));
        }
        return r;
    }

    /**
     * 执行相似性查询
     *
     * @param t
     * @return
     */
    public Timeline executeSimilarityQuery(Timeline t) {

        Envelope e = Envelope.create(2);
        for (ComplexInterval li : t.getLabeledIntervals()) {
            e.combine(queryShapeGenerator.overlaps(li).getMBR());
        }

        for (Pair<TriangleShape, SlaveDescriptor> p : slavePairs) {
            if (Geom2DSuits.intersects(e, p.first()) == false) continue;
            CommandDescriptor cd = new CommandDescriptor("executeSimilarityQuery");
            ParameterDescriptor pd1 = new ParameterDescriptor(t);
            ParameterDescriptor pd2 = new ParameterDescriptor(e);
            cd.addParameterDescriptor(pd1);
            cd.addParameterDescriptor(pd2);
            p.second().getSlave().executeCommand(cd);
        }

        return null;
    }

    /**
     * createTI-Trees
     *
     * @param slavePairs
     */
    void createTITrees(final List<Pair<TriangleShape, SlaveDescriptor>> slavePairs) {
        CommandDescriptor cd = new CommandDescriptor("createTITree");
        ParameterDescriptor pdTriangle = new ParameterDescriptor(null);
        cd.addParameterDescriptor(pdTriangle);
        ParameterDescriptor pdDataFile = new ParameterDescriptor(new Variant(this.dataFile));
        cd.addParameterDescriptor(pdDataFile);
        for (Pair<TriangleShape, SlaveDescriptor> p : slavePairs) {
            pdTriangle.setParameter((Serializable) p.first());
            p.second().getSlave().executeCommand(cd);
        }
    }
}
