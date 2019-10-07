package gtl.app;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

import java.util.ArrayList;
import java.util.List;

public class Cartesian {
    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .master("local[4]")
                .appName("Cartesian")
                .getOrCreate();
        JavaSparkContext sc = JavaSparkContext.fromSparkContext(spark.sparkContext());
        List<Integer> i1list = new ArrayList<Integer>(6);
        List<Integer> i2list = new ArrayList<Integer>(6);
        for (int i = 0; i < 6; i++) {
            i1list.add(i + 1);
            i2list.add(6 - i);
        }
        JavaRDD<Integer> rdd1 = sc.parallelize(i1list);
        JavaRDD<Integer> rdd2 = sc.parallelize(i2list);
        JavaPairRDD<Integer, Integer> result = rdd1.cartesian(rdd2);
        result.foreach(s -> System.out.println(s));
    }
}
