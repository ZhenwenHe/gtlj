package cn.edu.cug.cs.gtl.modeling;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.classification.SVMModel;
import org.apache.spark.mllib.linalg.distributed.CoordinateMatrix;
import org.apache.spark.mllib.linalg.distributed.IndexedRow;
import org.apache.spark.mllib.linalg.distributed.RowMatrix;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.util.MLUtils;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.function.BiFunction;

import static java.lang.Math.sqrt;

public interface Modeler {

    /**
     * 模型训练函数
     *
     * @param type
     * @param numIterations
     * @return 用于预测的模型
     */
    Object train(String type, int numIterations);

    /**
     * 用训练的模型预测输入点的属性（岩性）
     *
     * @param model
     * @param vs    double数组
     * @return
     */
    double predict(Object model, double[] vs);

    default double predict(Object model, double x, double y, double z) {
        double[] vs = new double[3];
        vs[0] = x;
        vs[1] = y;
        vs[2] = z;
        return predict(model, vs);
    }

    default double predict(Object model, double x, double y) {
        double[] vs = new double[2];
        vs[0] = x;
        vs[1] = y;
        return predict(model, vs);
    }

    /**
     * @param meshFile
     * @param model
     * @param stepX
     * @param stepY
     * @param stepZ
     * @param sx
     * @param sy
     * @param sz
     * @param ex
     * @param ey
     * @param ez
     */
    void generateMesh(String meshFile,
                      Object model,
                      double stepX, double stepY, double stepZ,
                      double sx, double sy, double sz,
                      double ex, double ey, double ez);

    default double getMinX() {
        return getMin()[0];
    }

    default double getMinY() {
        return getMin()[1];
    }

    default double getMinZ() {
        double[] m = getMin();
        if (m.length < 3)
            return Double.MAX_VALUE;
        else
            return m[2];
    }

    default double getMaxX() {
        return getMax()[0];
    }

    default double getMaxY() {
        return getMax()[1];
    }

    default double getMaxZ() {
        double[] m = getMax();
        if (m.length < 3)
            return -Double.MAX_VALUE;
        else
            return m[2];
    }

    double[] getMin();

    double[] getMax();

    default JavaRDD<LabeledPoint> readSVMFile(SparkSession spark, String fileName) {
        return MLUtils.loadLibSVMFile(spark.sparkContext(), fileName).toJavaRDD();
    }

    default void writeSVMFile(JavaRDD<LabeledPoint> data, String fileName) throws IOException {
        JavaRDD<String> datStrs = data.map(r -> {
            StringBuilder sb = new StringBuilder(Double.valueOf(r.label()).toString() + " ");
            double[] ds = r.features().toArray();
            int k;
            for (k = 1; k < ds.length; ++k) {
                sb.append(k);
                sb.append(":");
                sb.append(ds[k - 1]);
                sb.append(" ");
            }
            sb.append(k);
            sb.append(":");
            sb.append(ds[k - 1]);
            return sb.toString();
        });
        List<String> ls = datStrs.collect();
        int c = ls.size();
        int i = 0;
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            for (String s : ls) {
                bw.write(s);
                if (i < c - 1) bw.newLine();
                i++;
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 针对两个RDD做双重循环，基本的实现思路是先对连个RDD进行cartesian操作，
     * 得到一个PairRDD，然后对其中的每个对象执行BiFunction
     *
     * @param first
     * @param second
     * @param f
     * @param <T>
     * @param <U>
     * @param <R>
     * @return
     */
    default <T, U, R> JavaRDD<R> doubleLoop(JavaRDD<T> first, JavaRDD<U> second,
                                            java.util.function.BiFunction<T, U, R> f) {
        JavaPairRDD<T, U> cartesianRDD = first.cartesian(second);
        final BiFunction<T, U, R> ff = f;
        return cartesianRDD.map(t -> ff.apply(t._1(), t._2()));
    }

    /**
     * 计算点集1中每一个点与点集2中每个点的距离
     *
     * @param points1
     * @param points2
     * @return 返回距离矩阵
     */
    default JavaRDD<Double> calculateDistances(JavaRDD<LabeledPoint> points1, JavaRDD<LabeledPoint> points2) {
        JavaPairRDD<LabeledPoint, LabeledPoint> cartesianRDD = points1.cartesian(points2);
        JavaRDD<Double> drdd = cartesianRDD.map(t -> {
            double[] v1 = t._1.features().toArray();
            double[] v2 = t._2.features().toArray();
            int len = v1.length > v2.length ? v2.length : v1.length;
            double d = 0;
            for (int i = 0; i < len; ++i) {
                d += (v1[i] - v2[i]) * (v1[i] - v2[i]);
            }
            d = sqrt(d);
            return Double.valueOf(d);
        });
        return drdd;
    }
}
