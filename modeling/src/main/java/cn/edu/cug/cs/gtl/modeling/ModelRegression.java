package cn.edu.cug.cs.gtl.modeling;

import cn.edu.cug.cs.gtl.io.File;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.ml.regression.LinearRegression;
import org.apache.spark.ml.regression.LinearRegressionTrainingSummary;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;

import java.util.List;
import java.util.Map;

public class ModelRegression {
    private String inputDataFile;
    private List<String> inputParameters;
    private String modelFile;   // = "E:\\test\\train\\IF_IDF\\allTrainVSM.txt.model";    //训练后得到的模型文件
    private String testFile;   //= "E:\\test\\test\\IF_IDF\\allTestVSM.txt";
    private String resultFile;

    private int dimension;
    double[] minValues;
    double[] maxValues;


    private Map<String, Integer> typeMap = null;

    public ModelRegression() {
    }


    /**
     * 计算单个点到其他点的距离，包括到自己的距离
     */
    public void Normprocess(SparkSession spark) {
        //读取数据文件
        Dataset<Row> df = spark.read()
                .format("csv")
                .option("header", "false") //reading the headers
                .option("mode", "DROPMALFORMED")
                .load("D:\\devs\\data\\spark\\channel_100_xy.cs");
        //文件数据量
        final int s = df.toJavaRDD().collect().size();
        //转换成List
        List<Row> data = df.toJavaRDD().collect();
        //处理成RDD
        JavaRDD<Row> dataLines = df.toJavaRDD().map(r -> {
            //每一行的数据量
            final int rsize = r.size();
            Object[] os = new Object[s + rsize];
            os[0] = r.getString(0);
            os[1] = r.getString(1);
            os[2] = r.getString(2);
            //欧几里得距离
            for (int k = 0; k <= s - 1; k++) {
                os[k + 3] = String.valueOf(Math.sqrt(Math.pow((Double.valueOf(r.getString(1))) - Double.valueOf(data.get(k).get(1).toString()), 2) + Math.pow((Double.valueOf(r.getString(2))) - Double.valueOf(data.get(k).get(2).toString()), 2)));
            }
            return RowFactory.create(os);
        });

//        dataLines.foreach(r -> System.out.println(r.toString()));

        for (Map.Entry<String, Integer> e : typeMap.entrySet()) {
            final String t = e.getKey();
            JavaRDD<LabeledPoint> data2 = dataLines
                    .map(r -> {
                        final int s2 = r.size();
                        double[] ds = new double[s2];
                        for (int k = 1; k <= s2; ++k)
                            ds[k - 1] = r.getDouble(k);
                        Vector v = Vectors.dense(ds);
                        return new LabeledPoint(
                                r.getString(0).trim().compareTo(t) == 0 ? 1 : 0,//如果是t设置为1，否则设置为0
                                v);
                    });

            data2.foreach(r -> {
                System.out.println(r.toString());
            });
        }
    }

    public static void main(String[] args) {

        String datFile = "d:" + File.separator + "devs" + File.separator + "data"
                + File.separator + "spark" + File.separator + "channel_100_xy.csv";

        SparkSession spark = SparkSession
                .builder()
                .appName("JavaLinearRegressionWithElasticNet")
                .master("local")
                .getOrCreate();

        // Load training data.
        Dataset<Row> training = spark.read()
                .format("csv")
                .option("header", "false") //reading the headers
                .option("mode", "DROPMALFORMED")
                .load(datFile);

        //中间处理数据过程
        //  Dataset<Row> training = spark.read().csv(datFile);

        LinearRegression lr = new LinearRegression()
                .setMaxIter(10)
                .setRegParam(0.3)
                .setElasticNetParam(0.8);

        // Fit the model.
        org.apache.spark.ml.regression.LinearRegressionModel lrModel = lr.fit(training);

        // Print the coefficients and intercept for linear regression.
        System.out.println("Coefficients: "
                + lrModel.coefficients() + " Intercept: " + lrModel.intercept());

        // Summarize the model over the training set and print out some metrics.
        LinearRegressionTrainingSummary trainingSummary = lrModel.summary();
        System.out.println("numIterations: " + trainingSummary.totalIterations());
        System.out.println("objectiveHistory: " + org.apache.spark.ml.linalg.Vectors.dense(trainingSummary.objectiveHistory()));
        trainingSummary.residuals().show();
        System.out.println("RMSE: " + trainingSummary.rootMeanSquaredError());
        System.out.println("r2: " + trainingSummary.r2());

        spark.stop();
    }
}
