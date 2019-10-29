package cn.edu.cug.cs.gtl.modeling;

import org.apache.spark.api.java.JavaDoubleRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.classification.SVMModel;
import org.apache.spark.mllib.classification.SVMWithSGD;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/*
岩性，坐标，属性
T,X,Y,Z,V0,V1,.......
Q,2.3,3.4,5.6,7.9,9.8,......
 */
public class ModelerSVMWithSGD implements Modeler {

    private String dataFileName = null;
    private Map<String, Integer> typeMap = null;
    private JavaRDD<Row> dataLines = null;
    private double minX;
    private double minY;
    private double minZ;
    private double maxX;
    private double maxY;
    private double maxZ;

    public ModelerSVMWithSGD(String datFileName) {
        dataFileName = datFileName;
        typeMap = new HashMap<String, Integer>();//岩性映射表
        SparkSession spark = SparkSession
                .builder()
                .master("local")
                .appName("ModelerSVMWithSGD")
                .getOrCreate();
        //读取数据文件
        Dataset<Row> df = spark.read().csv(dataFileName);
        JavaRDD<String> types = df.toJavaRDD().map(r -> r.getString(0)).distinct();
        List<String> tList = types.collect();
        int i = 0;
        for (String row : tList) {
            typeMap.put(row, ++i);
        }
        typeMap.forEach((k, v) -> System.out.println(k + ":" + v.toString()));
        types = null;
        //处理成RDD
        dataLines = df.toJavaRDD().map(r -> {
            return RowFactory.create(
                    r.getString(0),
                    Double.valueOf(r.getString(1)),
                    Double.valueOf(r.getString(2)),
                    Double.valueOf(r.getString(3)));
        });
        dataLines.foreach(r -> System.out.println(r));

        //计算最大和最小值
        JavaDoubleRDD xData = dataLines.mapToDouble(r -> (Double) (r.get(1)));
        minX = xData.min();
        maxX = xData.max();
        JavaDoubleRDD yData = dataLines.mapToDouble(r -> (Double) (r.get(2)));
        minY = yData.min();
        maxY = yData.max();
        JavaDoubleRDD zData = dataLines.mapToDouble(r -> (Double) (r.get(3)));
        minZ = zData.min();
        maxZ = zData.max();
        //尽快销毁
        xData = null;
        yData = null;
        zData = null;
        System.out.println("min,max="
                + minX + "," + minY + "," + minZ
                + maxX + "," + maxY + "," + maxZ);

    }

    public Integer getTypeIdentifier(String lithology) {
        return typeMap.get(lithology);
    }

    @Override
    public Object train(String type, int numIterations) {
        final String t = type;
        JavaRDD<LabeledPoint> data = dataLines
                .map(r -> new LabeledPoint(
                        r.getString(0).trim().compareTo(t) == 0 ? 1 : 0,//如果是t设置为1，否则设置为0
                        Vectors.dense(
                                r.getDouble(1),
                                // r.getDouble(2))));
                                r.getDouble(2),
                                r.getDouble(3))));
        data.foreach(r -> System.out.println(r));
        return SVMWithSGD.train(data.rdd(), numIterations);
    }

    @Override
    public double predict(Object svmModel, double[] vs) {
        return ((SVMModel) svmModel).predict(Vectors.dense(vs));
    }

    @Override
    public void generateMesh(String meshFile, Object model,
                             double stepX, double stepY, double stepZ,
                             double sx, double sy, double sz,
                             double ex, double ey, double ez) {
        long xCells = (long) ((ex - sx) / stepX);
        long yCells = (long) ((ey - sy) / stepY);
        long zCells = (long) ((ez - sz) / stepZ);
        xCells = xCells <= 0 ? 1 : xCells;
        yCells = yCells <= 0 ? 1 : yCells;
        zCells = zCells <= 0 ? 1 : zCells;

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(meshFile));
            String line = Long.valueOf(xCells).toString() + " "
                    + Long.valueOf(yCells).toString() + " "
                    + Long.valueOf(zCells).toString();
            bw.write(line);
            bw.newLine();
            bw.write("1");
            bw.newLine();
            bw.write("facies");
            bw.newLine();
            double r;
            int ri;
            for (long z = 0; z < zCells; z++) {
                for (long y = 0; y < yCells; y++) {
                    for (long x = 0; x < xCells; x++) {
                        r = predict(model, x * stepX, y * stepY, z * stepZ);
                        if (Double.compare(0.0, r) == 0)
                            ri = 0;
                        else
                            ri = 1;
                        bw.write(Integer.valueOf(ri).toString());
                        bw.newLine();
                    }
                }
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public double[] getMin() {

        double[] x = new double[3];
        x[0] = minX;
        x[1] = minY;
        x[2] = minZ;
        return x;
    }

    @Override
    public double[] getMax() {
        double[] x = new double[3];
        x[0] = maxX;
        x[1] = maxY;
        x[2] = maxZ;
        return x;
    }

    public static void main(String[] args) {
        String datFile = "d:" + File.separator + "devs" + File.separator + "data"
                + File.separator + "spark" + File.separator + "channel_100.csv";
        Modeler m = new ModelerSVMWithSGD(datFile);
        //2.8,5.0,1.5
        //Object model = m.train("Iris-virginica",50);
        //System.out.println(m.predict(model,2.8,5.0,1.5));
        //3.5,1.2,0.2
        //System.out.println(m.predict(model,3.5,1.2,0.2));
        Object model = m.train("1", 10);

        double s = m.predict(model, 69, 141, 1);
        double sx = m.getMinX();
        double sy = m.getMinY();
        double sz = m.getMinZ();
        double ex = m.getMaxX();
        double ey = m.getMaxY();
        double ez = m.getMaxZ();
        m.generateMesh(datFile + ".mesh", model, 5, 5, 1, sx, sy, sz, ex, ey, ez);


    }
}
