package cn.edu.cug.cs.gtl.modeling;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.cug.cs.gtl.modeling.libsvmimpl.svm_predict;
import cn.edu.cug.cs.gtl.modeling.libsvmimpl.svm_train;
import cn.edu.cug.cs.gtl.io.File;
import org.apache.spark.api.java.JavaDoubleRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import libsvm.*;

public class ModelerSVM implements Modeler {
    private String inputDataFile;
    private List<String> inputParameters;
    private String modelFile;// = "E:\\test\\train\\IF_IDF\\allTrainVSM.txt.model";    //训练后得到的模型文件
    private String testFile;//= "E:\\test\\test\\IF_IDF\\allTestVSM.txt";
    private String resultFile;

    private int dimension;
    double[] minValues;
    double[] maxValues;


    private Map<String, Integer> typeMap = null;
    private SparkSession spark = null;
    private org.slf4j.Logger logger = null;

    /**
     * @param dataFile csv文件，格式如下：     *
     *                 岩性，坐标，属性
     *                 T,X,Y,Z,V0,V1,.......
     *                 Q,2.3,3.4,5.6,7.9,9.8,......
     * @param flags
     */
    public ModelerSVM(String dataFile, List<String> flags, SparkSession spark) {
        if (spark == null) {
            this.spark = SparkSession
                    .builder()
                    .master("local")
                    .appName("ModelerSVM")
                    .getOrCreate();
        } else
            this.spark = spark;

        inputDataFile = dataFile;
        typeMap = new HashMap<String, Integer>();//岩性映射表

        this.dimension = 3;

        preprocess();//数据预处理

        if (flags == null) {
            inputParameters = new ArrayList<String>(12);
            inputParameters.add("-s");
            inputParameters.add("0");
            inputParameters.add("-t");
            inputParameters.add("2");
            inputParameters.add("-d");
            inputParameters.add("3");
            //inputParameters.add("-g");
            //inputParameters.add("2.0");
            inputParameters.add("-c");
            inputParameters.add("1");
            inputParameters.add("-m");
            inputParameters.add("1024.0");
            inputParameters.add("-h");
            inputParameters.add("1");
        } else
            inputParameters = new ArrayList<String>(flags);
    }

    public ModelerSVM(String dataFile) {
        this(dataFile, null, null);
    }

