package cn.edu.cug.cs.gtl.index.itree;

import cn.edu.cug.cs.gtl.geom.*;
import cn.edu.cug.cs.gtl.ipc.*;
import cn.edu.cug.cs.gtl.common.Pair;
import cn.edu.cug.cs.gtl.common.Variant;
import cn.edu.cug.cs.gtl.index.shape.RegionShape;
import cn.edu.cug.cs.gtl.index.shape.TriangleShape;
import cn.edu.cug.cs.gtl.io.Serializable;
import cn.edu.cug.cs.gtl.io.storage.StorageManager;
import org.apache.commons.cli.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.List;

/**
 * Options:
 * -p,--port salve port used (int) 6666
 * -i,--ip the IP address of salve(String) localhost
 * -h,--help print help information
 */
public class Slave extends SlaveProxy {
    private static final long serialVersionUID = 1L;

    SlaveDescriptor slaveDescriptor;
    MasterDescriptor masterDescriptor;
    TITree<ComplexInterval> tree;
    transient SparkSession spark;

    public static void main(String[] args) {
        try {

            CommandLineParser cmp = new BasicParser();
            // Create a Parser
            CommandLineParser parser = new BasicParser();
            Options options = new Options();
            options.addOption("h", "help", false, "Print this usage information");
            options.addOption("i", "ip", true, "IP address of slave node");
            options.addOption("p", "port", true, "Port of slave node");
            options.addOption("m", "master", true, "address of master node, 127.0.0.1:8888");
            // Parse the program arguments
            CommandLine commandLine = parser.parse(options, args);
            // Set the appropriate variables based on supplied options
            String ipAddr = "127.0.0.1";
            int port = 6666;
            String masterIP = "127.0.0.1";
            int masterPort = 8888;
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
            if (commandLine.hasOption('m')) {

                String[] ss = commandLine.getOptionValue('m').split(":");
                masterIP = ss[0];
                masterPort = Integer.valueOf(ss[1]);
            }
            //start Server
            RPC.Builder builder = new RPC.Builder(new Configuration());
            Slave slave = new Slave();
            startServer(builder, slave);
            //register slave on master
            MasterProtocol sp = RPC.waitForProxy(
                    MasterProtocol.class,
                    MasterProtocol.versionID,
                    new InetSocketAddress(masterIP, masterPort),
                    new Configuration());

            sp.registerSlave(ipAddr, port);

            slave.masterDescriptor = sp.getMasterDescriptor();
            slave.masterDescriptor.setMaster(sp);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static void showUsage() {
        System.out.println("* Options");
        System.out.println("* -p,--port salve port used (int) 6666");
        System.out.println("* -i,--ip the IP address of salve(String) 127.0.0.1");
        System.out.println("* -m,--master the address of master(String) 127.0.0.1:8888");
        System.out.println("* -h,--help print help information");
    }

    public Slave(String ipAddress, int port) {
        tree = null;
        slaveDescriptor = new SlaveDescriptor(ipAddress, port);
    }

    public Slave() {
        tree = null;
        slaveDescriptor = new SlaveDescriptor("127.0.0.1", 6666);
    }


    @Override
    public SlaveDescriptor getSlaveDescriptor() {
        return slaveDescriptor;
    }

    @Override
    public ResultDescriptor executeCommand(CommandDescriptor cd) {
        ResultDescriptor rd = new ResultDescriptor(new Variant(false));
        try {

            if (cd.getName().equals("createTITree")) {
                Triangle triangle = (Triangle) cd.getParameterDescriptors().get(0).getParameter();
                Variant dataFile = (Variant) cd.getParameterDescriptors().get(1).getParameter();
                this.tree = createTITree(triangle, dataFile.toString());
                rd.setResult(new Variant(true));
            } else if (cd.getName().equals("executeSimilarityQuery")) {
                Timeline t = (Timeline) cd.getParameterDescriptors().get(0).getParameter();
                Envelope e = (Envelope) cd.getParameterDescriptors().get(1).getParameter();
                List<Pair<Long, Double>> lp = executeSimilarityQuery(t, e);
                ArrayListDescriptor<Pair<Long, Double>> r = new ArrayListDescriptor<>(lp);
                rd.setResult(r);
            } else {
                rd.setResult(new Variant(false));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rd;
    }

    @Override
    public String executeCommand(String cd) {
        return cd;
    }

    @Override
    public byte[] executeCommand(byte[] cd) {
        return cd;
    }

    @Override
    public ResultDescriptor executeCommand(ParameterDescriptor cd) {
        return new ResultDescriptor((Serializable) cd.getParameter());
    }


    TITree createTITree(Triangle triangle, String dataFile) throws IOException {
        spark = SparkSession
                .builder()
                //.master(this.masterDescriptor.getIPAddress())
                .master("local[*]")
                .appName("Slave")
                .getOrCreate();
        final Triangle t = (Triangle) triangle.clone();
        StorageManager sm = StorageManager.createMemoryStorageManager();
        this.tree = new TITree<ComplexInterval>(
                (TriangleShape) triangle.clone(),
                128,
                sm,
                JavaSparkContext.fromSparkContext(this.spark.sparkContext())
        );
        final TITree<ComplexInterval> tiTree = this.tree;
        //1.load data from file
        JavaRDD<ComplexInterval> data = spark
                .sparkContext()
                .textFile(dataFile, 3)
                .toJavaRDD()                                  //JavaRDD<String>
                .map(r -> GeomSuits.createTimeline(r)) //JavaRDD<Timeline>
                .flatMap(r -> r.getLabeledIntervals().iterator())//JavaRDD<ComplexIntervalImpl>
                .filter(r -> t.contains(r.getLowerBound(), r.getUpperBound()));
        data.foreach(r -> tiTree.insert(r));
        return this.tree;
    }

    boolean insert(List<ComplexInterval> timeline) {
        for (ComplexInterval i : timeline)
            tree.insert(i);
        return true;
    }


    boolean delete(List<ComplexInterval> timeline) {
        for (ComplexInterval i : timeline)
            tree.delete(i);
        return true;
    }


    List<Pair<Long, Double>> executeSimilarityQuery(Timeline t, Envelope e) {
        List<ComplexInterval> lr = tree.regionQuery(new RegionShape(e));
        if (lr == null && lr.isEmpty() == true) return null;
        return calculateSimilarity(t, lr);
    }

    /**
     * 计算两个IntervalSet的相似性
     *
     * @param t Timeline,转成IntervalSet后，其中的所有Interval的ParentID相同
     * @param b 其中的Interval的可能具有不同的ParentID
     * @return
     */
    List<Pair<Long, Double>> calculateSimilarity(Timeline t, List<ComplexInterval> b) {
        JavaSparkContext sparkContext = JavaSparkContext.fromSparkContext(spark.sparkContext());
        JavaRDD<ComplexInterval> aRDD = sparkContext.parallelize(t.getLabeledIntervals());
        JavaRDD<ComplexInterval> bRDD = sparkContext.parallelize(b);
        return bRDD.cartesian(aRDD)
                .filter(r -> r._1().getName().equals(r._2().getName()))
//                .mapToPair(r-> new Tuple2<Long,Double>(
//                                        Long.valueOf(r._1.getParentID().longValue()),
//                                        Double.valueOf(r._1.intersection(r._2))))
                .mapToPair(new PairFunction<Tuple2<ComplexInterval, ComplexInterval>, Long, Double>() {
                    public Tuple2<Long, Double> call(
                            Tuple2<ComplexInterval, ComplexInterval> r) {
                        return new Tuple2<Long, Double>(
                                Long.valueOf(r._1.getParentID().longValue()),
                                Double.valueOf(r._1.intersection(r._2)));
                    }
                })
                .groupByKey() //JavaPairRDD<Long,Iterator<Double>>
                .map(r -> {
                    Double s = new Double(0);
                    Iterator<Double> id = r._2.iterator();
                    while (id.hasNext())
                        s += id.next();
                    return new Pair<Long, Double>(r._1, s);
                })
                .collect();
    }

    /**
     * 计算两个IntervalSet的相似性
     *
     * @param a 其中的所有Interval的ParentID相同，一般是Timeline转换而来
     * @param b 其中的Interval的可能具有不同的ParentID
     * @return
     */
    List<Pair<Pair<Long, Long>, Double>> calculateSimilarity(List<ComplexInterval> a, List<ComplexInterval> b) {
        JavaSparkContext sparkContext = JavaSparkContext.fromSparkContext(spark.sparkContext());

        JavaRDD<ComplexInterval> aRDD = sparkContext.parallelize(a);
        JavaRDD<ComplexInterval> bRDD = sparkContext.parallelize(b);
        return bRDD.cartesian(aRDD)
                .filter(r -> r._1().getName().equals(r._2().getName()))
//                .mapToPair(r-> new Tuple2<Long,Double>(
//                                        Long.valueOf(r._1.getParentID().longValue()),
//                                        Double.valueOf(r._1.intersection(r._2))))
                .mapToPair(new PairFunction<Tuple2<ComplexInterval, ComplexInterval>, Pair<Long, Long>, Double>() {
                    public Tuple2<Pair<Long, Long>, Double> call(
                            Tuple2<ComplexInterval, ComplexInterval> r) {
                        return new Tuple2<Pair<Long, Long>, Double>(
                                new Pair<Long, Long>(
                                        Long.valueOf(r._1.getParentID().longValue()),
                                        Long.valueOf(r._2.getParentID().longValue())),
                                Double.valueOf(r._1.intersection(r._2)));
                    }
                })
                .groupByKey() //JavaPairRDD<Pair<Long,Long> ,Iterator<Double>>
                .map(r -> {
                    Double s = new Double(0);
                    Iterator<Double> id = r._2.iterator();
                    while (id.hasNext())
                        s += id.next();
                    return new Pair<Pair<Long, Long>, Double>(r._1, s);
                })
                .collect();
    }
}