    /**
     * @param type csv文件中第一行中，需要处理的岩性类型名称
     *             第一行中等于type的设置为1，否则设置为0
     */
    @Override
    public Object train(String type, int numIterations) {
        try {
            //参数设置和满足LibSVM输入格式的训练文本
            //private String[] str_trained = {"-g","2.0","-c","32","-t","2","-m","500.0","-h","0","E:\\test\\train\\IF_IDF\\allTrainVSM.txt"};
            //str_model = svm_train.main(str_trained)
            //训练返回的是模型文件，其实是一个路径，可以看出要求改svm_train.java
            List<String> params = new ArrayList<String>(inputParameters);
            params.add(getSVMFile(type));
            modelFile = getModelFile(type);
            params.add(modelFile);
            String[] str_trained = new String[params.size()];
            params.toArray(str_trained);
            libsvm.svm_model model = svm_train.execute(str_trained);
            //libsvm.svm.svm_save_model(modelFile,model);
            return model;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public double predict(Object model, double[] vs) {
        return svm_predict.execute((svm_model) model, vs, 0);
    }

    public double predict(Object model, String testFile, String resultFile) {
        if (model == null) return 0;
        //测试文件、模型文件、结果文件路径
        this.testFile = testFile;
        this.resultFile = resultFile;
        String[] str_result = {testFile, (String) modelFile, resultFile};
        double accuracy = -1;
        try {
            accuracy = svm_predict.execute(str_result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accuracy;
    }

    @Override
    public void generateMesh(String meshFile, Object model,
                             double stepX, double stepY, double stepZ,
                             double sx, double sy, double sz,
                             double ex, double ey, double ez) {
        int dim = 3;
        if (ez - ex == 0 || stepZ == 0) {
            dim = 2;
        }
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
            if (dim == 3) {
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
            } else {
                for (long y = 0; y < yCells; y++) {
                    for (long x = 0; x < xCells; x++) {
                        r = predict(model, x * stepX, y * stepY);
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
        return minValues;
    }

    @Override
    public double[] getMax() {
        return maxValues;
    }

    private String getSVMFile(String type) {
        String dir = File.getDirectory(inputDataFile);
        String fileNme = File.getFileName(inputDataFile);
        String fullPathName = dir + File.separator + type + "_" + fileNme;
        return File.replaceSuffixName(fullPathName, "svm");
    }

    private String getModelFile(String type) {
        String dir = File.getDirectory(inputDataFile);
        String fileNme = File.getFileName(inputDataFile);
        String fullPathName = dir + File.separator + type + "_" + fileNme;
        return File.replaceSuffixName(fullPathName, "model");
    }

    /**
     * 数据预处理，针对csv文件，拆分成多个SVM文件；
     * 扫描岩性，统计最大、最小值
     */
    private void preprocess() {
        //读取数据文件
        Dataset<Row> df = spark.read().csv(inputDataFile);
        this.dimension = df.first().size() - 1;
        minValues = new double[this.dimension];
        maxValues = new double[this.dimension];
        JavaRDD<String> types = df.toJavaRDD().map(r -> r.getString(0)).distinct();
        List<String> tList = types.collect();
        int i = 0;
        for (String row : tList) {
            typeMap.put(row, ++i);
        }
        typeMap.forEach((k, v) -> System.out.println(k + ":" + v.toString()));
        types = null;

        //处理成RDD
        JavaRDD<Row> dataLines = df.toJavaRDD().map(r -> {
            final int s = r.size();
            Object[] os = new Object[s];
            os[0] = r.getString(0);
            for (int k = 1; k <= s - 1; ++k) {
                os[k] = Double.valueOf(r.getString(k));
            }
            return RowFactory.create(os);
        });
        dataLines.foreach(r -> System.out.println(r.toString()));

        //计算最大和最小值
        for (i = 1; i <= this.dimension; ++i) {
            final int j = i;
            JavaDoubleRDD xData = dataLines.mapToDouble(r -> (Double) (r.get(j)));
            minValues[j - 1] = xData.min();
            maxValues[j - 1] = xData.max();
        }


        System.out.println("min:" + Vectors.dense(minValues).toString());
        System.out.println("max:" + Vectors.dense(maxValues).toString());


        for (Map.Entry<String, Integer> e : typeMap.entrySet()) {
            final String t = e.getKey();

            JavaRDD<LabeledPoint> data = dataLines
                    .map(r -> {
                        final int s = r.size();
                        double[] ds = new double[s - 1];
                        for (int k = 1; k <= s - 1; ++k)
                            ds[k - 1] = r.getDouble(k);
                        Vector v = Vectors.dense(ds);
                        return new LabeledPoint(
                                r.getString(0).trim().compareTo(t) == 0 ? 1 : 0,//如果是t设置为1，否则设置为0
                                v);
                    });

            data.foreach(r -> {
                System.out.println(r.toString());
            });

            final String fileName = getSVMFile(t);
            try {
                writeSVMFile(data, fileName);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        /*
        String datFile = "d:"+ File.separator+"devs"+  File.separator +"data"
                + File.separator+"spark"+ File.separator+"iris.csv";
        Modeler m = new  ModelerSVM (datFile);
        Object model = m.train("Iris-virginica",50);
        //2.8,5.0,1.5
        System.out.println(m.predict(model,2.8,5.0,1.5));
        //3.5,1.2,0.2
        System.out.println(m.predict(model,3.5,1.2,0.2));
        */

        String datFile = "d:" + File.separator + "devs" + File.separator + "data"
                + File.separator + "spark" + File.separator + "channel_100_xy.csv";
        Modeler m = new ModelerSVM(datFile);
        Object model = m.train("1", 10);
        double s = m.predict(model, 69, 141, 1);
        double sx = m.getMinX();
        double sy = m.getMinY();
        //double sz=m.getMinZ();
        double ex = m.getMaxX();
        double ey = m.getMaxY();
        //double ez=m.getMaxZ();
        //m.generateMesh(datFile+".mesh",model,5,5,1,sx,sy,sz,ex,ey,ez);
        m.generateMesh(datFile + ".mesh", model, 5, 5, 0, sx, sy, 0, ex, ey, 0);
    }
}
